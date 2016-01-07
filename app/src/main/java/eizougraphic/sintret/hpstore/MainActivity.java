package eizougraphic.sintret.hpstore;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private SessionManager session;
    TextView name,email,title;
    AppCompatButton print_report,view_report,scan_barcode;
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_main);
        View inflated = stub.inflate();
        /* your logic here */

        scan_barcode = (AppCompatButton) findViewById(R.id.scan_barcode);
        view_report = (AppCompatButton) findViewById(R.id.view_report);
        print_report = (AppCompatButton) findViewById(R.id.print_report);


        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        print_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, AppConfig.URL_PRINT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String name = jsonResponse.getString("name");
                                    String googleDocsUrl = "http://docs.google.com/viewer?url=";

                                    Toast.makeText(getApplication(),""+name,Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(Intent.ACTION_VIEW);
                                    //intent.setDataAndType(Uri.parse(googleDocsUrl + name), "application/pdf");
                                    ///WebView mWebView=new WebView(MainActivity.this);
                                    //mWebView.getSettings().setJavaScriptEnabled(true);
                                    //mWebView.getSettings().setPluginsEnabled(true);
                                    //mWebView.loadUrl(googleDocsUrl +name);
                                    //setContentView(mWebView);

                                    Intent i = new Intent(Intent.ACTION_VIEW );
                                    i.setDataAndType(Uri.parse(googleDocsUrl + name ), HTML_MIME_TYPE );
                                    startActivity( i );
                                    //finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<>();
                        // the POST parameters:
                        params.put("token", session.token());
                        return params;
                    }
                };
                Volley.newRequestQueue(v.getContext()).add(postRequest);

            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){

            Toast.makeText(getApplicationContext(),bundle.getString(AppConfig.TAG_ERROR_MESSAGE),Toast.LENGTH_LONG).show();
        }

        session = new SessionManager(getApplicationContext());
        name = (TextView) findViewById(R.id.name);
        //email = (TextView) findViewById(R.id.email);
        title = (TextView) findViewById(R.id.title);

        name.setText(session.getFullname() + " / " + session.getEmail());
        //email.setText(" / " +session.getEmail());
        title.setText(session.getStore());

    }

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}