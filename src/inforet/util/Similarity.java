package inforet.util;

/**
 * Created by johnuiterwyk on 26/09/2014.
 */
public class Similarity
{
    public static float GetBm25Score(String term, int docCount, int inDocTermFreq, int docFrequency , float docWeight, float k1, float b)
    {
        float score = 0.0f;
        float bigK = k1 * ((1-b) + (b*docWeight));
        score += Math.log(( (docCount - docFrequency + 0.5)/(docFrequency + 0.5) ) *( ((k1+1)*inDocTermFreq)/(bigK + inDocTermFreq)) );
        return score;

    }


}
