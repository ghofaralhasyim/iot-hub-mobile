package iot.umn.ac.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {
    private LinkedList<Buttons> mButtons;
    private LayoutInflater mInflater;
    private Context mContext;

    public ButtonAdapter(Context context, LinkedList<Buttons> mButtons) {
        this.mButtons = mButtons;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ButtonAdapter.ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.ButtonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
