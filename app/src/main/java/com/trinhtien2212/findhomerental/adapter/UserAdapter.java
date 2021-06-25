package com.trinhtien2212.findhomerental.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.User;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> mListUser;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }
    // item click
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public void setData(List<User> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent,false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User User = mListUser.get(position);
        if(User == null){
            return;
        }
        holder.txtName.setText(User.getDisplayName());
        holder.txtEmail.setText(User.getEmail());
//        ToDo
//        holder.imgUser.setImageResource(User.getPhotoUrl());
        Util.setImage(holder.imgUser,User.getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return mListUser == null ? 0 : mListUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtEmail;
        private ImageView imgUser;
        private ImageButton btnDelete;

        public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.titleName);
            txtEmail = itemView.findViewById(R.id.titleEmail);
            imgUser = itemView.findViewById(R.id.imageUser);
            btnDelete = itemView.findViewById(R.id.ImgViewDelete);

            // Todo set action for item
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
        }
    }
}

