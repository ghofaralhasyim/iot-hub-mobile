package id.ac.umn.iot;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityProjectSetupGuide extends AppCompatActivity {
    String token, projectName;
    RoomDB database;
    View loading;
    TextView note, textGuide, textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_setup_guide);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            projectName = null;
        }else{
            projectName = extras.getString("project_name");
        }

        if(!extras.getBoolean("isCreated")){
            loading = (View) findViewById(R.id.loading);
            loading.bringToFront();

            Retrofit retrofit = RetrofitClient.getRetrofitClient("https://iot-hub-backend.vercel.app/api/devices/generate/");
            Api api = retrofit.create(Api.class);
            Call<GetTokenDevice> getTokenDeviceCall = api.tokenDeviceRequest();

            getTokenDeviceCall.enqueue(new Callback<GetTokenDevice>() {
                @Override
                public void onResponse(Call<GetTokenDevice> call, Response<GetTokenDevice> response) {
                    GetTokenDevice getTokenDevice = (GetTokenDevice) response.body();
                    token = getTokenDevice.getGenerated_token();

                    MainData data = new MainData();
                    data.setProjectName(projectName);
                    data.setDeviceToken(token);
                    data.setProjectType("api");
                    database.getInstance(getApplicationContext()).mainDao().insert(data);

                    TextView tProjectToken = (TextView) findViewById(R.id.accesTokenText);
                    tProjectToken.setText(token);

                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<GetTokenDevice> call, Throwable t) {
                    Log.d("GETDATA", "onFailure: errror get data " + t);
                }
            });
        }

        Button setup = (Button) findViewById(R.id.next);
        if(extras.getBoolean("isCreated")){
            token = extras.getString("device_token");
            setup.setText("Back");
            TextView tProjectToken = (TextView) findViewById(R.id.accesTokenText);
            tProjectToken.setText(token);

            loading = (View) findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            note = (TextView) findViewById(R.id.note);
            note.setVisibility(View.GONE);

            textTitle = (TextView) findViewById(R.id.textTitle);
            textGuide = (TextView) findViewById(R.id.textGuide);
            textGuide.setText("Use this format to send data to our API:");
            textTitle.setText("Awesome! Let's get started");
        }
        setup.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                if(token == null){
                    return;
                }
                Intent projectIntent = new Intent(ActivityProjectSetupGuide.this, ActivityMain.class);
                projectIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(projectIntent);
                finish();
            }
        });
    }
}
