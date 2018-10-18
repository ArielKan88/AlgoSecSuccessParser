package ParserServe;

public class HttpParser extends LogParser {


    public HttpParser()
    {
        super();
        source = new MyHttpParser();
    }
}
