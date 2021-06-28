package com.trinhtien2212.findhomerental.adapter;

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.ArrayList;
import java.util.List;

public class RoomAdminAdapter extends RecyclerView.Adapter<RoomAdminAdapter.RoomViewHolder> implements Filterable{
    private List<Room> mListroom;
    private List<Room> mListroomOld;
    private OnItemClickListener mListener;

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

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onReportClick(int position);
    }

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
        Log.e("RoomAdminAdap","DANG VAO bindView");
        holder.txtAddress.setText(room.getAddress());
        holder.tv_cost.setText("Giá thuê 1 tháng: "+Util.formatCurrency(room.getCost()));
        Log.e("RoomAdminAdap","DANG VAO da qua getCost"+Util.formatCurrency(room.getCost())+" : "+Util.formateDate(room.getDateCreated()));
        Log.e("RoomDate",room.getDateCreated().toString());
        holder.tv_dateCreated.setText("Ngày đăng: "+Util.formateDate(room.getDateCreated()));
        Log.e("RoomAdminAdap","Da qua bindView");
    }

    @Override
    public int getItemCount() {
        return mListroom == null ? 0 : mListroom.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private TextView txtAddress,tv_cost,tv_dateCreated;
        private ImageView imgHome;
        private ImageButton btnReport, btnDelete;

        public RoomViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Log.e("RoomAdminAdap","DANG VAO khoi tao roomview holder");
            txtAddress = itemView.findViewById(R.id.titleAddress2);
            imgHome = itemView.findViewById(R.id.imageHome);
            btnDelete = itemView.findViewById(R.id.imageButtonDelete);
            btnReport = itemView.findViewById(R.id.imageButtonReport);
            tv_cost = itemView.findViewById(R.id.tv_cost_room_admin);
            tv_dateCreated = itemView.findViewById(R.id.tv_dateCreated);
            Log.e("RoomAdminAdap","Da qua khoi tao roomview holder");
            // Todo set action for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Log.e("RoomListAdminAdapter","Có vào");
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
                            listener.onReportClick(position);
                        }
                    }
                }
            });
        }
    }
}
