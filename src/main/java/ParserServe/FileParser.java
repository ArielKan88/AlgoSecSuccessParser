/*
* The son class of LogParser, Initializes the IDataParser to actual Parser class
* in this case a file Parser*/

package ParserServe;


public class FileParser extends LogParser {

    public FileParser()
    {
        super();
        source = new MyFileParser();

    }


}
