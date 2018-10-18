
/*
* The role of this class (and any different SourceReader in the future) is to access the
* desired Data source (File in our case) and to retrieve the logging data from it in an efficient way
* using Executor services for threading
* */
package ParserServe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


//ToDo, consider adding interface (Factory) to add multiple source readers (HTTP,different file format etc)
//ToDo add newSingleThreadExecutor() for concurrent data retrieval
//ToDo get file path and format from .Properties file
  public class SourceReader {


      //ToDo re-check necessity -> Synchronized queue
      public static List<String> data = new ArrayList<>();

      public static List<String> readData(){

          String filePath = getPathFromProperties();
          String row;

          try {
              BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

              while((row = bufferedReader.readLine()) != null)
              {
                  data.add(row);
              }

          }catch (IOException e)
          {
              //todo add proper logging
              e.printStackTrace();
          }


          return data;
      }

    //ToDo make the properties retrieving work
    protected static String getPathFromProperties() {
        String path = null;
        try{
            InputStream in = MyFileParser.class.getClassLoader().getResourceAsStream("/src/Config/Parsing.properties");

            if(null == in)
            {
                path = "C:\\Users\\ariel\\Desktop\\log_example.log";

            }else {
                Properties prop = new Properties();
                prop.load(in);

                path = prop.getProperty("LogFilePath");
            }
        }
        catch(IOException e)
        {
            //todo add proper logging
            e.printStackTrace();
        }

        return path;
    }




}
