package iot.umn.ac.id;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent intent = getIntent();
        Button newproj = findViewById(R.id.newproj);
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        /*TextView textView = findViewById(R.id.textView);
        textView.setText(message);*/
        newproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    }
    void showCustomDialog() {
        final Dialog dialog = new Dialog(Homepage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.choose_page);
        Button apibtn = dialog.findViewById(R.id.apibtn);
        Button BTbtn = dialog.findViewById(R.id.bluetoothbtn);



        apibtn.setOnClickListener(new View.OnClickListener() { // choose api
            @Override
            public void onClick(View v) {


                    Intent intentDua = new Intent(Homepage.this,
                            ApiNew.class);
                    startActivityForResult(intentDua, 1);

            }
        });
        BTbtn.setOnClickListener(new View.OnClickListener() { // choose api
            @Override
            public void onClick(View v) {


                Intent intentDua = new Intent(Homepage.this,
                        BTnew.class);
                startActivityForResult(intentDua, 1);

            }
        });


        dialog.show();

    }
}