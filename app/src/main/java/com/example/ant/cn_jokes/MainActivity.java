package com.example.ant.cn_jokes;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView viewTv;
    Button button;
    String url;
    RequestQueue queue;
    String jokeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        viewTv = (TextView)findViewById(R.id.jokeView);
        queue = Volley.newRequestQueue(this);
       // url = "http://api.icndb.com/jokes/random/1";

        viewTv.setText(jokeString);
        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        button.setOnClickListener(new View.OnClickListener() {
            // go to Settings View
            public void onClick(final View view) {
                dialog.show();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.icndb.com/jokes/random/1", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arr = response.getJSONArray("value");
                            JSONObject jObj = arr.getJSONObject(0);
                            jokeString = jObj.getString("joke");
                            // jokeString= jokeObj.toString();
                            dialog.cancel();
                            viewTv.setText(jokeString);
                           // Toast.makeText(getApplicationContext(), jokeString, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);

            }


        });









    }
}
