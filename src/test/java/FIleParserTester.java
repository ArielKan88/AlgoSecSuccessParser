import ParserServe.MyFileParser;
import java.util.concurrent.ConcurrentHashMap;

import ParserServe.SourceReader;
import org.junit.*;

public class FIleParserTester {

    private ConcurrentHashMap<String,Integer> excpectedResult;
    private ConcurrentHashMap<String,Integer> mapForSecondTest;

    @Before
    public void setExcpectedResults()
    {
        excpectedResult = new ConcurrentHashMap<>();
        excpectedResult.put("www.google-analytics.com",24);
        excpectedResult.put("clients1.google.com",18);
        excpectedResult.put("www.google.com",16);
        excpectedResult.put("t3.gstatic.com",15);

        mapForSecondTest = new ConcurrentHashMap<>();

    }


    @Test
    public void testFileParser()
    {
        SourceReader.readData();
        MyFileParser parser = new MyFileParser();
        parser.ParseData();
        ConcurrentHashMap<String,Integer> actualMap = parser.getCountingMap();

        Assert.assertEquals(excpectedResult.get("www.google-analytics.com"),actualMap.get("www.google-analytics.com"));
        Assert.assertEquals(excpectedResult.get("clients1.google.com"),actualMap.get("clients1.google.com"));
        Assert.assertEquals(excpectedResult.get("www.google.com"),actualMap.get("www.google.com"));
        Assert.assertEquals(excpectedResult.get("t3.gstatic.com"),actualMap.get("t3.gstatic.com"));
    }

    //If this test passes first must fail
    @Test
    public void testFileParserWithNonExistingFile()
    {
        MyFileParser parser = new MyFileParser();

        ConcurrentHashMap<String,Integer> actualMap = parser.getCountingMap();


        Assert.assertEquals(0,actualMap.size());
        Assert.assertEquals(0,mapForSecondTest.size());
    }

}
