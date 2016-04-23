package org.nasaappschallenge2016.looneymoony.backend.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nasaappschallenge2016.looneymoony.backend.bean.FunFact;
import org.nasaappschallenge2016.looneymoony.backend.bean.MoonStory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vhblasco on 23/04/16.
 */
public class LMJSONParser {

    private JSONObject loadJSON(String path) {

        String jsonStr = null;
        try {


            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(path);

            //InputStream is =  LMJSONParser.class.getResourceAsStream(path);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            jsonStr = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        JSONObject json = null;
        try {
            json = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


    /***************************************************************************/
    /* STORIES */
    /***************************************************************************/
    public List<MoonStory> getMoonStory(String path){
        JSONObject jObject = loadJSON(path);

        return parseMoonStories(jObject);
    }


    private List<MoonStory> parseMoonStories(JSONObject jObject) {
        JSONArray jMoonStories = null;
        try {
            /** Retrieves all the elements in the 'stories' array */
            jMoonStories = jObject.getJSONArray("stories");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseStoriesArray(jMoonStories);
    }

    private List<MoonStory> parseStoriesArray(JSONArray jMoonStories) {

        int storiesCount = jMoonStories.length();
        List<MoonStory> moonStoriesList = new ArrayList<MoonStory>();
        MoonStory moonStory = null;

        /** Taking each MoonStory, parses and adds to list object */
        for(int i = 0; i < storiesCount; i++){
            try {
                /** Call parseMoonStory with JSON object to parse the MoonStory */
                moonStory = parseMoonStory((JSONObject)jMoonStories.get(i));
                moonStoriesList.add(moonStory);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return moonStoriesList;
    }


    private MoonStory parseMoonStory(JSONObject jStory) {
        MoonStory moonStory = null;

        try {
            moonStory = new MoonStory();

            // Extracting title, if available
            if(!jStory.isNull("title")){
                moonStory.setTitle(jStory.getString("title"));
            }

            // Extracting text, if available
            if(!jStory.isNull("text")){
                moonStory.setText(jStory.getString("text"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return moonStory;

    }

    /***************************************************************************/
    /* FUN FACTS */
    /***************************************************************************/
    public List<FunFact> getFunFacts(String path){
        JSONObject jObject = loadJSON(path);

        return parseFunFacts(jObject);
    }



    private List<FunFact> parseFunFacts(JSONObject jObject) {
        JSONArray jFacts = null;
        try {
            /** Retrieves all the elements in the 'facts' array */
            jFacts = jObject.getJSONArray("facts");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseFactsArray(jFacts);
    }

    private List<FunFact> parseFactsArray(JSONArray jFacts) {

        int factsCount = jFacts.length();
        List<FunFact> funFactsList = new ArrayList<FunFact>();
        FunFact funFact = null;

        /** Taking each Facts, parses and adds to list object */
        for(int i = 0; i < factsCount; i++){
            try {
                /** Call parseFact with JSON object to parse the FunFact */
                funFact = parseFact((JSONObject)jFacts.get(i));
                funFactsList.add(funFact);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return funFactsList;
    }

    private FunFact parseFact(JSONObject jFact) {
        FunFact fact = null;

        try {
            fact = new FunFact();

            // Extracting title, if available
            if(!jFact.isNull("title")){
                fact.setTitle(jFact.getString("title"));
            }

            // Extracting text, if available
            if(!jFact.isNull("text")){
                fact.setText(jFact.getString("text"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return fact;
    }
}
