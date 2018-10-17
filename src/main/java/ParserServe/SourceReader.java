package ParserServe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


//ToDo, consider adding interface to add abstraction for multiple readers
  public class SourceReader {


      //ToDo re-check necessity -> Synchronized queue
      public static ArrayList<String> data = new ArrayList<>();

      public static ArrayList<String> readData(){

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
              e.printStackTrace();
          }

          return data;
      }

    //ToDo make the properties retrieving work
    protected static String getPathFromProperties() {
        String path = null;
        try{
            InputStream in = MyFileReader.class.getClassLoader().getResourceAsStream("/src/Config/Parsing.properties");

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
            e.printStackTrace();
        }

        return path;
    }




}
