/*
* The Father class of all available and future parsers, Applying the parse method through
* Interface composition
*
*
* */

package ParserServe.Old;

public class LogParser {

    protected IDataParser source;

        public void tryToParseSource()
        {
           source.ParseData();

           source.sortResults();


        }
}
