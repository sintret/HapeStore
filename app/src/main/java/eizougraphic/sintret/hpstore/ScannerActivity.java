package eizougraphic.sintret.hpstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 11/21/2015.
 */
public class ScannerActivity extends BaseActivity {
    SessionManager session;
    TextView textView;
    String scannerMessage = "Scanner Start!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_scanner);
        View inflated = stub.inflate();
        /* your logic here */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Intent intent = new Intent(ScannerActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        session = new SessionManager(getApplicationContext());
        textView = (TextView) findViewById(R.id.textView);
        IntentIntegrator integrator = new IntentIntegrator(ScannerActivity.this);
        integrator.addExtra("SCAN_WIDTH", 640);
        integrator.addExtra("SCAN_HEIGHT", 480);
        integrator.addExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
        //customize the prompt message before scanning
        integrator.addExtra("PROMPT_MESSAGE", scannerMessage);
        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
                //textView.setText(result.toString());
                progressDialog = new ProgressDialog(ScannerActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("token", session.getToken());
                data.put("content", result.toString());
                data.put("qr_code", result.toString());

                textView.setText(result.toString());

                Boolean isPoint = AppConfig.contains(result.toString(), "-points-");

                if (isPoint) {
                    Intent intent1 = new Intent(ScannerActivity.this, PointActivity.class);
                    intent1.putExtra("qr_code", result.toString());
                    startActivity(intent1);
                    finish();
                } else {
                   /* Intent intent1 = new Intent(ScannerActivity.this,MainActivity.class);
                    intent1.putExtra("success","Successfuly to send to server!");
                    intent.putExtra("qr_code",result.toString());
                    startActivity(intent1);
                    finish();*/

                    String url = AppConfig.URL_SCAN;
                    String error_message ="Successfully use a coupon";

                    final ProgressDialog pDialog = new ProgressDialog(ScannerActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    pDialog.setMessage("Loading...");
                    pDialog.show();
                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("App", response.toString());
                            //responseText.setText("Response:" + " " +response.toString());
                            //pDialog.hide();
                            // Session manager
                            try {
                                Boolean error = response.getBoolean(AppConfig.TAG_ERROR);
                                String error_message = response.getString(AppConfig.TAG_ERROR_MESSAGE);

                                if (error == true) {
                                    //Jika ada error
                                    //session.setLogin(false);
                                } else {

                                    Toast.makeText(ScannerActivity.this, error_message, Toast.LENGTH_LONG).show();

                                }
                                Intent intent1 = new Intent(ScannerActivity.this, MainActivity.class);
                                intent1.putExtra(AppConfig.TAG_ERROR_MESSAGE, error_message);
                                startActivity(intent1);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.hide();



                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError response) {
                            Log.d("Response: ", response.toString());
                            pDialog.hide();
                        }
                    });
                    Volley.newRequestQueue(ScannerActivity.this).add(jsObjRequest);


                }

                //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_LONG).show();


                //call asyncTask for scanner
                //LoginTask loginTask = new LoginTask(LoginActivity.this, AppConfig.URL_LOGIN, data,_loginButton, progressDialog, session);
                //ScannerTask scannerTask = new ScannerTask(ScannerActivity.this, url, data, progressDialog, session);
                // scannerTask.execute();
                //showDialog(R.string.result_succeeded, result.toString());
            } else {
                textView.setText("Scanner Failed!");
                //showDialog(R.string.result_failed, getString(R.string.result_failed_why));
            }
        }
    }
}
