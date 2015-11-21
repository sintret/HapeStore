package eizougraphic.sintret.hpstore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

/**
 * Created by andy on 11/22/2015.
 */
public class PointActivity extends BaseActivity {
    private SessionManager session;
    TextView name,email,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        session = new SessionManager(getApplicationContext());


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_point);
        View inflated = stub.inflate();
        /* your logic here */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        session = new SessionManager(getApplicationContext());


    }
}
