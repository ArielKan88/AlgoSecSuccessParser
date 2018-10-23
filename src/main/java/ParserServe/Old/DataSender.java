/*
* Our main class, Begins the procedure of getting and parsing  the data
* * */

package ParserServe.Old;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

public class DataSender{

    /*
      ToDo start static reader here -> consider getting it from factory
    */

    public static void main(String[] args)
    {
        InputStream log = DataSender.class.getResourceAsStream("/log_example.log");

        SourceReader.readData();
        setFilePathToPropertiesFile();

        FileParser fileAnalysis = new FileParser();
        fileAnalysis.tryToParseSource();


    }

    private static void setFilePathToPropertiesFile() {
        Properties parsingProperties = new Properties();

        System.out.println("Welcome to Our Log Parser! ");
        System.out.println("Please Enter The Path of the file you wish to parse:");
        System.out.println("Something like this C:\\Users\\ariel\\Desktop\\log_example.log");
        Scanner userInput = new Scanner(System.in);

            try(OutputStream output = new FileOutputStream("parsing.properties")) {
                    if(userInput.hasNextLine()){
                        String filePath = userInput.nextLine();

                        parsingProperties.setProperty("LogFilePath",filePath);
                        parsingProperties.store(output,null);
                    }else {
                        System.out.println("Please Enter The Path of the file you wish to parse:");
                    }

            }catch (IOException e){
                //ToDo add proper logging or handle properly exception
                e.printStackTrace();
            }


    }
}
