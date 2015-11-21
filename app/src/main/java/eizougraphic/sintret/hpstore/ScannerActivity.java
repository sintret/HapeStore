package eizougraphic.sintret.hpstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

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

                textView.setText(result.toString());

                Boolean isPoint = AppConfig.contains(result.toString(),"-points-");

                if(isPoint){
                    Intent intent1 = new Intent(ScannerActivity.this,PointActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent1 = new Intent(ScannerActivity.this,MainActivity.class);
                    intent1.putExtra("success","Successfuly to send to server!");
                    startActivity(intent1);
                    finish();
                    //ScannerTask scannerTask = new ScannerTask(ScannerActivity.this, url, data, progressDialog, session);
                    // scannerTask.execute();

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
