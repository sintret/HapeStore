package eizougraphic.sintret.hpstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ASUS-PC on 4/21/2016.
 */
public class DiscountActivity extends BaseActivity {
    private SessionManager session;
    EditText subtotal, total, discount;
    String qr_code;
    AppCompatButton btn_submit;
    int f = 0;
    int s = 0;
    double disc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_discount);
        View inflated = stub.inflate();
        /* your logic here */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        session = new SessionManager(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        qr_code = bundle.getString("qr_code");
        subtotal = (EditText) findViewById(R.id.subtotal);
        total = (EditText) findViewById(R.id.total);
        discount = (EditText) findViewById(R.id.discount);


        //get discount by qr_code
        if (s == 0) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("content", qr_code);
            VolleyRequest volleyRequest = new VolleyRequest(Request.Method.POST, AppConfig.URL_CARD_NUMBER_IS, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        s = 1;
                        Log.d("LOG_IS_CARD", jsonObject.toString());
                        boolean error = jsonObject.getBoolean(AppConfig.TAG_ERROR);
                        String error_message = jsonObject.getString(AppConfig.TAG_ERROR_MESSAGE);
                        double discs = jsonObject.getDouble("discount");
                        Toast.makeText(getApplicationContext(), discs +"" , Toast.LENGTH_LONG).show();
                        disc = discs;

                        if(error){
                            Toast.makeText(getApplicationContext(), error_message +"" , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DiscountActivity.this, MainActivity.class);
                            intent.putExtra(AppConfig.TAG_ERROR_MESSAGE, error_message);
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(volleyRequest);
        }


        total.setEnabled(false);
        discount.setEnabled(false);

        subtotal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    double v = Double.parseDouble(String.valueOf(s));
                    double calc = 0;
                    double totals=0;
                    if (disc != 0) {
                        calc = (disc / 100) * v;
                        totals = v - calc;

                        discount.setText("" + disc);
                        total.setText(""+totals);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });
        ;

        btn_submit = (AppCompatButton) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (subtotal.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill subtotal", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.v("EditText value=", subtotal.getText().toString());
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("qr_code", qr_code);
                data.put("token", session.token());
                data.put("subtotal", subtotal.getText().toString());
                data.put("total", total.getText().toString());
                data.put("discount", discount.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(DiscountActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                if (f == 0) {
                    VolleyRequest volleyRequest = new VolleyRequest(Request.Method.POST, AppConfig.URL_SCAN, data, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Log.d("LOG", jsonObject.toString());
                                boolean error = jsonObject.getBoolean(AppConfig.TAG_ERROR);
                                String error_message = jsonObject.getString(AppConfig.TAG_ERROR_MESSAGE);
                                String json_message = jsonObject.getString(AppConfig.TAG_JSON_MESSAGES);

                                session.setMessages(json_message);
                                //Log.d("email user",email+" is true");
                                Log.d("status", error + " is " + error);
                                Log.d("JSON OBJECT", jsonObject.toString());

                                Intent intent = new Intent(DiscountActivity.this, ReportActivity.class);
                                intent.putExtra(AppConfig.TAG_ERROR_MESSAGE, error_message);
                                startActivity(intent);
                                f = 1;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.hide();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Volley.newRequestQueue(getApplicationContext()).add(volleyRequest);
                }

            }
        });


    }
}
