package inforet.model;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class DocumentMap
{
    public static String mapFileName = "map";

    private HashMap<Integer,DocumentMapEntry>  map = new HashMap<Integer,DocumentMapEntry>();

    public DocumentMap()
    {

    }

    public int getSize()
    {
        return map.size();
    }

    public void addDocumentMapEntry(Integer index, String identifier, Float weight, long fileOffset)
    {
        DocumentMapEntry mapEntry = new DocumentMapEntry(index,identifier,weight,fileOffset);
        map.put(index, mapEntry);
    }
    public void saveToFile()
    {
        //convert the doc map to a string using the format  docIndex,docNo\n
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(mapFileName));

            for (Map.Entry<Integer, DocumentMapEntry> entry : map.entrySet())
            {
                DocumentMapEntry docMapEntry = entry.getValue();

                stringBuilder.append(docMapEntry.index);
                stringBuilder.append(",");
                stringBuilder.append(docMapEntry.identifier);
                stringBuilder.append(",");
                stringBuilder.append(docMapEntry.weight);
                stringBuilder.append(",");
                stringBuilder.append(docMapEntry.fileOffset);
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
    public void loadFromFile(String pathToFile)
    {
        //empty the hashmap first
        map.clear();

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
            DocumentMapEntry entry = new DocumentMapEntry(
                    Integer.valueOf(lineData[0]),
                    lineData[1],
                    Float.valueOf(lineData[2]),
                    Long.valueOf(lineData[3])
            );
            map.put(entry.index,entry);
            try
            {
                line = reader.readLine();
            }catch (IOException ex)
            {
                System.err.println("IO exception.");
                line= null;
            }
        }
    }
}
