package ParserServe.ForFutureExpansionHttp;

import ParserServe.IDataParser;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class MyHttpParser implements IDataParser {

    LocalDateTime counterLastUpdate;

    private ConcurrentHashMap<String,Integer> countingMap;

    public MyHttpParser()
    {
        countingMap = new ConcurrentHashMap<>();
    }
    //ToDo Implement method for HTTP input source, get source URL from properties file
    public ConcurrentHashMap ParseData() {
        return countingMap;
    }
}
