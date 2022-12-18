package id.ac.umn.iot;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ActivityProjectBluetooth extends AppCompatActivity implements BtnBluetoothListener{
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    public ArrayAdapter<String> mBTArrayAdapter;

    private Handler mHandler;
    private BTConnectionThread mConnectedThread;
    private BluetoothSocket mBTSocket = null;
    private ListView mDevicesListView;
    private Dialog dialog, dialogUpdate;

    private final String TAG = ActivityBTConnect.class.getSimpleName();

    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public final static int MESSAGE_READ = 2;
    private final static int CONNECTING_STATUS = 3;

    private TextView mBluetoothStatus;

    RecyclerView listButton;
    Button addButton;
    Integer parentID;
    ImageView connectBT;

    List<BTData> dataList =new ArrayList<>();
    FlexboxLayoutManager flexboxLayoutManager;
    RoomDB database;

    AdapterProjectBTList adapterProjectBTList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_bluetooth);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            parentID = null;
        }else{
            parentID = extras.getInt("parent_id");
        }

        getList();

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent setupProject = new Intent(ActivityProjectBluetooth.this, ActivityBTButtonSetup.class);
                setupProject.putExtra("parent_id", parentID);
                startActivity(setupProject);
            }
        });

        connectBT = findViewById(R.id.connectBT);

        mBluetoothStatus = (TextView) findViewById(R.id.bluetooth_status);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                }

                if (msg.what == CONNECTING_STATUS) {
                    char[] sConnected;
                    if (msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device:" + msg.obj);
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }
            }
        };

        connectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(ActivityProjectBluetooth.this);
                dialog.setContentView(R.layout.dialog_bluetooth_pair);
                dialog.show();

                mBTArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                mBTAdapter = BluetoothAdapter.getDefaultAdapter();
                mDevicesListView = (ListView) dialog.findViewById(R.id.devices_list_view);
                mDevicesListView.setAdapter(mBTArrayAdapter);
                mDevicesListView.setOnItemClickListener(mDeviceClickListener);
                listPairedDevices();
            }
        });

    }

    private void listPairedDevices() {
        mBTArrayAdapter.clear();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
        mPairedDevices = mBTAdapter.getBondedDevices();
        if (mBTAdapter.isEnabled()) {
            for (BluetoothDevice device : mPairedDevices)
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            Toast.makeText(getApplicationContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }

            dialog.dismiss();

            mBluetoothStatus.setText("Connecting…");
            String info = ((TextView) view).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0, info.length() - 17);

            new Thread()
            {
                @Override
                public void run() {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(ActivityProjectBluetooth.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);

                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Error socket creation", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            Toast.makeText(getBaseContext(), "Error socket creation", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!fail) {
                        mConnectedThread = new BTConnectionThread(mBTSocket, mHandler);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityProjectBluetooth.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

    public void getList(){
        dataList.clear();
        listButton = findViewById(R.id.buttonList);

        BTData data = new BTData();

        database = RoomDB.getInstance(this);
        dataList = database.btDao().getByParentID(parentID);

        flexboxLayoutManager = new FlexboxLayoutManager(this);
        listButton.setLayoutManager(flexboxLayoutManager);
        adapterProjectBTList = new AdapterProjectBTList(dataList, ActivityProjectBluetooth.this, this );
        listButton.setAdapter(adapterProjectBTList);
    }

    @Override
    public void onBtnClicked(BTData data) {
        String command = data.getCommand();
        if(mConnectedThread != null) {
            mConnectedThread.write(command);
            Toast.makeText(getBaseContext(), command, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBtnLongClicked(BTData data) {
        dialogUpdate = new Dialog(ActivityProjectBluetooth.this);
        dialogUpdate.setContentView(R.layout.dialog_bluetooth_button_update);

        Button update = (Button) dialogUpdate.findViewById(R.id.update);
        Button delete = (Button) dialogUpdate.findViewById(R.id.delete);
        EditText name = (EditText) dialogUpdate.findViewById(R.id.button_name);
        EditText command = (EditText) dialogUpdate.findViewById(R.id.command);

        name.setText(data.getButtonName());
        command.setText(data.getCommand());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getInstance(getApplicationContext()).btDao().update(command.getText().toString().trim(),
                        name.getText().toString().trim(),
                        data.getID());
                getList();
                dialogUpdate.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getInstance(getApplicationContext()).btDao().deleteByID(data.getID());
                getList();
                dialogUpdate.dismiss();
            }
        });
        dialogUpdate.show();
    }
}
