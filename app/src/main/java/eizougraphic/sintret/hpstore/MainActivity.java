package eizougraphic.sintret.hpstore;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private SessionManager session;
    TextView name,email,title;
    AppCompatButton print_report,view_report,scan_barcode;

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