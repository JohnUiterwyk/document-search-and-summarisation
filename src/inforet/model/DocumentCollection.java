package inforet.model;

import inforet.util.LineReader;

import java.io.*;
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

    public HashMap<Integer,Document> getDocuments()
    {
        return documents;
    }
    public Document getDocumentByIndex(Integer index, Boolean textRequired)
    {
        Document doc = documents.get(index);
        //first look for the document in the list
        if(doc.getHeadline() == "" && textRequired)
        {
            try
            {
            // Open the file for reading.
            RandomAccessFile randomFile = new RandomAccessFile(pathToCollection, "r");
            // Create wrapper for doc parser
            LineReader reader = new LineReader(randomFile);

            //seek to the location
            randomFile.seek(doc.getFileOffset());
            //parse the next document
            parseNextDocument(reader,doc);
            //close the file
            randomFile.close();
            }catch (IOException ex) {
                System.out.println("IOException: "+ex.getMessage());
            }
            System.out.println("   ***Done with reading from a random access binary file.");
        }
        return doc;
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


        long position = 0l;
        float collectionLength = 0f;
        float averageDocumentLength = 0f;
        Boolean endOfFile = false;
        while(endOfFile == false)
        {
            try
            {
                Document document = parseNextDocument(lineReader);
                document.setIndex(documents.size());
                document.setFileOffset(position);

                documents.put(document.getIndex(), document);
                collectionLength += document.getBodyTextLength();
                position += document.getRawLength();
            }catch (IOException ex)
            {
                endOfFile = true;
                //System.err.print(ex.getMessage());
            }
        }
        try
        {
            fileInputStream.close();
        }catch (IOException ex){}
        averageDocumentLength = collectionLength / documents.size();



        //update all documents with their weight
        for (Map.Entry<Integer, Document> entry : documents.entrySet())
        {
            Document document = entry.getValue();
            document.setWeight(this.calcWeight(document.getBodyTextLength(),averageDocumentLength));
            //calculate document weight
        }
    }
    public float calcWeight(int docLength, float averageLength)
    {
        float k1 = 1.2f;
        float b = 0.75f;
        return Math.round(k1 * ((1-b)+(b*docLength)/averageLength));

    }

    /***
     *
     * @param reader
     * @return
     * @throws IOException
     */
    public Document parseNextDocument(LineReader reader) throws IOException
    {
        return parseNextDocument(reader,new Document());
    }
    public Document parseNextDocument(LineReader reader, Document doc) throws IOException
    {
        long totalLength = 0;
        Boolean inDoc = false;
        Boolean inHeadline = false;
        Boolean inText = false;
        String newLine = "\n";
        String characterEncoding = "US-ASCII";

        int newLineLength = newLine.getBytes(characterEncoding).length;

        do {
            String line = reader.readLine();
            if(line == null)throw new IOException();

            //add the line length and +1 for the newline to the total.
            totalLength += line.getBytes(characterEncoding).length +newLineLength;
            if(line.length() > 0)
            {
                //and check if the word is tag
                if (line.charAt(0) == '<')
                {
                    //fist split the line on spaces and dashes
                    //TODO: parse <p> tags as double newlines
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
        doc.setRawLength(totalLength);
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
            documents.put(Integer.valueOf(doc.getIndex()),doc);
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
