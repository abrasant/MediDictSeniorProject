package com.app.medi_dict_senior_project;

import android.content.Context;
import android.provider.UserDictionary;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;


public class AutoCorrectBinding extends AppCompatActivity implements View.OnClickListener {

    Context context;

    public AutoCorrectBinding(Context c){
        this.context = c;
    }
    /**
        input: String, a .csv file with no comma's and newline markers for each delineation
        returns: linkedlist<String>, each word parsed through in iteratible list

        this simply parses a dictionary and retrieves each word from the .csv file and places them
        into a linked list.

        @param csvFile a csv file containing words for auto correct library
        @return LinkedList<String> a list containing parsed words.
     */
    public LinkedList<String> dictParser(String csvFile){

        LinkedList<String> out = new LinkedList<>();

        try{
            InputStream inputStream = context.getAssets().open("AutoCorrectBinding.txt");
            Scanner scan = new Scanner(inputStream);

            while(scan.hasNext()){
                out.add(scan.next());
            }

            scan.close();
            inputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        return out;
    }

    @Override
    public void onClick(View view) {

        try {
            InputStream inputStream = context.getAssets().open("AutoCorrectBinding.txt");
            Scanner scan = new Scanner(inputStream);

            if (scan.next().equals("1")){
                System.out.println("Already Installed...");
                return;
            }

            scan.close();
            inputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        LinkedList<String> words;

        words = dictParser("medical_dict_autocorrect_support.txt");

        for (String word : words) {
            UserDictionary.Words.addWord(getApplicationContext(), word, 1, "", Locale.ENGLISH);
        }
    }
}
