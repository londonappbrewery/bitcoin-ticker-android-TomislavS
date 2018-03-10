package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;
    Spinner spinner;
    String BASE_URL2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                String currency = String.valueOf(parent.getItemAtPosition(position));
                BASE_URL2=BASE_URL+currency;
                Log.d("Bitcoin",""+parent.getItemAtPosition(position));
                letsDoSomeNetworking();
                Log.d("Bitcoin","You selected " + currency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL2, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                //called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON WORKED: " + response.toString());
                try {
                    String price=response.getString("last");
                    mPriceTextView.setText(price);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String fe, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("Bitcoin",  "ERROR" + e.toString());
                Log.d("Bitcoin" + "fe", "Request fail! Status code: " + statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                Log.d(BASE_URL,  "This is the FINAL URL");

            }

        });


//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://www.google.com", new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // called when response HTTP status is "200 OK"
//                Log.d("Bitcoin", "JSON: " + response.toString());
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
//                Log.d("Bitcoin", "Fail response: " + response);
//                Log.e("ERROR", e.toString());
//            }
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });


    }


}
