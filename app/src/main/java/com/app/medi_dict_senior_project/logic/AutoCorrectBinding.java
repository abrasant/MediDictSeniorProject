package com.app.medi_dict_senior_project.logic;

import android.provider.UserDictionary;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;


public class AutoCorrectBinding extends AppCompatActivity implements View.OnClickListener {

    /**
        input: String, a .csv file with no comma's and newline markers for each delineation
        returns: linkedlist<String>, each word parsed through in iteratible list

        this simply parses a dictionary and retrieves each word from the .csv file and places them
        into a linked list.

        @param csvFile a csv file containing words for auto correct library
        @return LinkedList<String> a list containing parsed words.
     */
    public LinkedList<String> dictParser(String csvFile){

        LinkedList<String> out = new LinkedList<String>();

        try{
            File file = new File(csvFile);
            FileReader fileRead = new FileReader(file);
            BufferedReader fb = new BufferedReader(fileRead);

            String word = "";

            while((word = fb.readLine()) != null){
                out.add(word);
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return out;
    }

    @Override
    public void onClick(View view) {

        try {
            File file = new File("com.app.medi_dict_senior_project.res.AutoCorrectStatus.txt");
            FileReader fr = new FileReader(file);

            int value = fr.read();
            if ((char) value == '1'){
                //TODO Add error output for autocorrect library already being installed.
                return;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        LinkedList<String> words = new LinkedList<String>();

        words = dictParser("medical_dict_autocorrect_support.csv");

        for (String word : words) {
            UserDictionary.Words.addWord(getApplicationContext(), word, 1, "", Locale.ENGLISH);
        }
    }
}
