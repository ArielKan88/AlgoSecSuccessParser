package ParserServe.OldUmImplementedHttp;

import ParserServe.Old.LogParser;

public class HttpParser extends LogParser {


    public HttpParser()
    {
        super();
        source = new MyHttpParser();
    }
}
