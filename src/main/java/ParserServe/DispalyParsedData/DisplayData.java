package ParserServe.DispalyParsedData;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

public class DisplayData implements Runnable {
    private BlockingQueue<String> parsedDataBuffer;
    private ConcurrentHashMap<String,Integer> countingMap;

    public DisplayData(BlockingQueue<String> dataBuffer)
    {
        parsedDataBuffer = dataBuffer;
        countingMap = new ConcurrentHashMap<>();
    }

    public void run() {
      sortAndPrintMap();
      addHostsToMap();
    }

    private void addHostsToMap()
    {
        while (true)
        {
            try {
                String parsedHost = parsedDataBuffer.take();

                Integer temp = countingMap.putIfAbsent(parsedHost,1);

                if(null != temp)
                {
                    Integer countToUpdate = countingMap.get(parsedHost);
                    countingMap.replace(parsedHost,++countToUpdate);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sortAndPrintMap()
    {
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
}
