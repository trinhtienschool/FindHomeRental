package com.trinhtien2212.findhomerental.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.ArrayList;
import java.util.List;

public class RoomAdminAdapter extends RecyclerView.Adapter<RoomAdminAdapter.RoomViewHolder> {
    private List<Room> mListroom;
    private List<Room> mListroomOld;

    private OnItemClickListener mListener; // item click

    // item click
    public interface OnItemClickListener{
        void onItemClick(int positon);
        void onDeleteClick(int position);
        void onReportClick(int position);
    }
    // item click
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setData(List<Room> list){
        this.mListroom = list;
        this.mListroomOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent,false);
        return new RoomViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
        Util.setImage(holder.imgHome,room.getImages().get(0));
        holder.txtAddress.setText(room.getAddress());
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }



    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress;
        private ImageView imgHome;
        private ImageButton btnReport, btnDelete;

        public RoomViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome);
            btnDelete = itemView.findViewById(R.id.imageButtonDelete);
            btnReport = itemView.findViewById(R.id.imageButtonReport);

            // ToDo set action for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Log.e("LoveAdapter","Có vào");
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            btnReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
