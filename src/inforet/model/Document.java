package inforet.model;

import inforet.module.TermNormalizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by johnuiterwyk on 20/09/2014.
 * The DocInfo class contains information that will be stored in the map
 * This includes the index, a the identifier string, and the wieght
 */
public class Document {


    private Integer index = -1;
    private String identifier;

    private Long fileOffset;



    private Long sizeOfDoc;


    private String headline = "";
    private StringBuilder bodyTextBuilder = new StringBuilder();
    private Float weight = 0.0f;

    public Document() {
    }

    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getSize() {
        return sizeOfDoc;
    }

    public void setSize(Long size) {
        this.sizeOfDoc = size;
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

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }


    public Long getFileOffset() {
        return fileOffset;
    }

    public void setFileOffset(Long fileOffset) {
        this.fileOffset = fileOffset;
    }

    public String getBodyText() {
        return bodyTextBuilder.toString();
    }
    public int getBodyTextLength() {
        return bodyTextBuilder.length();
    }

    public void appendLineToBody(String text) {
        this.bodyTextBuilder.append(text);
        this.bodyTextBuilder.append("\n");
    }

    public List<String> getListOfWords() {
        List<String> words = new ArrayList<String>();
        words.addAll(Arrays.asList(TermNormalizer.stringToTerms(getHeadline())));
        words.addAll(Arrays.asList(TermNormalizer.stringToTerms(getBodyText())));
        return words;
    }
}
