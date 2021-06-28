package com.trinhtien2212.findhomerental.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> implements Filterable {
    private List<Room> mListroom;
    private List<Room> mListroomOld;

    public void setData(List<Room> list){
        this.mListroom = list;
        this.mListroomOld = list;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_item, parent,false);
        return new RoomViewHolder(view);
    }
    public interface ItemClickListener{
        void onItemClick(int position);
    }
    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
        String address = room.getAddress();

        if(address.length() >=40){
            address = address.substring(0,40)+"...";
        }
        Util.setImage(holder.imgHome,room.getImages().get(0));
        holder.tv_address.setText(address);
        holder.tv_cost.setText("Giá thuê 1 tháng: "+Util.formatCurrency(room.getCost()));
        holder.tv_distance.setText("Khoảng cách: "+Util.formatDistance(room.getLocation().getDistance()));
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_address,tv_cost,tv_distance;
        private ImageView imgHome;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
           imgHome = itemView.findViewById(R.id.imageHome);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_cost = itemView.findViewById(R.id.tv_cost);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            // ToDo set action for item
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemClickListener.onItemClick(getAdapterPosition());
//                }
//            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListroom = mListroomOld;
                } else{
                    List<Room> list = new ArrayList<>();
                    for(Room r: mListroomOld){
                        if(r.getAddress().contains(strSearch.toLowerCase())){
                            list.add(r);
                        }
                    }
                    mListroom = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListroom;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListroom = (List<Room>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
