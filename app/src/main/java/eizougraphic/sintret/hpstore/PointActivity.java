package eizougraphic.sintret.hpstore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by andy on 11/22/2015.
 */
public class PointActivity extends BaseActivity {
    private SessionManager session;
    EditText amount;
    String qr_code;
    AppCompatButton btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_point);
        View inflated = stub.inflate();
        /* your logic here */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        session = new SessionManager(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        qr_code = bundle.getString("qr_code");
        amount = (EditText) findViewById(R.id.amount);
        btn_submit = (AppCompatButton) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("EditText value=", amount.getText().toString());
                HashMap<String, String> data =  new HashMap<String, String>();
                data.put("qr_code",qr_code);
                data.put("token",session.getToken());
                data.put("amount",amount.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(PointActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                PointTask task = new PointTask(PointActivity.this,data, progressDialog, session);
                task.execute();

            }
        });



    }
}
