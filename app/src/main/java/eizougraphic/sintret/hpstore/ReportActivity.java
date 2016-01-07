package eizougraphic.sintret.hpstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andi on 1/6/2016.
 */
public class ReportActivity extends BaseActivity {
    private SessionManager session;
    RecyclerView rv;
    TextView title;

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
        stub.setLayoutResource(R.layout.content_report);
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
                Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rv = (RecyclerView) findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);

        title=(TextView) findViewById(R.id.title);

        String jsonString = session.getMessages();
       // title.setText(jsonString);

        //Log.d("JSoN Messages", "Messages is:" + jsonString);
        JSONArray array;
        if(jsonString.length() < 5){
            Toast.makeText(getBaseContext(), "You have no a message", Toast.LENGTH_LONG).show();
        } else {
            List<JSONObject> messages;
            messages = new ArrayList<>();
            JSONObject jsonObject = null;
            //JSONArray jsonArray = null;
            try {
                array = new JSONArray(jsonString);
               // Toast.makeText(getBaseContext(),"Panjang"+ array.length(),Toast.LENGTH_LONG).show();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonData = array.getJSONObject(i);
                    messages.add(jsonData);
                    //Toast.makeText(getBaseContext()," "+ jsonData.getString("title"),Toast.LENGTH_LONG).show();

                }
                MessagesAdapter adapter = new MessagesAdapter(messages);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
