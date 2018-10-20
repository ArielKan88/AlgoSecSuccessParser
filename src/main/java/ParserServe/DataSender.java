/*
* Our main class, Begins the procedure of getting and parsing  the data
* If the process were successful it sorts the dataCounting map and displays it
* */

package ParserServe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class DataSender{

    /*ToDo get file path and format from .Properties, get input (File Path) from user, add input to properties for reader to read-> Factory of readers
      ToDo Add singlethreadedExecutor for runTime results retrieval
      ToDo start static reader here -> consider getting it from factory
    */
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
        SourceReader.readData();
        setFilePathToPropertiesFile();

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

    private static void setFilePathToPropertiesFile() {
        Properties parsingProperties = new Properties();

        System.out.println("Please Enter The Path of the file you wish to parse");
        Scanner userInput = new Scanner(System.in);

            try(OutputStream output = new FileOutputStream("parsing.properties")) {
                    if(userInput.hasNextLine()){
                        String filePath = userInput.nextLine();

                        parsingProperties.setProperty("LogFilePath",filePath);
                        parsingProperties.store(output,null);
                    }else {
                        System.out.println("Please Enter The Path of the file you wish to parse");
                    }

            }catch (IOException e){
                //ToDo add proper logging or handle properly exception
                e.printStackTrace();
            }


    }
}
