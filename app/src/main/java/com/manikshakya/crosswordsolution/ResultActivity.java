package com.manikshakya.crosswordsolution;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;



public class ResultActivity extends AppCompatActivity{

    ArrayAdapter<String> arrayAdapter;
    ListView showResults;
    ArrayList<String> results;
    ArrayList<String> store;

    public void reset(View view){
        store.clear();
        results.clear();

        arrayAdapter.notifyDataSetChanged();

        finish();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        showResults = (ListView) findViewById(R.id.showResult);

        //showResults.getEmptyView();

        Intent it = getIntent();

        String userdata = it.getStringExtra("userdata");

        int count = 0;
        for(int i = 0; i < userdata.length(); i++) {
            if(userdata.charAt(i) != ' ') {
                count++;
            }
        }

        char[] s1 = new char[count];

        for(int i = 0; i < userdata.length(); i++) {
            if(userdata.charAt(i) != ' ') {
                s1[i] = userdata.charAt(i);
            }
        }

        //showResults.setText(Arrays.toString(s1));

        Calculate.printSubsets(s1);

        ArrayList<String> solution = new ArrayList<>();
        store = Calculate.store;
        System.out.println(store.remove(0));
        //System.out.println(store);

        for(String ss1: store) {
            char[] temp1 = new char[ss1.length()];
            for(int i = 0; i < ss1.length(); i++) {
                temp1[i] = ss1.charAt(i);
            }

            //System.out.println(Arrays.toString(temp1) + "" + permute(temp1, 0) + "" + count++);
            solution.addAll(Calculate.permute(temp1, 0));

            Calculate.words = new ArrayList<>();
        }

        results = new ArrayList<>();

        //System.out.println("Solution: " + solution);

        try {


            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            try {
                // Use the line below to get the file from asset folder. If any. (getAssets().open(path))
                //br = new BufferedReader(new InputStreamReader(getAssets().open(path)));

                br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.dictionary_compact)));
                String temp;
                while ((temp = br.readLine()) != null)
                    sb.append(temp);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close(); // stop reading
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //return sb.toString();

            String filename = "dictionary_compact";
            int id = getResources().getIdentifier(filename, "raw", getPackageName());


            InputStream is = getResources().openRawResource(id);

            JSONObject json = new JSONObject(new JSONTokener(sb.toString()));


            int i = 0;

            for(String fetch : solution) {
                if(json.opt(fetch) != null) {
                    results.add(fetch);
                    //System.out.println(fetch);
                }
                //System.out.println("Fetch: " + fetch);
            }




        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
        }


        //showResults.setText(results.toString());

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        showResults.setAdapter(arrayAdapter);




        //System.out.println(results);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}

class Calculate{

    private Calculate() {

    }

    static ArrayList<String> printSubsets(char[] s) {
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i <= s.length; i++) {
            boolean[] ifPrint = new boolean[s.length];
            //printSubset(s, ifPrint, 0, i);
            printSubsets(s, ifPrint, 0, i);
        }

        return temp;
    }

    static ArrayList<String> store = new ArrayList<>();
    int count = 0;
    private static void printSubsets(char[] s, boolean[] ifPrint, int start, int remain) {
        String s1 = "";
        if(remain == 0) {

            for(int i = 0; i < ifPrint.length; i++) {
                if(ifPrint[i]) s1 += s[i];
            }
            //aptraSystem.out.println(s1);
            store.add(s1);

        }else {
            if(start + remain > s.length);
            else {
                for(int i = start; i < s.length; i++) {
                    if(!ifPrint[i]) {
                        ifPrint[i] = true;

                        printSubsets(s, ifPrint, i + 1, remain - 1);

                        ifPrint[i] = false;
                    }
                }
            }

        }

        //return s1;


        //return s1;
    }


    static ArrayList<String> words = new ArrayList<>();
    int ij = 0;
    static String s = "";
    static ArrayList<String> permute(char[] a, int k)
    {


        if (k == a.length)
        {

            for (int i = 0; i < a.length; i++)
            {
                s += a[i];
                //System.out.print(a[i]);
            }
            words.add(s);
            //System.out.println();
            s = "";

            //System.out.println(" : "+ ij++);
        }
        else
        {
            for (int i = k; i < a.length; i++)
            {
                char temp = a[k];
                a[k] = a[i];
                a[i] = temp;

                permute(a, k + 1);

                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
            //System.out.println(k);
        }
        return words;
    }
}




