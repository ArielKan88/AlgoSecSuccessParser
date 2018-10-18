/**
 * The role of the IDataParser interface is to help
 * navigate between the different available parsers (and allows future expansion
 */



package ParserServe;

import java.util.concurrent.ConcurrentHashMap;

public interface IDataParser {

    ConcurrentHashMap ParseData();
}
