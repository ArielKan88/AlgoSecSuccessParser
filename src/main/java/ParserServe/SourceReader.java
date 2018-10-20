
/*
* The role of this class (and any different SourceReader in the future) is to access the
* desired Data source (File in our case) and to retrieve the logging data from it in an efficient way
* using Executor services for threading
* */
package ParserServe;

import java.io.*;
import java.util.*;


//ToDo, consider adding interface (Factory) to add multiple source readers (HTTP,different file format etc)
  public class SourceReader {


      //ToDo re-check necessity -> Synchronized queue
      //ToDo add Static getter
      public static List<String> data = new ArrayList<>();

      //ToDo change to void, should be invoked from Main in DataSender class
      public static void readData(){

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
              //todo add proper logging, exception handling
              e.printStackTrace();
          }

      }

      public static List<String> getData(){
          return data;
      }

    protected static String getPathFromProperties() {
        Properties pathProperties = new Properties();
        String path = null;

	    try(InputStream input = new FileInputStream("parsing.properties")) {

		    pathProperties.load(input);

		    path = pathProperties.getProperty("LogFilePath");

            if(null == path)
            {
                path = "C:\\Users\\ariel\\Desktop\\log_example.log";

            }

	    } catch (IOException e) {
            //todo add proper logging, exception handling
            e.printStackTrace();
	    }
        return path;
    }




}
