package ParserServe;

import java.util.concurrent.ConcurrentHashMap;

public class LogDataParser {


    protected DataSource source;

        public ConcurrentHashMap tryToParseSource()
        {
           ConcurrentHashMap result= source.ParseData();

           return result;
        }
}
