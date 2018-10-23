import ParserServe.Old.SourceReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SourceReaderTester {

    private List<String> expectedResult;
    @Before
    public void SetExpectedValues()
    {
        expectedResult = new ArrayList<>();

        String firstRow = "#Software: SGOS 5.4.3.7";

           expectedResult.add(firstRow);

    }

    @Test
    public void testSourceReader()
    {
        //ToDo technically we're suppose to use Mocking here because it is a Static Method,
        // just for the test we do it this way
        SourceReader.readData();
        List<String> actual = SourceReader.getData();

        Assert.assertEquals(expectedResult.get(0),actual.get(0));
    }

    //Testing reader for non existing file file
    //Passing this test means that the first has to fail
    @Test
    public void testReaderWithDiffPath()
    {
        SourceReader.readData();
        List<String> actual = SourceReader.getData();

        Assert.assertEquals(expectedResult.size(),actual.size());

    }
}
