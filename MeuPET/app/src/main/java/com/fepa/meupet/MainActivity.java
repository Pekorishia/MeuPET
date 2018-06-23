package com.fepa.meupet;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import  java.net.URL;
import 	java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText ed_ip;
    private TextView tv_data;
    private Button bt_connect;

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
        // send requisition
        protected String doInBackground(Void... params) {
            String text = "sucesso";
            return text;
        }

        @Override
        // execute something after
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            tv_data.setText(result);
        }
    }
}

