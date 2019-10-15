package com.example.psnstatistivs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    Button button;
        private String TAG = MainActivity.class.getSimpleName();
        private ListView lv;
        ArrayList<HashMap<String, String>> contactList;
        HashMap<String, String> d = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            contactList = new ArrayList<>();
            lv = findViewById(R.id.list);


            try {
                d = new GetDataFromAPI().execute("https://flaskapi.eastus.cloudapp.azure.com/psnstatistik").get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contactList.add(d);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    String selectedItem = (String) parent.getItemAtPosition(position);

                    Toast.makeText(getApplicationContext(), "Servux", Toast.LENGTH_LONG).show();

                }
            });

        }

    @Override
    protected void onResume()
    {
        super.onResume();

        contactList.clear();

        try {
            d = new GetDataFromAPI().execute("https://flaskapi.eastus.cloudapp.azure.com/psnstatistik").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contactList.add(d);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.AddUser:
                AddUser();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true ;
    }
    public void onAddFull(View view)
    {
        try {
            Intent i = new Intent(MainActivity.this, NewItem.class);

        startActivity(i);
        }
        catch (Exception e ){
            e.getMessage();
        }
    }

    public void AddUser()
    {
        Intent i = new Intent(MainActivity.this, NewItem.class);
        startActivity(i);
    }

    public void onShareClick(MenuItem item)
    {

    }

    public class GetDataFromAPI extends AsyncTask<String, Void,
            HashMap<String, String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,
              //      "Json Data is downloading", Toast.LENGTH_LONG).show();

        }


        @Override
        protected HashMap<String, String> doInBackground(String... url) {
            HttpHandler sh = new HttpHandler();


                    String jsonStr = sh.makeServiceCall(url[0]);

            HashMap<String, String> contact = new HashMap<>();
            if (jsonStr != null) {
                try {

                    // Getting JSON Array node
                    JSONArray contacts = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String bill = c.getString("bill");
                        String codePrice = c.getString("codePrice");
                        String firstname = c.getString("firstname");
                        String secondname = c.getString("secondname");
                        String ispayedforyassine = c.getString("payedForme");
                        // tmp hash map for single contact


                        // adding each child node to HashMap key => value
                        contact.put("bill", bill);
                        contact.put("codePrice", codePrice);
                        contact.put("firstname", firstname);
                        contact.put("secondname", secondname);
                        contact.put("ispayed", ispayedforyassine);


                        //contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return contact;
        }


            @Override
            protected void onPostExecute(HashMap<String, String> result) {
                super.onPostExecute(result);
                ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                        contactList,
                        R.layout.list_item, new String[]{
                                "firstname","bill", "ispayed",
                        "ispayedforyassine", "codePrice" },
                        new int[]{R.id.email, R.id.name,
                                R.id.tvItemPriority, R.id.cbItemCheck, R.id.cbItemCheck});
                lv.setAdapter(adapter);

            }
        }
    }