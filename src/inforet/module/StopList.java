package inforet.module;

import inforet.util.IndexArgs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Daniel on 12/08/2014.
 */
public class StopList {

    // Load StopList on First Call
    HashMap<Integer, String> stopList = null;

    // Model ======================================================
    public StopList() {
    }

    private  Boolean enabled = false;
    public  void setEnabled() {
        this.enabled = true;
    }
    public Boolean isEnabled() {
        return enabled;
    }


    public HashMap<Integer, String> getStopList() {
        return stopList;
    }

    public void setStopList(HashMap<Integer, String> stopList) {
        this.stopList = stopList;
    }


    // Normalise Stoplist Transform
    public static String normalize(String s){
        return s.trim().toLowerCase();
    }

    public static Integer hashKey(String s){
        Integer i = normalize(s).hashCode();
        return i;
    }

    // Initialise Stop List ======================================
    public void initStopList(String stopFile){

        //Read File
        try {
            BufferedReader br = new BufferedReader((new FileReader(stopFile)));
            String word;

            while (( word = br.readLine()) != null ){
                // Processing the Stop Word
                insertStopWord(word);
            }
            setEnabled();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void insertStopWord(String word){
        if (this.getStopList() == null){
            this.setStopList( new HashMap<Integer, String>());
        }
        Integer hashKey = Integer.valueOf( word.toLowerCase().trim().hashCode() );
        // Check hashMap for existing
        if ( stopList.get( hashKey )  == null){
            // Insert if not existing
            this.getStopList().put(hashKey, word);
        }
    }

    public Boolean contains(String word)
    {
        return stopList.containsKey(StopList.hashKey(word));
    }

    // CRUD Interface for StopList
    // TODO Create CRUD methods.


}
