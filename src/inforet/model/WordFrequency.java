package inforet.model;

/**
 * Created by johnuiterwyk on 27/09/2014.
 */
public class WordFrequency implements Comparable<WordFrequency>{
    public WordFrequency(String word)
    {
        this.word = word;
    }

    public String word;
    public int frequency = 0;

    @Override
    public int compareTo(WordFrequency compareWord)
    {
        if(this.frequency < compareWord.frequency)
        {
            return -1;
        }else if(this.frequency > compareWord.frequency)
        {
            return 1;
        }
        return 0;
    }
}
