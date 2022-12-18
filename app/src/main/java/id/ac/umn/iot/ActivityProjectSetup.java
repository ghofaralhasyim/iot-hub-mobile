package id.ac.umn.iot;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityProjectSetup extends AppCompatActivity {
    Button setup;
    EditText field_projectName;
    TextView activityTitle;

    String projectType;

    RoomDB database;
    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_setup);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            projectType = null;
        }else{
            projectType = extras.getString("project_type");
        }

        field_projectName = findViewById(R.id.projectName);
        activityTitle = (TextView) findViewById(R.id.toolbarText);
        activityTitle.setText("Project setup");

        setup = (Button) findViewById(R.id.next);
        if(projectType.equals("api")){
            setup.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    String sProjectName = field_projectName.getText().toString().trim();
                    if (TextUtils.isEmpty(field_projectName.getText())) {
                        field_projectName.setError("Project name is required");
                    } else {
                        Intent setupProject = new Intent(ActivityProjectSetup.this, ActivityProjectSetupGuide.class);
                        setupProject.putExtra("project_name", sProjectName);
                        setupProject.putExtra("isCreated", false);
                        setupProject.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupProject);
                        finish();
                    }
                }
            });
        }else{
            setup.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    String sProjectName = field_projectName.getText().toString().trim();
                    if (TextUtils.isEmpty(field_projectName.getText())) {
                        field_projectName.setError("Project name is required");
                    } else {
                        MainData data = new MainData();
                        data.setProjectName(sProjectName);
                        data.setProjectType("bluetooth");
                        data.setProjectType(projectType);
                        database.getInstance(getApplicationContext()).mainDao().insert(data);

                        Intent setupProject = new Intent(ActivityProjectSetup.this, ActivityMain.class);
                        setupProject.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(setupProject);
                        finish();
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityProjectSetup.this, ActivityMain.class));
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar_text);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

}
