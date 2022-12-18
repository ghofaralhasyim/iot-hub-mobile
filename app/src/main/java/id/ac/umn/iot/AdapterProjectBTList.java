package id.ac.umn.iot;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProjectBTList extends RecyclerView.Adapter<AdapterProjectBTList.ViewHolder>{
    private List<BTData> dataList;
    private Activity context;
    private RoomDB database;

    private BtnBluetoothListener listener;

    public AdapterProjectBTList(List<BTData> dataList, Activity context, BtnBluetoothListener listener) {
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public AdapterProjectBTList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_button,parent,false);

        return new AdapterProjectBTList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProjectBTList.ViewHolder holder, int position) {
        BTData data= dataList.get(position);
        database=RoomDB.getInstance(context);
        holder.btnProject.setText(data.getButtonName());
        holder.btnProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBtnClicked(data);
            }
        });
        holder.btnProject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onBtnLongClicked(data);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnProject;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            btnProject=itemView.findViewById(R.id.btnProject);
        }
    }


}
