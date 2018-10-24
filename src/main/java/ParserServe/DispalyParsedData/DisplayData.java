package ParserServe.DispalyParsedData;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

public class DisplayData implements Runnable {

    private BlockingQueue<String> parsedDataBuffer;
    private ConcurrentHashMap<String, Integer> countingMap;
    private ScheduledExecutorService readingService;

    public DisplayData(BlockingQueue<String> dataBuffer) {
        parsedDataBuffer = dataBuffer;
        countingMap = new ConcurrentHashMap<>();
        readingService = Executors.newSingleThreadScheduledExecutor();
        sortAndPrintMap();
    }

    public void run() {
        sortAndPrintMap();
        addHostsToMap();
    }

    private void addHostsToMap() {
        while (true) {
            try {
                String parsedHost = parsedDataBuffer.take();
                Integer temp = countingMap.putIfAbsent(parsedHost, 1);
                if (temp != null) {
                    countingMap.replace(parsedHost, temp + 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sortAndPrintMap() {
        readingService.scheduleAtFixedRate(() -> {
            countingMap.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .forEach(entry -> System.out.println("Host : " + entry.getKey() + " Count : " + entry.getValue()));
        },10,5, TimeUnit.SECONDS);
    }

}
