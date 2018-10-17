package ParserServe;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

public class DataSender{

    //ToDo get file path and format from .Properties, get input from interface -> Strategy of readers
    //ToDo Add JMS for client communication

    public static void sortAndPrintResults(ConcurrentHashMap<String,Integer> results)
    {
        LinkedHashMap<String,Integer> sorted = results
        .entrySet()
        .stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(
            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));

        sorted.entrySet().forEach(entry -> {
            System.out.println("Host : " + entry.getKey() + " Count : " + entry.getValue());
        });



    }
    public static void main(String[] args)
    {
        LogFileAnalysis fileAnalysis = new LogFileAnalysis();
        ConcurrentHashMap<String,Integer> parsingRs= fileAnalysis.tryToParseSource();

        if(!parsingRs.isEmpty())
            {

                sortAndPrintResults(parsingRs);
                System.out.println("done");
            }









    }
}
