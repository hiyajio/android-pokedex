package com.example.progparadigms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {
    private Button nianticButton;
    private Button jsonButton;
    private Button ndMapButton;
    private Button jioButton;
    private Button zachButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Connect with visual elements in activity_main.xml (View)
        nianticButton = (Button) findViewById(R.id.openNianticLink);
        jsonButton = (Button) findViewById(R.id.openJSONLink);
        ndMapButton = (Button) findViewById(R.id.openMapLink);
        jioButton = (Button) findViewById(R.id.openJioLink);
        zachButton = (Button) findViewById(R.id.openZachLink);

        // Set up URL links for buttons
        final String nianticLink = "https://nianticlabs.com/en/";
        final String jsonLink = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
        final String jioLink = "https://jbuenviaje.com/";
        final String zachLink = "https://zacharysy.net/";

        // Button OnClickListeners
        nianticButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWebPage(nianticLink); // open nianticLink
                    }
                } // end of View.OnClickListener
        ); // end of nianticButton.setOnClickListener

        jsonButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWebPage(jsonLink); // open jsonLink
                    }
                } // end of View.OnClickListener
        ); // end of jsonButton.setOnClickListener

        ndMapButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openMap(); // call openMap() function
                    }
                } // end of View.OnClickListener
        ); // end of ndMapButton.setOnClickListener

        jioButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWebPage(jioLink); // open jioLink
                    }
                } // end of View.OnClickListener
        ); // end of jioButton.setOnClickListener

        zachButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWebPage(zachLink); // open zachLink
                    }
                } // end of View.OnClickListener
        ); // end of zachButton.setOnClickListener
    } // end of onCreate

    public void openWebPage(String urlString) { // given openWebPage function
        Uri webpage = Uri.parse(urlString);
        Intent openWebpageIntent = new Intent(Intent.ACTION_VIEW, webpage);

        try {
            startActivity(openWebpageIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    } // end of openWebPage

    public void openMap() { // given openMap function
        String addressString = "University of Notre Dame, IN";
        Uri addressUri = Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q", addressString).build();

        Intent openMapIntent = new Intent(Intent.ACTION_VIEW);
        openMapIntent.setData(addressUri);

        try {
            startActivity(openMapIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    } // end of openMap
} // end of class InfoActivity