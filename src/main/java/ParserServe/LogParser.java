package ParserServe;

import java.util.concurrent.ConcurrentHashMap;

public class LogParser {


    protected IDataParser source;

        public ConcurrentHashMap tryToParseSource()
        {
           ConcurrentHashMap result= source.ParseData();

           return result;
        }
}
