package com.ultron.sahilpratap.moversshifters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
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

public class login extends AppCompatActivity {

    EditText e1,e2;
    Button btn;
    TextView t1,t2;
    String url;
    String email,pass;
    String jsonData;
    public static  final String TAG_RESULT = "result";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        url="https://first-android.000webhostapp.com/getdata_login.php";

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        btn = findViewById(R.id.button);
        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView3);
        SpannableString content = new SpannableString("Register");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        t2.setText(content);
        t2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                t2.setTextColor(Color.WHITE);
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              email = e1.getText().toString();
              pass = e2.getText().toString();
              new Dataprocess().execute();

            }
        });

    }
    class Dataprocess extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(login.this);
            pd.setMessage("Uploading...");
            pd.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            DefaultHttpClient httpClient;
            HttpPost httpPost;
            InputStream inputStream = null;
            String result = null;
            try {

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                Log.i("qwe", email);
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("password", pass));

                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpClient.execute(httpPost);
            }catch (Exception e){}

            try{
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();

                inputStream = entity.getContent();

                //json is UTF-8 by default

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");
                }
                result = sb.toString();
            } catch (Exception e) {


            } finally {

                try {
                    if (inputStream != null)
                        inputStream.close();

                } catch (Exception e) {
                }
                }

            return result;

        }

        @Override
        protected void onPostExecute(String s) {

            jsonData = s;
            pd.dismiss();
            e1.setText("");
            e2.setText("");
            if(getResponse())
            Toast.makeText(login.this,"login seccesfull",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(login.this,"not seccesfull",Toast.LENGTH_LONG).show();

            super.onPostExecute(s);
        }

    }

    public boolean getResponse() {

        Boolean response = false;
        try{

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULT);

            if(jsonArray.length() != 0) {
                response = false;
            } else
                response = true;
        } catch (JSONException e6){

            Toast.makeText(login.this,"aszxzvd",Toast.LENGTH_LONG).show();
        }

        return response;
    }
}
