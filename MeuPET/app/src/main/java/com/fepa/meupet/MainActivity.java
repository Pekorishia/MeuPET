package com.fepa.meupet;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private EditText ed_ip;
    private TextView tv_data;
    private Button bt_connect;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(MainActivity.this);

        this.ed_ip = findViewById(R.id.et_ip);
        this.tv_data = findViewById(R.id.tv_data);
        this.bt_connect = findViewById(R.id.bt_connect);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "";
                if (ed_ip.getText().toString().equals("IP")) {
                    url = "http://google.com";
                } else {
                    url = "http://" + ed_ip.getText().toString();
                }

                // Request a string response from the provided URL
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                tv_data.setText(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tv_data.setText("That didn't work!" + error.getCause());
                            }
                        }
                );

                queue.add(stringRequest);
            }
        });

    }

}

