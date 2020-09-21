package com.example.progparadigms;

// Imports from utilities
import com.example.progparadigms.utilities.PokeAPI;
import com.example.progparadigms.utilities.Pokemon;

// External dependency for images in app
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText textField;
    private Button searchButton;
    private Button resetButton;
    private ImageView imageView;
    public ArrayList<Pokemon> pokedex = new ArrayList<Pokemon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On App start up, populate pokemon list
        makeNetworkSearchQuery();

        // Connect with visual elements in activity_main.xml (View)
        textView = (TextView) findViewById(R.id.textView);
        textField = (EditText) findViewById(R.id.textField);
        searchButton = (Button) findViewById(R.id.searchButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        // Used to reset list header
        final String defaultDisplayText = textView.getText().toString();

        // Button OnClickListeners
        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        // Reset list header
                        textView.setText(defaultDisplayText);

                        // Reset textField to empty
                        textField.setText("");

                        // Reset to output list of Pokemon
                        makeNetworkSearchQuery();
                    } // end of onClick
                } // end of View.OnClickListener
        ); // end of resetButton.setOnClickListener

        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String query = textField.getText().toString().toLowerCase();
                        String countries = textView.getText().toString();
                        String[] countriesList = countries.split("\n");
                        String url = "";
                        textView.setText("");
                        boolean noResults = true;
                        boolean imageSet = false;

                        for (Pokemon pokemon : pokedex) {
                            if (pokemon.name.toLowerCase().contains(query)) { // allows search to not be exact
                                noResults = false;
                                textView.append(pokemon.num + " " + pokemon.name + "\n\n");

                                if (!imageSet) { // this is so if multiple hits, first Image shown
                                    try {
                                        url = pokemon.spriteURL;
                                        Picasso.get().load(url).into(imageView); // populate first hit Image
                                    } catch (Exception e) {

                                    }
                                    imageSet = true; // switch to true so Image not overwritten
                                }
                            }
                        }

                        if (noResults) {
                            try {
                                url = "https://archive-media-1.nyafuu.org/vp/image/1501/41/1501419430416.gif";
                                Picasso.get().load(url).into(imageView); // show ? Image if no pokemon found
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            textView.setText("No results match.");
                        }
                    } // end of onClick
                } // end of View.OnClickListener
        ); // end of searchButton.setOnClickListener
    } // end of onCreate

    public void makeNetworkSearchQuery() {
        new FetchNetworkData().execute();
    } // end of makeNetworkSearchQuery

    public class FetchNetworkData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL listURL = PokeAPI.createURL("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json");
            String pokemonListString = null;

            try {
                pokemonListString = PokeAPI.getResponseFromUrl(listURL); // request data from Pokemon JSON
            } catch (IOException e) {
                e.printStackTrace();
            }

            return pokemonListString;
        } // end of doInBackground

        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            pokedex = PokeAPI.generatePokemon(responseData);

            for (Pokemon pokemon : pokedex) {
                textView.append("\n\n" + pokemon.num + " " + pokemon.name); // list pokemon using num + name
            }

            try {
                Picasso.get().load(pokedex.get(0).spriteURL).into(imageView); // generate first Image as default
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // end of onPostExecute
    } // end of FetchNetworkData

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    } // end of onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.menu_info) {
            Class destinationActivity = InfoActivity.class;

            Intent startAboutActivityIntent = new Intent(MainActivity.this, destinationActivity);
            String msg = textField.getText().toString();
            startAboutActivityIntent.putExtra(Intent.EXTRA_TEXT, msg);

            startActivity(startAboutActivityIntent);
            Log.d("info", "About Activity launched");
        }

        return true;
    } // end of onOptionsItemSelected
} // end of class MainActivity