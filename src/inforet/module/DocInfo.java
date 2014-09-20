package inforet.module;

/**
 * Created by johnuiterwyk on 20/09/2014.
 * The DocInfo class contains information that will be stored in the map
 * This includes the index, a the identifier string, and the wieght
 */
public class DocInfo
{


    private Integer index = -1;
    private String identifier = "";


    private Float length = 0f;
    private Float weight = 0.0f;

    public DocInfo(String identifier, Float length)
    {
        this.identifier = identifier;
        this.length = length;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getLength() {
        return length;
    }

    public String getIdentifier()
    {
        return identifier;
    }
}
