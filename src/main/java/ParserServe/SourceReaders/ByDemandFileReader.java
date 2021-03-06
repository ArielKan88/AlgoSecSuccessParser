package ParserServe.SourceReaders;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;

public class ByDemandFileReader implements Runnable, SourceReader {

    private BlockingQueue<String> dataBuffer;
    private String requestedPath;
    private String pathForService;
    private String requestedFileName;
    private Integer linesReadSoFar;

    //ToDo split file name from path and send separately
    public ByDemandFileReader(BlockingQueue<String> queue, String filePath) {
        dataBuffer = queue;
        requestedPath = filePath;
        requestedFileName = null;
        pathForService = null;
        linesReadSoFar = 0;
    }

    @Override
    public void run() {
        splitNameFromPath(requestedPath);
        readData();
    }

    //Watch service needs a clear path, this why we need to separate it from from the full path
    private void splitNameFromPath(String path) {
        int endIndex = path.lastIndexOf(File.separator);
        pathForService = endIndex == -1 ? System.getProperty("user.dir") : path.substring(0, endIndex);
        requestedFileName = path.substring(endIndex + 1);
        //System.out.println("Handling " + requestedFileName);
    }

    public void readData() {
        //First we read the data which is already in the file, than we'll monitor for changes
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(requestedPath))) {
            bufferedReader.lines().forEach(e -> {
                try {
                    dataBuffer.put(e);
                    ++linesReadSoFar;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            });

            //After the initial reading we want to watch for any modification in the file
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path fileDir = Paths.get(pathForService);
            fileDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key;
                key = watcher.take();

                key.pollEvents().forEach(e -> {
                    WatchEvent.Kind<?> kind = e.kind();

                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) e;
                    Path fileName = ev.context();
//                    System.out.println("is filename equals to requested filename? : " + fileName +);
                    if (fileName.toString().equals(requestedFileName)) {
                        try (BufferedReader modifiedReader = new BufferedReader(new FileReader(requestedPath))) {
                            modifiedReader.lines().skip(linesReadSoFar).forEach(l -> {
                                try {
                                    dataBuffer.put(l);
                                    ++linesReadSoFar;
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            });

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    

}
