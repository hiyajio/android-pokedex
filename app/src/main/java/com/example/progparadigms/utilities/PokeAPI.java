package com.example.progparadigms.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class PokeAPI { // essentially NetworkUtils file
    public static ArrayList<Pokemon> generatePokemon(String pokemonListString) {
        ArrayList<Pokemon> pokedex = new ArrayList<Pokemon>();

        try {
            JSONObject pokemonListContainer = new JSONObject(pokemonListString);
            JSONArray pokemonList = pokemonListContainer.getJSONArray("pokemon");

            // Create pokedex by calling pokemon multiple times
            for (int i = 0; i < pokemonList.length(); i++) {
                JSONObject pokeData = pokemonList.getJSONObject(i);
                Pokemon pokemon = new Pokemon(pokeData);

                pokedex.add(pokemon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pokedex;
    } // end of generatePokemon

    public static URL createURL(String str) { // given createURL function
        URL listURL = null;

        try {
            listURL = new URL(str);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        return listURL;
    } // end of createURL

    public static String getResponseFromUrl(URL url) throws IOException { // given getResponseFromUrl function
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput) return scanner.next();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    } // end of getResponseFromUrl
} // end of class PokeAPI
