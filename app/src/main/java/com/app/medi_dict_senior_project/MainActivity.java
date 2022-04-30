package com.app.medi_dict_senior_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.medi_dict_senior_project.logic.DictionarySearch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.medi_dict_senior_project.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Button submitQuery = findViewById(R.id.submitQuery);
    private final EditText query = findViewById(R.id.dictSearch);
    private final TextView queryOutput = findViewById(R.id.dictDefinition);
    private final File dictFile = new File("dictionary.txt");
    private boolean isLoaded = false;
    private final HashMap<String, String> dictMap = new HashMap<>(1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



        //initializes the dictionary lookup system.

        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeHashMap();
            }
        });
    }
    public void initializeHashMap () {
        if(!isLoaded) {
            try (InputStream stream = getAssets().open(String.valueOf(dictFile))){
                Scanner scan = new Scanner(stream);

                while(scan.hasNext()){
                    String key = scan.next();
                    String val = scan.nextLine();

                    dictMap.put(key, val);
                }

                isLoaded = true;

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void search() {
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow((getCurrentFocus() == null) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String sKey = query.getText().toString().trim();

        String out = dictMap.get(sKey.toLowerCase());
        if (out != null){
            queryOutput.setText(out.toString());
        }
        else queryOutput.setText("Word not contained in dictionary");
    }



}