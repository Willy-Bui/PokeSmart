package com.pokesmart.pokesmart;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created by willy.bui on 08/09/2016.
 * Allows different access to JSON Pokémon DB.
 */
public class JsonDB {

    private JSONObject JSONDB;

    public JsonDB(String path_to_db) {

        try(FileInputStream inputStream = new FileInputStream(path_to_db)) {
            JSONDB = new JSONObject(getStringFromInputStream(inputStream));
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getNameFromDB (String name) throws JSONException {
        for(int i = 1; i <= JSONDB.length(); i++) {
            JSONObject currentMon = JSONDB.getJSONObject(Integer.toString(i));
            if (currentMon.get("name").equals(name)) {
                return JSONDB.getJSONObject(name);
            }
        }
        throw new JSONException("Pokémon not found in database.");
    }

    public JSONArray getNamesFromDB () throws JSONException {
        JSONArray result = new JSONArray();
        for(int i = 1; i <= JSONDB.length(); i++) {
            JSONObject currentMon = JSONDB.getJSONObject(Integer.toString(i));
            result.put(currentMon.get("name"));
        }
        return result;
    }

    public JSONObject getNumFromDB (int num) throws JSONException {

        return JSONDB.getJSONObject(Integer.toString(num));
    }

    public JSONArray getTypesFromDB (String type) throws JSONException {
        JSONArray result = new JSONArray();
        for(int i = 1; i <= JSONDB.length(); i++) {
            JSONObject currentMon = JSONDB.getJSONObject(Integer.toString(i));
            if (currentMon.get("type1").equals(type)) {
                result.put(currentMon);
            } else if (currentMon.get("type2").equals(type)) {
                result.put(currentMon);
            }
        }
        return result;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
