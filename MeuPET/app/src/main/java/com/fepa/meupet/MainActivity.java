package com.fepa.meupet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import  java.net.URL;
import 	java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText ed_ip;
    private TextView tv_data;
    private Button bt_connect;

    private MeuAsyncTask asyncTask = new MeuAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ed_ip = (EditText) findViewById(R.id.et_ip);
        this.tv_data = (TextView) findViewById(R.id.tv_data);
        this.bt_connect = (Button) findViewById(R.id.bt_connect);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeuAsyncTask asyncTask = new MeuAsyncTask();
                asyncTask.execute();
            }
        });
    }

    class MeuAsyncTask extends AsyncTask<Void, Void, String> {

        private String message;

        @Override
        protected String doInBackground(Void... params) {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url ="http://google.com" ;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            tv_data.setText("Response is: "+ response.substring(0,500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tv_data.setText("That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
           // tv_data.setText(result);
        }
    }
}

