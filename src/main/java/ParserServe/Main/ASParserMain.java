package ParserServe.Main;
import ParserServe.DataParsers.FileParser;
import ParserServe.DispalyParsedData.DisplayData;
import ParserServe.SourceReaders.RegularFileReader;
import ParserServe.SourceReaders.ByDemandFileReader;

import javax.xml.ws.Dispatch;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ASParserMain {

    public static void main(String[] args)
    {
        BlockingQueue<String> dataQ = new LinkedBlockingDeque<>();
        BlockingQueue<String> parsedDataQ = new LinkedBlockingDeque<>();
        //Regular mode - reading data from resources
        if(args.length ==0) {
            runFileReader();

        }
        else {
            runDispaly(parsedDataQ);
            runParser(dataQ,parsedDataQ);
            runByDemandFileReader(args,dataQ);
        }
    }

    private static void runFileReader() {
        BlockingQueue<String> dataQ = new LinkedBlockingDeque<>();
        RegularFileReader fileReader = new RegularFileReader(dataQ);

        Thread readerThread = new Thread(fileReader);
        readerThread.start();
    }

    private static void runByDemandFileReader(String[] params, BlockingQueue<String>data){
        String exFilePath = params[0];
        ByDemandFileReader byDemandFileReader = new ByDemandFileReader(data,exFilePath);
        Thread exFileReaderThread = new Thread(byDemandFileReader);
        exFileReaderThread.start();

    }

    private static void runParser(BlockingQueue<String> data,BlockingQueue<String> parsedHosts){
        FileParser parser = new FileParser(data,parsedHosts);
        Thread parserThread = new Thread(parser);
        parserThread.start();

    }

    private static void runDispaly(BlockingQueue<String>parsedHosts)
    {
        DisplayData displayD = new DisplayData(parsedHosts);
        Thread displayThread = new Thread(displayD);
        displayThread.start();
    }
}
