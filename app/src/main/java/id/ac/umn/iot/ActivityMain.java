package id.ac.umn.iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity implements ProjectListListener{
    Button newproj,btReset;
    RecyclerView listProject;
    Dialog dialogUpdate;

    List<MainData> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    AdapterProjectList adapterProjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newproj = findViewById(R.id.newproj);
        listProject=findViewById(R.id.projectList);

        getList();

        newproj.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ActivityMain.this);
                dialog.setContentView(R.layout.dialog_choose_project);

                Button bluetooth = (Button) dialog.findViewById(R.id.btnBluetooth);
                Button api = (Button) dialog.findViewById(R.id.btnApi);

                api.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent setupProject = new Intent(ActivityMain.this, ActivityProjectSetup.class);
                        setupProject.putExtra("project_type", "api");
                        startActivity(setupProject);
                        dialog.dismiss();
                    }
                });

                bluetooth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent setupProject = new Intent(ActivityMain.this, ActivityProjectSetup.class);
                        setupProject.putExtra("project_type", "bt");
                        startActivity(setupProject);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    public void getList(){
        dataList.clear();
        MainData data = new MainData();

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        listProject.setLayoutManager(linearLayoutManager);
        adapterProjectList = new AdapterProjectList(dataList,ActivityMain.this, this);
        listProject.setAdapter(adapterProjectList);
    }

    @Override
    public void onBtnClicked(MainData data){
        String projectType = data.getProjectType();
        if(projectType.equals("api")){
            Intent projectIntent = new Intent(ActivityMain.this, ActivityProjectApi.class);
            projectIntent.putExtra("device_token", data.getDeviceToken());
            projectIntent.putExtra("project_name",data.getProjectName());
            startActivity(projectIntent);
        }else{
            Intent projectIntent = new Intent(ActivityMain.this, ActivityProjectBluetooth.class);
            projectIntent.putExtra("parent_id", data.getID());
            startActivity(projectIntent);
        }
    }

    @Override
    public void onBtnLongClicked(MainData data) {
        dialogUpdate = new Dialog(ActivityMain.this);
        dialogUpdate.setContentView(R.layout.dialog_project_update);

        Button update = (Button) dialogUpdate.findViewById(R.id.update);
        Button delete = (Button) dialogUpdate.findViewById(R.id.delete);
        EditText projectName = (EditText) dialogUpdate.findViewById(R.id.projectName);

        projectName.setText(data.getProjectName());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getInstance(getApplicationContext()).mainDao().update(data.getID(),
                        projectName.getText().toString().trim());
                getList();
                dialogUpdate.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getInstance(getApplicationContext()).mainDao().deleteById(data.getID());
                getList();
                dialogUpdate.dismiss();
            }
        });
        dialogUpdate.show();
    }
}