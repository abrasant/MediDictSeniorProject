package com.app.medi_dict_senior_project.logic;

import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.medi_dict_senior_project.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class DictionarySearch extends AppCompatActivity {


    private final EditText query = findViewById(R.id.dictSearch);
    private final TextView queryOutput = findViewById(R.id.dictDefinition);
    private final File dictFile = new File("dictionary.txt");
    private boolean isLoaded = false;
    private final HashMap<String, String> dictMap = new HashMap<>(1000);;

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
