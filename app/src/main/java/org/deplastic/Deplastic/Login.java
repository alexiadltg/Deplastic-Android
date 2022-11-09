package org.deplastic.Deplastic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    Button b = findViewById(R.id.LoginButton);
    b.setOnClickListener(v -> {
        EditText email=findViewById(R.id.editText1);
        String emailVal=email.getText().toString();

        EditText passwd=findViewById(R.id.editText2);
        String passwdVal=passwd.getText().toString();

        JSONObject credentials = new JSONObject();

        try {
            credentials.put("email" , emailVal);
            credentials.put("password" , passwdVal);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject[] auth = {new JSONObject()};

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/login";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, credentials,
                response -> auth[0] = response,
                error -> error.printStackTrace());
        Toast.makeText(getApplicationContext(), auth.toString(), Toast.LENGTH_SHORT);
        queue.add(stringRequest);
        try {
            if(auth[0].getBoolean("auth")){
                Toast.makeText(getApplicationContext(), "Benvingut User.", Toast.LENGTH_SHORT).show();
                changeLoc();
            }else{
                Toast.makeText(getApplicationContext(), "Wrong user or password, try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    });
    }
    void changeLoc(){
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
    }
}


