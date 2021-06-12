package com.trinhtien2212.findhomerental.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;

import java.util.List;

public class RoomHomeAdapter extends RecyclerView.Adapter<RoomHomeAdapter.RoomViewHolder>{
    private List<Room> mListroom;


    public void setData(List<Room> list){
        this.mListroom = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
//        holder.txtName.setText(room.getName());
        holder.txtPrice.setText(room.getCost()+"");
        holder.txtAddress.setText(room.getAddress());
//        ToDo
//        holder.imgHome.setImageResource(room.get());
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtPrice, txtAddress;
        private ImageView imgHome;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.title2);
            txtPrice = itemView.findViewById(R.id.titlePrice2);
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome2);
        }
    }
}
