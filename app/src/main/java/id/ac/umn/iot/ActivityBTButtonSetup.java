package id.ac.umn.iot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityBTButtonSetup extends AppCompatActivity {
    Button setup;
    EditText button_name, command;
    TextView activityTitle;

    RoomDB database;
    Integer parentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_bluetooth_button);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            parentID = null;
        }else{
            parentID = extras.getInt("parent_id");
        }

        button_name = findViewById(R.id.button_name);
        command = findViewById(R.id.command);
        activityTitle = (TextView) findViewById(R.id.toolbarText);
        activityTitle.setText("Button setup");

        setup = (Button) findViewById(R.id.next);
        setup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sProjectName = button_name.getText().toString().trim();
                String sCommand =  command.getText().toString().trim();
                if (TextUtils.isEmpty(button_name.getText()) || TextUtils.isEmpty(command.getText()) ) {
                    button_name.setError("Button name & Command is required");
                } else {
                    BTData data = new BTData();
                    data.setButtonName(sProjectName);
                    data.setCommand(sCommand);
                    data.setParentID(parentID);
                    database.getInstance(getApplicationContext()).btDao().insert(data);

                    Intent setupProject = new Intent(ActivityBTButtonSetup.this, ActivityProjectBluetooth.class);
                    setupProject.putExtra("parent_id", parentID);
                    setupProject.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupProject);
                    finish();
                }
            }
        });

    }

}
