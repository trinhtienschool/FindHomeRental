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

public class RoomAdminAdapter extends RecyclerView.Adapter<RoomAdminAdapter.RoomViewHolder> {
    private List<Room> mListroom;
//    private List<Room> mListroomOld;


    public void setData(List<Room> list){
        this.mListroom = list;
//        this.mListroomOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent,false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = mListroom.get(position);
        if(room == null){
            return;
        }
        holder.txtAddress.setText(room.getAddress());
//        ToDo
//        holder.imgHome.setImageResource(room.getImages(0));
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress;
        private ImageView imgHome;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome);
        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String strSearch = constraint.toString();
//                if(strSearch.isEmpty()){
//                    mListroom = mListroomOld;
//                } else{
//                    List<Room> list = new ArrayList<>();
//                    for(Room r: mListroomOld){
//                        if(r.getName().toLowerCase().contains(strSearch.toLowerCase()) || r.getAddress().contains(strSearch.toLowerCase())){
//                            list.add(r);
//                        }
//                    }
//                    mListroom = list;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mListroom;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                mListroom = (List<Room>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}
