package ParserServe;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class MyHttpReader implements DataSource {

    LocalDateTime counterLastUpdate;

    private ConcurrentHashMap<String,Integer> countingMap;

    public MyHttpReader()
    {
        countingMap = new ConcurrentHashMap<>();
    }
    //ToDo Implement method for HTTP input source, get source URL from properties file
    public ConcurrentHashMap ParseData() {
        return countingMap;
    }
}
