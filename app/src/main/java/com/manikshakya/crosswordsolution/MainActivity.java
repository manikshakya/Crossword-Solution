package com.manikshakya.crosswordsolution;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    // Create exception for number (All Integer). When you have time.
    // ProgressBar not working.

    RelativeLayout layout;
    TextView appName;
    EditText myText;

    Button searchButton;
    Button exit;


    public void exitApplication(View view){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);


    }

    public void showResult(View view){
        if(!myText.getText().toString().trim().equals("")){
            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
            i.putExtra("userdata", myText.getText().toString());
            ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

            progressBar1.setIndeterminate(true);
            progressBar1.animate();
            progressBar1.setVisibility(View.VISIBLE);

            appName.setVisibility(View.GONE);
            myText.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
            startActivity(i);
        }else{
            Toast.makeText(this, "It cannot be Empty.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.mainLayout || view.getId() == R.id.appName){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.mainLayout);
        appName = (TextView) findViewById(R.id.appName);
        myText = (EditText) findViewById(R.id.editText);
        searchButton = (Button) findViewById(R.id.submit);
        exit = (Button) findViewById(R.id.exit);





        layout.setOnClickListener(this);
        appName.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        myText.getText().clear();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
