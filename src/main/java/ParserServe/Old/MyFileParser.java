/*
 * The actual parser of the data, retreives the data from the Reader and Parses it according to the rules
 * Should include Executor Service of a single thread to allow runTime data parsing.
 * */

package ParserServe.Old;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ToDo Fix by demand
public class MyFileParser implements IDataParser {

    private ConcurrentHashMap<String,Integer> countingMap;

    public MyFileParser()
    {
        countingMap = new ConcurrentHashMap<>();
    }

    public void ParseData()
    {

        int indexOfCsHost =0;
        int i=0;
        //ToDo add newSingleThreadExecutor() for runTime data parsing
        List<String> dataToParse = SourceReader.getData();


        ListIterator<String> dataIterator = dataToParse.listIterator();

        while(dataIterator.hasNext())
        {

            String data = dataIterator.next();
            if(i<5 && data.startsWith("#Fields"))
            {
                indexOfCsHost = findIndexOfHosts(data);

            }
            //First 5 lines of the file are comments and MetaData
            if(i>5 && indexOfCsHost != -1)
            {
                String[] line = data.split(" ");

                String host = line[indexOfCsHost];
                validateAndCountHosts(host);
            }

            ++i;
        }

    }

    public void sortResults(){

        ExecutorService readingService =  Executors.newSingleThreadExecutor();

        readingService.execute(() -> {

            if(countingMap.isEmpty())
            {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                }catch (InterruptedException e)
                {
                    //ToDo add proper logging
                    e.printStackTrace();
                }
            }
            else {
                countingMap.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                    System.out.println("Host : " + entry.getKey() + " Count : " + entry.getValue());
                });
            }

        });
        readingService.shutdown();
    }

    private int findIndexOfHosts(String data) {

        int index=-1;

        String[] titlesLine = data.split(" ");

        for(int i=0;i<titlesLine.length;++i)
        {
            if(titlesLine[i].equals("cs-host"))
            {
                index =i;
            }
        }
        return index;
    }

    private void validateAndCountHosts(String host) {

        host = host.trim();

        String urlPattern = "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String ipPattern = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

        if(host.length()<4 || host.equals("")){

            return;
        }

        Pattern urlPat = Pattern.compile(urlPattern);
        Pattern ipPat = Pattern.compile(ipPattern);

        Matcher urlMatcher = urlPat.matcher(host);
        Matcher ipMatcher = ipPat.matcher(host);

        if(urlMatcher.matches() || ipMatcher.matches())
        {
            Integer temp = countingMap.putIfAbsent(host,1);

            if(null != temp)
            {
                Integer countToUpdate = countingMap.get(host);
                countingMap.replace(host,++countToUpdate);
            }

        }

    }

    public ConcurrentHashMap<String,Integer> getCountingMap() {
        return countingMap;
    }
}
