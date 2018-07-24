package com.ultron.sahilpratap.moversshifters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DefaultDatabaseErrorHandler;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {

    EditText e1,e2;
    Button btn;
    TextView t1,t2,t3;
    String url;
    InputStream is=null;
    String email,pass;
    String jsonData;
    public static  final String TAG_RESULT = "result";
    public static  final  String FILE = "jadu";
    String user_name,user_email,user_phone,user_gender,user_password;

    public static  final String TAG_NAME = "name";
    public static  final String TAG_EMAIL = "email";
    public static  final String TAG_PHONE = "phone";
    public static  final String TAG_GENDER = "gender";
    public static  final String TAG_PASSWORD = "password";

    SharedPreferences pref;
    SharedPreferences.Editor editor;



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        url="https://first-android.000webhostapp.com/getdata_login.php";

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        btn = findViewById(R.id.button);
        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView3);
        t3 = findViewById(R.id.textView5);
        SpannableString content = new SpannableString("Register");
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        t2.setText(content);

        pref = getSharedPreferences(FILE,MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("email_key","sahilpratap7200@gmail.com");
        editor.putString("password","sahil173573");
        editor.commit();
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

                String name = pref.getString("email_key","No Data");
                String phone = pref.getString("password","No Data");
                if(email.equals(name) && pass.equals(phone)){

                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.putExtra("name",email);
                    intent.putExtra("email","sahil pratap");
                    startActivity(intent);

                }else
                    new Dataprocess().execute();

            }
        });

    }

    class Dataprocess extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(login.this);
            pd.setMessage("Signing In...");
            pd.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            // put the values of id and name in that variable
            nameValuePairs.add(new BasicNameValuePair("email", email));
            //nameValuePairs.add(new BasicNameValuePair("",name));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.i("pass 1", "connection success ");
            } catch (Exception e) {
                Log.i("Fail 1", e.toString());
            }

            try {
                String line = null;
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                jsonData = sb.toString();
                Log.i("pass 2", "connection success ");
            } catch (Exception e) {
                Log.i("Fail 2", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            pd.dismiss();
            e1.setText("");
            e2.setText("");
            if (getResponse()) {

                success();

            } else

                t3.setText("please enter correct login detailes");

            super.onPostExecute(s);
        }

    }

    public boolean getResponse() {

        Boolean response = false;
        try{

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULT);
            if(jsonArray.length() != 0) {
                Log.i("asd",jsonArray.toString());
                response = true;
            } else
                response = false;

            JSONObject obj = jsonArray.getJSONObject(0);
            user_name = obj.getString(TAG_NAME);
            user_email = obj.getString(TAG_EMAIL);
            user_phone = obj.getString(TAG_PHONE);
            user_gender = obj.getString(TAG_GENDER);
            user_password = obj.getString(TAG_PASSWORD);

            if(!pass.equals(user_password)){

                response = false;

            }

        } catch (Exception e){

            //Toast.makeText(login.this,"aszxzvd",Toast.LENGTH_LONG).show();
        }

        return response;
    }

    public void success(){

        Intent intent = new Intent(login.this, MainActivity.class);
        intent.putExtra("name",user_name);
        intent.putExtra("email",user_email);
        intent.putExtra("phone",user_phone);
        intent.putExtra("gender",user_gender);
        startActivity(intent);

    }



}
update lo
