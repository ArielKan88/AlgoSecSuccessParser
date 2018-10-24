package ParserServe.DataParsers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class FileParser implements Runnable, IDataParser {
    private final String FIELDS_STR = "#Fields:";
    private final String CS_HOST = "cs-host";
    private BlockingQueue<String> originalDataBuffer;
    private BlockingQueue<String> parsedDataBuffer;
    private int indexOfCS;

    public FileParser(BlockingQueue<String> dataQ, BlockingQueue<String> parsedQ) {
        originalDataBuffer = dataQ;
        parsedDataBuffer = parsedQ;
    }

    public void run() {
        parseData();
    }

    public void parseData() {
        while (true) {
            try {
                String line = originalDataBuffer.take();
                //File's meta-data find the index of cs-host
                if (line.startsWith(FIELDS_STR)) {
                    String[] metaData = line.split("\\s+");
                    this.indexOfCS = findIndexOfHosts(Arrays.asList(metaData));
                    if (indexOfCS < 0) throw new RuntimeException("No " + CS_HOST + " field. Cannot parse");
                } else if (!line.startsWith("#")) {
                    String host = extractHost(line);
                    parsedDataBuffer.add(host);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String extractHost(String line) {
        int countFieldIndex = 0;
        String[] split = line.split("\\s+");
        int i = 0;
        int j = 0;
        while (countFieldIndex <= indexOfCS && i < split.length) {
            if (split[i].startsWith("\"")) {
                j = i;
                while (j < split.length && !split[j].endsWith("\"")) {
                    j++;
                }
            } else {
                j = i;
            }
            countFieldIndex++;
            i++;
        }

        StringBuilder sb = new StringBuilder();
        for (int idx = i -1 ; idx <= j && idx < split.length; idx++) {
            sb.append(split[idx]);
        }
        return sb.toString();
    }

    private int findIndexOfHosts(List<String> metaData) {
        return metaData.indexOf(CS_HOST) - 1;
    }

}
