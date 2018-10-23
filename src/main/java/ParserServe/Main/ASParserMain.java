package ParserServe.Main;
import ParserServe.SourceReaders.RegularFileReader;
import ParserServe.SourceReaders.ByDemandFileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ASParserMain {

    public static void main(String[] args)
    {
        String nextMode;

        //Regular mode - reading data from resources
        if(args.length ==0) {
            BlockingQueue<String> dataQ = new LinkedBlockingDeque<>();
            RegularFileReader fileReader = new RegularFileReader(dataQ);
            Thread readerThread = new Thread(fileReader);

            readerThread.start();
        }
        else {
            BlockingQueue<String> dataQ = new LinkedBlockingDeque<>();
            String exFilePath = args[0];
            nextMode = args[1];
            ByDemandFileReader byDemandFileReader = new ByDemandFileReader(dataQ,exFilePath);
            Thread exFileReaderThread = new Thread(byDemandFileReader);

            exFileReaderThread.start();

        }
    }
}
