package inforet.util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnuiterwyk on 14/08/2014.
 *
 * References:
 * 1. http://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
 *
 */
public class MapFileManager  {
    public static String mapFileName = "map";

    public MapFileManager() {
    }

    public void saveDocIdMap(List<String> docIdMap)
    {
        //convert the doc map to a string using the format  docIndex,docNo\n
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(mapFileName));

            for (int i = 0; i < docIdMap.size(); i++) {
                stringBuilder.append(i);
                stringBuilder.append(",");
                stringBuilder.append(docIdMap.get(i));
                stringBuilder.append(lineSeparator);

            }
            writer.write( stringBuilder.toString());
            stringBuilder.setLength(0);

        }
        catch (IOException ex)
        {

        }
        finally
        {
            try
            {
                if (writer != null)
                    writer.close( );
            }
            catch (IOException ex)
            {

            }
        }

    }
    public List<String> loadDocIdMap(String pathToFile)
    {
        List<String> docIdMap = new ArrayList<String>();
        FileReader fileReader = null;
        try
        {
            fileReader  = new FileReader(pathToFile);
        }catch (FileNotFoundException ex)
        {
            System.err.println(pathToFile+" not found.");
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line = null;

        try
        {
            line = reader.readLine();
        }catch (IOException ex)
        {
            System.err.println("IO exception.");
        }
        while(line != null)
        {
            String[] lineData =  line.split(",");
            docIdMap.add(Integer.parseInt(lineData[0]), lineData[1]);
            try
            {
                line = reader.readLine();
            }catch (IOException ex)
            {
                System.err.println("IO exception.");
                line= null;
            }
        }
        return docIdMap;

    }
}
