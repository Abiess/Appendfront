package com.example.psnstatistivs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NewItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner Ispayedforabdellahspinner;
    Spinner Ispayedforyassinespinner;
    Spinner codepricespinner;
    EditText bittEditTExt;
    EditText firstname;
    EditText secondname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addnew);


        Ispayedforabdellahspinner = findViewById(R.id.Ispayedforabdellah);

        Ispayedforyassinespinner = findViewById(R.id.Ispayedforyassine);

        codepricespinner = findViewById(R.id.codePriceSpinner);

        bittEditTExt = findViewById(R.id.BillE);
        firstname = findViewById(R.id.etNewTask);
        secondname = findViewById(R.id.firstnameE);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ispayedspinner, android.R.layout.simple_expandable_list_item_1);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.codepricespinner, android.R.layout.simple_expandable_list_item_1);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Ispayedforabdellahspinner.setAdapter(adapter);
        Ispayedforyassinespinner.setAdapter(adapter);
        codepricespinner.setAdapter(adapter1);
        codepricespinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        bittEditTExt.setText(calcutebill(parent.getItemAtPosition(position).toString()));

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


    }
    private String calcutebill (String codeprice) {
        if (codeprice.equals("5")) {
            return "60";
        }
        if (codeprice.equals("10")) {
            return "110";
        }
        if (codeprice.equals("15")) {
            return "160";
        }
        if (codeprice.equals("20")) {
            return "215";
        }
        if (codeprice.equals("25")) {
            return "260";
        }
        if (codeprice.equals("30")) {
            return "310";
        }
        if (codeprice.equals("35")) {
            return "360";
        }
        if (codeprice.equals("40")) {
            return "410";
        }
        if (codeprice.contains("50")) {
            return "510";
        }
        return "thinking";
    }





    public void send(View view) throws JSONException {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        // perform HTTP POST request


            new HTTPAsyncTask().execute(hgh());

    }



    private JSONObject hgh() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("url", "https://flaskapi.eastus.cloudapp.azure.com/psnstatistik");
        jsonObject.accumulate("firstname", firstname.getText().toString());
        jsonObject.accumulate("secondname",  secondname.getText().toString());
        jsonObject.accumulate("payedForyassine",  Ispayedforyassinespinner.getSelectedItem().toString());
        jsonObject.accumulate("payedForme",  Ispayedforabdellahspinner.getSelectedItem().toString());
        jsonObject.accumulate("codePrice",  codepricespinner.getSelectedItem().toString());
        jsonObject.accumulate("bill",  bittEditTExt.getText().toString());
        Log.d("meine string", jsonObject.toString());


        return jsonObject;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
