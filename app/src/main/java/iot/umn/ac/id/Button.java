package iot.umn.ac.id;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class Button extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    ButtonAdapter mAdapter;

//    RecyclerView rvButton;
//    ButtonAdapter mAdapter;

    private ImageButton btnRefresh;
    private TextView connDev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_list);

        recyclerView = findViewById(R.id.recyclerView);
//        initArray();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ButtonAdapter(this, arrayList);
        recyclerView.setAdapter(mAdapter);

    }

/*    public void update (View view) {
        View u = (View) view.getParent();
        TextView taskUpdateView = (TextView) u.findViewById(R.id.list);
        final int id = u.getId();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Data");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String update = inputField.getText().toString();

                ButtonDBHelper helper = new ButtonDBHelper(Button.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                //db.execSQL(sql);
                ContentValues values = new ContentValues();

                values.clear();
                values.put(ButtonDB.Columns._ID, id);
                values.put(ButtonDB.Columns.BUTTON, update);
                db.replace(ButtonDB.TABLE, null, values);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }*/

/*    public void addbutton () {
        EditText buttonName;
        EditText OnCommand;
        EditText OffCommand;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a button");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_button_dialog,null);

        buttonName = view.findViewById(R.id.etButtonName);
        OnCommand = view.findViewById(R.id.etOnCommand);
        OffCommand = view.findViewById(R.id.etOffCommand);

        builder.setMessage("What do you want to do?");
        final EditText inputField = new EditText(this);
        builder.setView(inputField);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String task = inputField.getText().toString();

                helper = new ButtonDBHelper(Button.this);
                SQLiteDatabase sqlDB = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(ButtonDB.Columns.BUTTON, task);

                sqlDB.insertWithOnConflict(ButtonDB.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                updateUI();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }*/

//    private void updateUI() {
//        ButtonDBHelper helper = new ButtonDBHelper(Button.this);
//        SQLiteDatabase sqlDB = helper.getReadableDatabase();
//        Cursor cursor = sqlDB.query(ButtonDB.TABLE,
//                new String[]{ButtonDB.Columns._ID, ButtonDB.Columns.BUTTON},
//                null, null, null, null, null);
//
//        ButtonAdapter = new SimpleCursorAdapter(
//                this,
//                R.layout.button_list,
//                cursor,
//                new String[]{ButtonDB.Columns.BUTTON},
//                new int[]{R.id.button},
//                0
//        );
//        this.setListAdapter(ButtonAdapter);
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}