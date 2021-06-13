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
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.List;

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.RoomViewHolder>{
    private List<Room> mListroom;


    public void setData(List<Room> list){
        this.mListroom = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.love_item, parent,false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }

        Util.setImage(holder.imgHome,room.getImages().get(0));

        holder.txtPrice.setText(Util.formatCurrency(room.getCost()));
        holder.txtAddress.setText(room.getAddress());
//        ToDo

    }


    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPrice, txtAddress;
        private ImageView imgHome;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.titlePrice4);
            txtAddress = itemView.findViewById(R.id.titleAddress4);
            imgHome = itemView.findViewById(R.id.imageHome4);
        }
    }
}
