package com.example.assignment4attempt1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    //declare buttons and edit texts here
    Button btnAdd, btnSubtract;

    EditText txtBalance, txtDate, txtAmount, txtNote;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //counter for number of history entries
        final int[] counter = {-1};

        //associate variable EditTexts with their view components
        txtBalance = findViewById(R.id.txtBalance);
        txtDate = findViewById(R.id.txtDate);
        txtAmount = findViewById(R.id.txtAmount);
        txtNote = findViewById(R.id.txtNote);

        //associate variable Buttons with their view components
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);

        //set date field
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        txtDate.setText(formattedDate);

        //pull strings from shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String balance = preferences.getString("Balance", "100");
        String amount = preferences.getString("Amount", "");
        String note = preferences.getString("Note", "");

        //set strings to shared preferences
        txtBalance.setText(balance);
        txtAmount.setText(amount);
        txtNote.setText(note);

        //prep for history shared preference unloading
        LayoutInflater ltInflater = getLayoutInflater();
        LinearLayout subLayoutFields = findViewById(R.id.fieldHistory);
        View view1 = ltInflater.inflate(R.layout.history, null, false);

//        //add history views
        EditText tempHistoryEditText;
        tempHistoryEditText = view1.findViewById(R.id.txtString);
        tempHistoryEditText.setText(preferences.getString("History" + String.valueOf(1), ""));
        subLayoutFields.addView(view1);
        //prep for history shared preference unloading
        int loopcounter = preferences.getInt("Counter",0);
        int i = 0;
        while (loopcounter > i){
            ltInflater = getLayoutInflater();
            subLayoutFields = findViewById(R.id.fieldHistory);
            view1 = ltInflater.inflate(R.layout.history, null, false);
            EditText tempHistoryEditText1;
            tempHistoryEditText1 = view1.findViewById(R.id.txtString);
            tempHistoryEditText1.setText(preferences.getString("History" + String.valueOf(i), ""));
            subLayoutFields.addView(view1);
            i++;
        }

            //Add button on click method
        btnAdd.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          //Declare Temporary Variables
                                          EditText myHistoryString;
                                          String DoneString = "";

                                          //setup adding notes to history
                                          LayoutInflater ltInflater = getLayoutInflater();
                                          LinearLayout subLayoutFields = findViewById(R.id.fieldHistory);
                                          View view1 = ltInflater.inflate(R.layout.history, null, false);

                                          //the actual work
                                          myHistoryString = view1.findViewById(R.id.txtString);

                                          txtBalance.setText(String.valueOf(Integer.parseInt(txtBalance.getText().toString()) + Integer.parseInt(txtAmount.getText().toString())));
                                          DoneString = ("Added $" + txtAmount.getText().toString() + " on " + formattedDate + " from " + txtNote.getText().toString());

                                          myHistoryString.setText(DoneString);

                                          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                          SharedPreferences.Editor editor = preferences.edit();
                                          editor.putString("Balance",txtBalance.getText().toString());
                                          editor.putString("Amount",txtAmount.getText().toString());
                                          editor.putString("Note",txtNote.getText().toString());

                                          counter[0] += 1;
                                          editor.putString("History" + String.valueOf(counter[0]),DoneString);
                                          editor.putInt("Counter",counter[0]);
                                          editor.apply();

                                          subLayoutFields.addView(view1);
                                      }
                                  });

        //Subtract button on click method
        btnSubtract.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               //Declare Temporary Variables
                                               EditText myHistoryString;
                                               String DoneString = "";

                                               //setup adding notes to history
                                               LayoutInflater ltInflater = getLayoutInflater();
                                               LinearLayout subLayoutFields = findViewById(R.id.fieldHistory);
                                               View view1 = ltInflater.inflate(R.layout.history, null, false);

                                               //the actual work
                                               myHistoryString = view1.findViewById(R.id.txtString);

                                               txtBalance.setText(String.valueOf(Integer.parseInt(txtBalance.getText().toString()) - Integer.parseInt(txtAmount.getText().toString())));
                                               DoneString = ("Spent $" + txtAmount.getText().toString() + " on " + formattedDate + " for " + txtNote.getText().toString());

                                               myHistoryString.setText(DoneString);

                                               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                               SharedPreferences.Editor editor = preferences.edit();
                                               editor.putString("Balance",txtBalance.getText().toString());
                                               editor.putString("Amount",txtAmount.getText().toString());
                                               editor.putString("Note",txtNote.getText().toString());

                                               counter[0] += 1;
                                               editor.putString("History" + String.valueOf(counter[0]),DoneString);
                                               editor.putInt("Counter",counter[0]);
                                               editor.apply();

                                               subLayoutFields.addView(view1);
                                           }
                                       });
    }
}
