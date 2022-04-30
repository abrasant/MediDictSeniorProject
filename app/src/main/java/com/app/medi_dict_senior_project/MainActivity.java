package com.app.medi_dict_senior_project;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.medi_dict_senior_project.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Context context;
    private Button submitQuery, pressedBtn;
    private EditText query;
    private TextView queryOutput;
    private boolean isLoaded = false;
    private HashMap<String, String> dictMap = new HashMap<>(1000);

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

        submitQuery = findViewById(R.id.submitQuery);
        queryOutput = findViewById(R.id.dictDefinition);
        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               search();
            }
        });
        query = (EditText) findViewById(R.id.dictSearch);
        pressedBtn = findViewById(R.id.dictionary);
        pressedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeHashMap();
            }
        });

        Button autocorrectBtn = findViewById(R.id.autocorrectBtn);

        autocorrectBtn.setOnClickListener(new AutoCorrectBinding(this));
    }
    public void initializeHashMap () {
        if(!isLoaded) {
            try {
                InputStream inputStream = getAssets().open("dictionary.txt");
                Scanner scan = new Scanner(inputStream);

               while(scan.hasNext()){
                    String key = scan.next().toLowerCase();
                    String val = scan.nextLine();
                    System.out.println(key + ",  " + val);
                    dictMap.put(key, val);
                    System.out.println(dictMap.get(key));
               }
                
                isLoaded = true;
                scan.close();
                inputStream.close();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void search() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((getCurrentFocus() == null) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String sKey = query.getText().toString().trim();
        System.out.println(sKey);

        String out = dictMap.get(sKey);
        System.out.println(out);
        if (out != null){
            queryOutput.setText(out);
        }
        else queryOutput.setText("Word not contained in dictionary");
    }



}