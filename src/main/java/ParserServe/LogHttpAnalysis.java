package ParserServe;

public class LogHttpAnalysis  extends LogDataParser{


    public LogHttpAnalysis()
    {
        super();
        source = new MyHttpReader();
    }
}
