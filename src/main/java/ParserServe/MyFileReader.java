package ParserServe;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileReader implements DataSource{

    LocalDateTime counterLastUpdate;

    private ConcurrentHashMap<String,Integer> countingMap;

        public MyFileReader()
        {
            countingMap = new ConcurrentHashMap<>();
        }

        public ConcurrentHashMap ParseData()
        {

            int indexOfCsHost =0;
            int i=0;

                    ArrayList<String> dataToParse = SourceReader.readData();
                    ListIterator<String> dataIterator = dataToParse.listIterator();

                    while(dataIterator.hasNext())
                    {
                        String data = dataIterator.next();
                        if(i<5 && data.startsWith("#Fields"))
                        {
                            indexOfCsHost = findIndexOfHosts(data);

                        }
                        //First 5 lines of the file are comments and MetaData
                        if(i>5)
                        {
                            String[] line = data.split(" ");

                            String host = line[indexOfCsHost];
                            validateAndCountHosts(host);
                        }
                        ++i;
                    }
                    //ToDo Implement by demand requests according to request time
                    counterLastUpdate = LocalDateTime.now();
     //               printHashMap();

            return countingMap;
        }

    private int findIndexOfHosts(String data) {

            int index=0;

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

    public void printHashMap()
    {
        countingMap.entrySet().forEach(entry -> {
            System.out.println("Host : " + entry.getKey() + " Count : " + entry.getValue());
        });
    }

    public ConcurrentHashMap<String,Integer> getParsingResults()
    {
        return countingMap;
    }

}
