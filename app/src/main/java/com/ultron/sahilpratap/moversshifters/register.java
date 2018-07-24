package com.ultron.sahilpratap.moversshifters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class register extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;
    Button b1;
    CheckBox c1;
    RadioButton r1,r2;
    String url = "https://first-android.000webhostapp.com/setdata_register.php";
    String name,email,phone,result,pass,passNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        e3 = findViewById(R.id.editText3);
        e4 = findViewById(R.id.editText4);
        e5 = findViewById(R.id.editText5);
        b1 = findViewById(R.id.button);
        c1 = findViewById(R.id.checkBox);
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);
        b1.setEnabled(false);


        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                b1.setEnabled(true);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(register.this,"hello",Toast.LENGTH_LONG).show();
                name = e1.getText().toString();
                email = e2.getText().toString();
                phone = e3.getText().toString();

                if(r1.isChecked())
                    result = r1.getText().toString();
                else
                    result = r2.getText().toString();

                pass = e4.getText().toString();
                passNew = e5.getText().toString();

                new Dataprocess().execute();

                Intent intent = new Intent(register.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }


    class Dataprocess extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(register.this);
            pd.setMessage("Uploading...");
            pd.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            try {

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("phoneNo", phone));
                params.add(new BasicNameValuePair("gender", result));
                params.add(new BasicNameValuePair("password", pass));

                DefaultHttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url);

                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpClient.execute(httpPost);

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            pd.dismiss();
            e1.setText("");
            e2.setText("");
            e3.setText("");
            e4.setText("");
            e5.setText("");
            r1.setChecked(false);
            r2.setChecked(false);
            c1.setChecked(false);
            Toast.makeText(register.this, "Data Saved..", Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }

    }
}
