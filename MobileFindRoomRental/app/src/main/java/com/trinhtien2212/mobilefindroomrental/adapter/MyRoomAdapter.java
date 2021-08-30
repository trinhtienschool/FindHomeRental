package com.trinhtien2212.mobilefindroomrental.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.mobilefindroomrental.R;
import com.trinhtien2212.mobilefindroomrental.model.Room;
import com.trinhtien2212.mobilefindroomrental.ui.Util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyRoomAdapter extends RecyclerView.Adapter<MyRoomAdapter.RoomViewHolder>{
    private List<Room> mListroom;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int positon);
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setData(List<Room> list){
        this.mListroom = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_room_item, parent,false);
        return new RoomViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
        if(room.getImages() == null || room.getImages().isEmpty()){
            holder.imgHome.setImageResource(R.drawable.image_holder);
        }
        else Util.setImage(holder.imgHome,room.getImages().get(0));

        holder.txtPrice.setText(Util.formatCurrency(room.getCost()));
//        ToDo address
        String address = room.getAddress();
        if(address.length() > 40){
            address = address.substring(0,40) + "...";
        }
        holder.txtAddress.setText(address);

    }


    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPrice, txtAddress;
        private ImageView imgHome, btnDelete, btnEdit;

        public RoomViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.titlePrice3);
            txtAddress = itemView.findViewById(R.id.titleAddress3);
            imgHome = itemView.findViewById(R.id.imageHome3);
            btnDelete = itemView.findViewById(R.id.imageViewDelete);
            btnEdit = itemView.findViewById(R.id.imageViewEdit3);

            // ToDo set action for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
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
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}
