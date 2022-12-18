package id.ac.umn.iot;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProjectList extends RecyclerView.Adapter<AdapterProjectList.ViewHolder> {
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
    private ProjectListListener listener;

    public AdapterProjectList(List<MainData> dataList, Activity context, ProjectListListener listener) {
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_project_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProjectList.ViewHolder holder, int position) {
        MainData data = dataList.get(position);
        database=RoomDB.getInstance(context);

        holder.projectName.setText(data.getProjectName());
        if(data.getProjectType().equals("api")){
            holder.iconProject.setImageResource(R.drawable.ic_baseline_api);
            holder.projectDesc.setText("API project connection");
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
        TextView projectName, projectDesc;
        ImageView iconProject;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            projectName=itemView.findViewById(R.id.projectName);
            iconProject=itemView.findViewById(R.id.iconProject);
            projectDesc=itemView.findViewById(R.id.projectDesc);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    MainData data = dataList.get(pos);
                    String projectType = data.getProjectType();

                   if(projectType.equals("api")){
                       Intent projectIntent = new Intent(context, ActivityProjectApi.class);
                       projectIntent.putExtra("device_token", data.getDeviceToken());
                       projectIntent.putExtra("project_name",data.getProjectName());
                       context.startActivity(projectIntent);
                   }else{
                       Intent projectIntent = new Intent(context, ActivityProjectBluetooth.class);
                       projectIntent.putExtra("parent_id", data.getID());
                       context.startActivity(projectIntent);
                   }
                }
            });
        }
    }
}
