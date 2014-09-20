package inforet.model;

import inforet.module.TermNormalizer;
import inforet.util.LineReader;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class DocumentCollection
{
    public static String mapFileName = "map";

    private String pathToCollection;
    private HashMap<Integer,Document> documents = new HashMap<Integer,Document>();

    public DocumentCollection()
    {
    }
    public void setPathToCollection(String pathToCollection)
    {
        this.pathToCollection = pathToCollection;
    }

    public Collection<Document> getDocuments()
    {
        return documents.values();
    }
    public Document getDocumentByIndex(Integer index, Boolean textRequired)
    {
        Document result = documents.get(index);
        //first look for the document in the list
        if(result.getHeadline() == "" && textRequired)
        {
            //setup RandomAccessReader and run parseNextDocument
        }
        return result;
    }

    public void parseCollection()
    {
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try
        {
             fileInputStream = new FileInputStream(new File(pathToCollection));
        }catch (FileNotFoundException ex)
        {
            System.err.println("IO Error reading "+ pathToCollection);
        }
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        LineReader lineReader = new LineReader(bufferedReader);


        Long position = 0l;
        Boolean endOfFile = false;
        while(endOfFile == false)
        {
            try
            {
                Document document = parseNextDocument(lineReader);
                document.setIndex(documents.size());
                document.setFileOffset(position);

                documents.put(document.getIndex(), document);

                position += document.getSize();
            }catch (IOException ex)
            {
                endOfFile = true;
                System.err.print(ex.getMessage());
            }
        }
    }
    public Document parseNextDocument(LineReader reader) throws IOException
    {
        return parseNextDocument(reader,new Document());
    }
    public Document parseNextDocument(LineReader reader, Document doc) throws IOException
    {
        int totalLength = 0;
        Boolean inDoc = false;
        Boolean inHeadline = false;
        Boolean inText = false;
        do {
            String line = reader.readLine();
            if(line == null)throw new IOException();
            totalLength += line.length()+1;
            if(line.length() > 0)
            {
                //and check if the word is tag
                if (line.charAt(0) == '<')
                {
                    //fist split the line on spaces and dashes
                    if (line.startsWith("<DOC>"))
                    {
                        inDoc = true;
                    }
                    else if (line.startsWith("</DOC>"))
                    {
                        inDoc = false;
                    }
                    else if (line.startsWith("<DOCNO>"))
                    {
                        doc.setIdentifier(Arrays.asList(line.split("[\\s]")).get(1));
                    }
                    else if (line.startsWith("<HEADLINE>"))
                    {
                        inHeadline = true;
                    }
                    else if (line.startsWith("</HEADLINE>"))
                    {
                        inHeadline = false;
                    }
                    else if (line.startsWith("<TEXT>"))
                    {
                        inText = true;
                    }
                    else if (line.startsWith("</TEXT>"))
                    {
                        inText = false;
                    }
                } else if (inDoc) {
                    if(inText)
                    {
                        doc.appendLineToBody(line);
                    }else if(inHeadline)
                    {
                        doc.setHeadline(line);
                    }
                }
            }
        }while (inDoc == true);
        doc.setSize(Long.valueOf(totalLength));
        return doc;
    }

    public void loadFromMapFile(String pathToFile)
    {
        //empty the hashmap first
        documents.clear();

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
            Document doc = new Document();
            doc.setIndex(Integer.valueOf(lineData[0]));
            doc.setIdentifier(lineData[1]);
            doc.setWeight(Float.valueOf(lineData[2]));
            doc.setFileOffset(Long.valueOf(lineData[3]));
            documents.put(doc.getIndex(),doc);
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
    public void saveMapToFile()
    {
        //convert the doc map to a string using the format  docIndex,docNo\n
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(mapFileName));

            for (Map.Entry<Integer, Document> entry : documents.entrySet())
            {
                Document document = entry.getValue();

                stringBuilder.append(document.getIndex());
                stringBuilder.append(",");
                stringBuilder.append(document.getIdentifier());
                stringBuilder.append(",");
                stringBuilder.append(document.getWeight());
                stringBuilder.append(",");
                stringBuilder.append(document.getFileOffset());
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

}
