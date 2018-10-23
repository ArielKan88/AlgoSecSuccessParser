package ParserServe.SourceReaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;


public class RegularFileReader implements Runnable, SourceReader {

  private BlockingQueue<String> dataBuffer;

    public RegularFileReader(BlockingQueue<String> dataQueue)
    {
        dataBuffer = dataQueue;

    }

    public void run() {
            readData();
    }

    public void readData() {

        InputStream data = RegularFileReader.class.getResourceAsStream("/log_example.log");

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(data))) {
                 buffer.lines().forEach(e -> {
                     try {
                         dataBuffer.put(e);
                     } catch (InterruptedException e1) {
                         e1.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintQueue();
    }

    private void PrintQueue() {
        dataBuffer.forEach(e -> System.out.println(e));
    }


}
