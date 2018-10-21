/**
 * The role of the IDataParser interface is to help
 * decouple between the different available parsers and their implementation(and allows future expansion of new parsers)
 */



package ParserServe;

public interface IDataParser {

    void ParseData();

   void sortResults();
}
