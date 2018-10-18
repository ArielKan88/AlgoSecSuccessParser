package ParserServe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSender{

    //ToDo get file path and format from .Properties, get input from interface -> Strategy of readers

    public static void sortAndPrintResults(ConcurrentHashMap<String,Integer> results)
    {
        results.entrySet()
               .stream()
               .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(entry -> {
                        System.out.println("Host : " + entry.getKey() + " Count : " + entry.getValue());
                });

    }
    public static void main(String[] args)
    {
        FileParser fileAnalysis = new FileParser();
        ConcurrentHashMap<String,Integer> parsingRs= fileAnalysis.tryToParseSource();

        if(!parsingRs.isEmpty())
            {

                sortAndPrintResults(parsingRs);
                System.out.println("done");
            }
        else
            {
                System.out.println("Error in Parsing Data");
            }

    }
}
