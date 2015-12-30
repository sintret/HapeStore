package eizougraphic.sintret.hpstore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private SessionManager session;
    TextView name,email,title;

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