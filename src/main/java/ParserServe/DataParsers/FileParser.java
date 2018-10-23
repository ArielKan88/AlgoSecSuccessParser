package ParserServe.DataParsers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser implements Runnable,IDataParser {
    private BlockingQueue<String> originalDataBuffer;
    private BlockingQueue<String> parsedDataBuffer;
    private final String FIELDS_STR = "#Fields:";
    private final String CS_HOST = "cs-host";
    public FileParser(BlockingQueue<String> dataQ, BlockingQueue<String> parsedQ)
    {
        originalDataBuffer = dataQ;
        parsedDataBuffer = parsedQ;
    }
    public void run() {
        parseData();
    }

    public void parseData() {

        int indexOfCS = 0;
        while (true)
        {
            String line = originalDataBuffer.poll();

            //File's meta-data find the index of cs-host
            if(line.startsWith(FIELDS_STR)){
                String[] metaData = line.split(" ");
                indexOfCS = findIndexOfHosts(Arrays.asList(metaData));
            }
            if(indexOfCS != -1)
            {
                extractHosts(line,indexOfCS);
            }
        }
    }

    private int findIndexOfHosts(List<String> metdaData) {
        return metdaData.indexOf(CS_HOST);
    }

    private void extractHosts(String line, int indexOfCS) {

        //Looking for hosts with spaces inside ToDo for later ((?<![\\])['"])((?:.(?!(?<![\\])\1))*.?)\1
        Pattern pattern = Pattern.compile("((?<![\\\\])['\"])((?:.(?!(?<![\\\\])\\1))*.?)\\1");

        Matcher matcher = pattern.matcher(line);

        if(matcher.find()) {

            validateAndSend(matcher.group());
        }
        //No hosts with inspaces were found, split by spaces and Index
        else{
            String[] splittedLine = line.split(" ");
            String host = splittedLine[indexOfCS];
            validateAndSend(host);

        }
    }

    private void validateAndSend(String host) {
        host = host.trim();

        String urlPattern = "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|][\\s+(?=\\S{2})]*";
        String ipPattern = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})[\\s+(?=\\S{2})]*$";

        if(host.length()<4 || host.equals("")){

            return;
        }

        Pattern urlPat = Pattern.compile(urlPattern);
        Pattern ipPat = Pattern.compile(ipPattern);

        Matcher urlMatcher = urlPat.matcher(host);
        Matcher ipMatcher = ipPat.matcher(host);

        if(urlMatcher.matches() || ipMatcher.matches())
        {
            parsedDataBuffer.add(host);
        }

    }

}
