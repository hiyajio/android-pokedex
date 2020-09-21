package com.example.progparadigms.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pokemon {
    public String num;
    public String name;
    public ArrayList<String> types;
    public String spriteURL;

    public Pokemon(String num, String name, ArrayList<String> types, String spriteURL) {
        this.num = num;
        this.name = name;
        this.types = types;
        this.spriteURL = spriteURL;
    } // end of Pokemon(String num, String name, ArrayList<String> types, String spriteURL)

    public Pokemon(JSONObject pokeJSON) {
        try {
            // Grab pokemon Numbers
            String num = pokeJSON.getString("num");

            // Grab pokemon Names
            String name = pokeJSON.getString("name");

            ArrayList<String> types = new ArrayList<String>();
            // Grab pokemon Types
            JSONArray typesArray = pokeJSON.getJSONArray("type");

            // Accumulate types since most pokemon have more than one
            for (int i = 0; i < typesArray.length(); i++) {
                String typeString = typesArray.getString(i);
                types.add(typeString);
            }

            // Grab Image URLs
            String spriteURLString = pokeJSON.getString("img");

            // Make http links to https so no error with loading Image
            char secureChar = 's';
            StringBuilder stringBuilder = new StringBuilder(spriteURLString);
            spriteURLString = spriteURLString.substring(0, 4) + secureChar + spriteURLString.substring(4);

            this.num = num;
            this.name = name;
            this.types = types;
            this.spriteURL = spriteURLString;

        } catch(JSONException e) {
            e.printStackTrace();
        }
    } // end of Pokemon(JSONObject pokeJSON)
} // end of class Pokemon
