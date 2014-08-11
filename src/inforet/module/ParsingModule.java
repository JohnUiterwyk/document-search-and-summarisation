package inforet.module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

/**
 * Created by johnuiterwyk on 11/08/2014.
 */
public class ParsingModule
{
    private List<String> docIds = new ArrayList<String>();

    public ParsingModule()
    {

    }

    public void loadFile(String pathToFile)
    {
        try
        {
            FileReader fileReader = new FileReader(pathToFile);

            BufferedReader reader = new BufferedReader(fileReader);
            String line = null;
            do {
                try
                {
                    line = reader.readLine();
                    System.out.println(line);
                }catch (IOException ex)
                {
                    System.err.print(ex.getMessage());
                    line = null;

                }

            }while(line != null);
        }catch (FileNotFoundException ex)
        {
            System.err.print(ex.getMessage());
            return;
        }
    }

}
