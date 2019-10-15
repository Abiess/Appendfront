package com.example.psnstatistivs;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class NewTodo extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    DatePickerDialog picker;

    EditText Subject;
    EditText Description;
    Spinner AssignedToDropDown;
     Button newuserButton;
     Spinner TodoStatusDropDown;
     EditText  DeadlineDate;
    @Override
    public void onItemSelected(AdapterView<?>
                                           adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        DeadlineDate = findViewById(R.id.DeadlineDate);
        Subject = findViewById(R.id.Subject);
        Description = findViewById(R.id.Description);
        AssignedToDropDown = findViewById(R.id.AssignedToDropDown);
        newuserButton = findViewById(R.id.newuserButton);
        TodoStatusDropDown = findViewById(R.id.TodoStatusDropDown);
        DeadlineDate.setInputType(InputType.TYPE_NULL);


        DeadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NewTodo.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view,
                                                  int year, int monthOfYear,
                                                  int dayOfMonth) {
                                DeadlineDate.setText(dayOfMonth + "/" +
                                        (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }




        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_dropdown, android.R.layout.simple_expandable_list_item_1);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.AssignedTome_dropdown, android.R.layout.simple_expandable_list_item_1);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        TodoStatusDropDown.setAdapter(adapter);
        AssignedToDropDown.setAdapter(adapter1);


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               finish();
               //onbackpress()
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void send(View view) throws JSONException {

        new HTTPAsyncTask().execute(hgh());

    }

    private JSONObject hgh() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("url", "https://flaskapi.eastus.cloudapp.azure.com/todo");
        jsonObject.accumulate("Subject", Subject.getText().toString());
        jsonObject.accumulate("Description",  Description.getText().toString());
        jsonObject.accumulate("TodoStatus",  TodoStatusDropDown.getSelectedItem().toString());
        jsonObject.accumulate("AssignedTo",  AssignedToDropDown.getSelectedItem().toString());
        jsonObject.accumulate("User_id",  1);
        jsonObject.accumulate("Username",  "Abdellah");
        Log.d("meine string", jsonObject.toString());


        return jsonObject;
    }





}
