package iot.umn.ac.id;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothDialog extends AppCompatActivity {

    private ListView lstvw;
    private ArrayAdapter aAdapter;

    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_dialog);

        ImageButton btn = (ImageButton) findViewById(R.id.btSearch);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(BluetoothDialog.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BluetoothDialog.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 3);
                }

                if (bAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
                } else {
                    Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
                    ArrayList list = new ArrayList();
                    if(pairedDevices.size()>0){
                        for(BluetoothDevice device: pairedDevices){
                            String devicename = device.getName();
                            String macAddress = device.getAddress();
                            list.add("Name: "+devicename+"\n MAC Address: "+macAddress);
                        }
                        lstvw = (ListView) findViewById(R.id.BtList);
                        aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                        lstvw.setAdapter(aAdapter);
                    }
                }
            }
        });
    }

    /*private void list() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 3);
        }

        pairedDevices = bluetoothAdapter.getBondedDevices();

        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        BTList.setAdapter(adapter);
    }*/


//    public void showBTDialog() {
//
//        AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View view= inflater.inflate(R.layout.bluetooth_dialog, findViewById(R.id.BtList));
//
//        popDialog.setTitle("Paired Bluetooth Devices");
//        popDialog.setView(view);
//
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 3);
//        }
//
//        // create the arrayAdapter that contains the BTDevices, and set it to a ListView
//        BTList = (ListView) findViewById(R.id.BtList);
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        BTList.setAdapter(adapter);
//
//        // get paired devices
//        pairedDevices = bluetoothAdapter.getBondedDevices();
//
//        ArrayList list = new ArrayList();
//
//        // put it's one to the adapter
//        for(BluetoothDevice device : pairedDevices){
//            list.add(device.getName()+ "\n" + device.getAddress());
//        }
//
//        // Button OK
//        popDialog.setPositiveButton("Pair", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//
//        });
//
//        // Create popup and show
//        popDialog.create();
//        popDialog.show();
//
//    }
}