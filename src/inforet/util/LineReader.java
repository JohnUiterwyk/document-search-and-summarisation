package inforet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

/**
 * Created by johnuiterwyk on 20/09/2014.
 */
public class LineReader {


    private BufferedReader bufferedReader = null;
    private RandomAccessFile randomAccessFile= null;

    public LineReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
    public LineReader(RandomAccessFile randomAccessFile) {
        this.randomAccessFile = randomAccessFile;
    }
    public String readLine() throws IOException
    {
        if(bufferedReader != null)
        {
            return bufferedReader.readLine();

        }else if(randomAccessFile != null)
        {
            return randomAccessFile.readLine();
        }else
        {
            return null;
        }


    }

}
