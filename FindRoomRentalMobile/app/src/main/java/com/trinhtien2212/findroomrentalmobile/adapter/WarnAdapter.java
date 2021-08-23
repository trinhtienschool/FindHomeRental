package com.trinhtien2212.findroomrentalmobile.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findroomrentalmobile.R;
import com.trinhtien2212.findroomrentalmobile.model.Notification;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.ui.Util;

import java.util.List;

public class WarnAdapter extends RecyclerView.Adapter<WarnAdapter.RoomViewHolder>{
    private List<Notification> mNotification;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int positon);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setData(List<Notification> list){
        this.mNotification = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warn_item, parent,false);
        return new RoomViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Notification notification = mNotification.get(position);
        if(notification == null){
            return;
        }
//        ToDo
        if(notification.getMessage().length() > 50){
            holder.txtReport.setText(notification.getMessage().substring(0,50) + "...");
        } else{
            holder.txtReport.setText(notification.getMessage());
        }
        holder.txtAddress.setText(notification.getAddress());
        holder.txtDateCreated.setText(Util.formateDate(notification.getDateCreated()));
    }


    @Override
    public int getItemCount() {
        return mNotification == null ? 0 : mNotification.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtReport, txtAddress,txtDateCreated;

        public RoomViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            txtAddress = itemView.findViewById(R.id.titleAddress5);
            txtReport = itemView.findViewById(R.id.titleReport);
            txtDateCreated = itemView.findViewById(R.id.dateCreated);
            // ToDo set action for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Log.e("WarnAdapter","Có vào");
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
