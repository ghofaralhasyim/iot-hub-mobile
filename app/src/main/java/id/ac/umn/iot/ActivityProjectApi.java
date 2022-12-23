package id.ac.umn.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityProjectApi extends AppCompatActivity {

    String BASE_URL = "https://iot-hub-backend.vercel.app/api/";
    String token, projectTitle;
    GraphView graphView;
    TextView activityTitle;
    ImageButton btnInfo;

    private RoomDB database;
    List<DataApi> dataList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_api);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            token = null;
        }else{
            token = extras.getString("device_token");
            projectTitle = extras.getString("project_name");
        }

        Log.d("TAGGGG", extras.getString("device_token"));

        activityTitle = (TextView) findViewById(R.id.toolbarText);
        activityTitle.setText(projectTitle);

        findViewById(R.id.btCallApi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getRetrofitClient(BASE_URL+"devices/"+token+'/');
                Api api = retrofit.create(Api.class);
                Call<GetDataApi> getDataApiCall = api.getDataApi();

                getDataApiCall.enqueue(new Callback<GetDataApi>() {
                    @Override
                    public void onResponse(Call<GetDataApi> call, Response<GetDataApi> response) {
                        GetDataApi getDataApi = (GetDataApi) response.body();
                        DataApi dataDevice = getDataApi.getData();

                        graphView = findViewById(R.id.idGraphView);
                        if(dataDevice.getDataDeviceList().size() > 0) {
                            DataPoint[] dataPoints = new DataPoint[dataDevice.getDataDeviceList().size()];
                            Integer index = 0;

                            for (DataDevice data : dataDevice.getDataDeviceList()) {
                                if (data.getData() != null) {
                                    dataPoints[index] = new DataPoint(index, Double.parseDouble(data.getData()));
                                } else {
                                    dataPoints[index] = new DataPoint(index, 2);
                                }
                                index++;
                            }

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                            graphView.setTitle("Data");
                            graphView.setTitleColor(R.color.purple_200);
                            graphView.setTitleTextSize(32);
                            graphView.addSeries(series);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDataApi> call, Throwable t) {
                        Toast.makeText(ActivityProjectApi.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent projectIntent = new Intent(ActivityProjectApi.this, ActivityProjectSetupGuide.class);
                projectIntent.putExtra("device_token", token);
                projectIntent.putExtra("project_name", projectTitle);
                projectIntent.putExtra("isCreated", true);
                startActivity(projectIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent projectIntent = new Intent(ActivityProjectApi.this, ActivityMain.class);
        projectIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(projectIntent);
        finish();
    }
}
