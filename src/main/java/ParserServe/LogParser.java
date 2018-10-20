/*
* The Father class of all available and future parsers, Applying the parse method through
* Interface composition
*
*
* */


package ParserServe;

import java.util.concurrent.ConcurrentHashMap;

public class LogParser {


    protected IDataParser source;

        public ConcurrentHashMap tryToParseSource()
        {
           ConcurrentHashMap result= source.ParseData();

           source.sortResults();

           return result;
        }
}
