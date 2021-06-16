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

public class RoomHomeAdapter extends RecyclerView.Adapter<RoomHomeAdapter.RoomViewHolder>{
    private List<Room> mListroom;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int positon);
    }

    public void setData(List<Room> list){
        this.mListroom = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);
        return new RoomViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
        // Todo set img, price, address
        Util.setImage(holder.imgHome,room.getImages().get(0));
        holder.txtPrice.setText(Util.formatCurrency(room.getCost()));
        holder.txtAddress.setText(room.getAddress());
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView  txtPrice, txtAddress;
        private ImageView imgHome;

        public RoomViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.titlePrice2);
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome2);

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
        }
    }
}
