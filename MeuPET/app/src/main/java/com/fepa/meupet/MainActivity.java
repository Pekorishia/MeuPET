package com.fepa.meupet;

import android.os.Handler;
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

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText ed_ip;
    private TextView tv_id, tv_temp, tv_humidity;
    private Button bt_connect;

    private RequestQueue queue;

    private StringRequest stringRequest;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(MainActivity.this);

        this.ed_ip = findViewById(R.id.et_ip);
        this.tv_humidity = findViewById(R.id.tv_humidity);
        this.tv_id = findViewById(R.id.tv_id);
        this.tv_temp = findViewById(R.id.tv_temp);
        this.bt_connect = findViewById(R.id.bt_connect);

        handler = new Handler();

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ip = "";
                if (ed_ip.getText().toString().equals("IP")) {
                    ip = "10.9.100.132";
                } else {
                    ip = ed_ip.getText().toString();
                }
                String url = "http://"+ip+":8080/MeuPetWS/rest/update";

                queue.add(new StringRequest(Request.Method.GET, "http://"+ip+":8080/MeuPetWS/rest/subscribe?idEntity=c1",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                tv_data.setText(response);
                                tv_humidity.setText("Umidade : 0");
                                tv_id.setText("ID : 0");
                                tv_temp.setText("Temperatura : 0");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                tv_data.setText("That didn't work!" + error.getCause());
                                tv_humidity.setText("Umidade : ERROR!");
                                tv_id.setText("ID : ERROR!");
                                tv_temp.setText("Temperatura : ERROR!");
                            }
                        }
                ));

                initLoop(url);
            }
        });

    }


    private void initLoop(String url) {
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int temp, humidity;
                        String id;
//                        tv_data.setText(response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONObject coleira = obj.getJSONObject("Coleira");
                            id = coleira.getString("id");
                            temp = coleira.getInt("temperature");
                            humidity = coleira.getInt("humidity");

                            tv_humidity.setText("Umidade : "+ humidity);
                            tv_id.setText("ID : " + id);
                            tv_temp.setText("Temperatura : " + temp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        tv_data.setText("That didn't work!" + error.getCause());
                        tv_humidity.setText("Umidade : ERROR!");
                        tv_id.setText("ID : ERROR!");
                        tv_temp.setText("Temperatura : ERROR!");
                    }
                }
        );

        handler.post(new Runnable() {

            @Override
            public void run() {
                handler.postDelayed(this, 5000);
                queue.add(stringRequest);
//              Toast.makeText(getApplicationContext(), (i++)+"", Toast.LENGTH_LONG).show();
            }
        });
    }

}

