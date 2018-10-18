package ParserServe.ForFutureExpansionHttp;

import ParserServe.LogParser;

public class HttpParser extends LogParser {


    public HttpParser()
    {
        super();
        source = new MyHttpParser();
    }
}
