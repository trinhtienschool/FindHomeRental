package com.trinhtien2212.mobilefindroomrental.adapter;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.mobilefindroomrental.R;
import com.trinhtien2212.mobilefindroomrental.model.Room;
import com.trinhtien2212.mobilefindroomrental.ui.Util;

import java.util.ArrayList;
import java.util.List;

public class RoomHomeAdapter extends RecyclerView.Adapter<RoomHomeAdapter.RoomViewHolder>{
    private List<Room> mListroom = new ArrayList<>();
    private ItemClickListener mItemClickListener;

    public RoomHomeAdapter(List<Room> rooms, ItemClickListener itemClickListener){
        this.mListroom = rooms;
        this.mItemClickListener = itemClickListener;
    }

    public void setData(List<Room> list){
        this.mListroom = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent,false);
        return new RoomViewHolder(view, mItemClickListener);
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

        //ToDo
        String address = room.getAddress();
        if(address.length() >=40){
            address = address.substring(0,40)+"...";
        }
        holder.txtAddress.setText(address);
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView  txtPrice, txtAddress;
        private ImageView imgHome;


        public RoomViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            txtPrice = itemView.findViewById(R.id.titlePrice2);
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome2);

            // ToDo set action for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

    }
}
