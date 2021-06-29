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
import com.trinhtien2212.findhomerental.UserListActivity;
import com.trinhtien2212.findhomerental.model.User;
import com.trinhtien2212.findhomerental.presenter.AdminUserPresenter;
import com.trinhtien2212.findhomerental.presenter.IUserResult;
import com.trinhtien2212.findhomerental.presenter.StatusResult;
import com.trinhtien2212.findhomerental.presenter.UserManagerPresenter;
import com.trinhtien2212.findhomerental.ui.Util;
import com.trinhtien2212.findhomerental.ui.home.IGetMyLocation;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private AdminUserPresenter adminUserPresenter = AdminUserPresenter.getInstance();
    private List<User> mListUser;
    private OnItemClickListener mListener;
    private UserListActivity userListActivity;
    public interface OnItemClickListener {
        void onDeleteClick(int position);

        void onAdminClick(int position);

        void onHouseClick(int position);

        void onNoteClick(int position);
    }
    public UserAdapter(UserListActivity userListActivity){
        this.userListActivity = userListActivity;
    }
    // item click
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public void setData(List<User> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User User = mListUser.get(position);
        if (User == null) {
            return;
        }
        holder.txtName.setText(User.getDisplayName());
        holder.txtEmail.setText(User.getEmail());
//        ToDo
//        holder.imgUser.setImageResource(User.getPhotoUrl());
        Util.setImage(holder.imgUser, User.getPhotoUrl());
        if (adminUserPresenter.checkIsAdmin(User.getUserUid())) {
            holder.btnAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24_yellow);
            holder.btnAdmin.setTag(R.drawable.ic_baseline_admin_panel_settings_24_yellow);
        } else {
            holder.btnAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24);
            holder.btnAdmin.setTag(R.drawable.ic_baseline_admin_panel_settings_24);
        }
    }

    @Override
    public int getItemCount() {
        return mListUser == null ? 0 : mListUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements StatusResult, IGetMyLocation {
        private TextView txtName, txtEmail;
        private ImageView imgUser;
        private ImageButton btnDelete, btnHouse, btnNote, btnAdmin;
        private AdminUserPresenter adminUserPresenter;

        public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            adminUserPresenter = AdminUserPresenter.getInstance();
            txtName = itemView.findViewById(R.id.titleName);
            txtEmail = itemView.findViewById(R.id.titleEmail);
            imgUser = itemView.findViewById(R.id.imageUser);
            btnHouse = itemView.findViewById(R.id.imgButtonHouse);
            btnAdmin = itemView.findViewById(R.id.img_admin_panel);
            btnNote = itemView.findViewById(R.id.imageButtonReport);
            btnDelete = itemView.findViewById(R.id.imageButtonDelete);

            // Todo set action for item
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            btnNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onNoteClick(position);
                        }
                    }
                }
            });
            btnAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onAdminClick(position);
                            User user = mListUser.get(position);
                            if (btnAdmin.getTag().equals(R.drawable.ic_baseline_admin_panel_settings_24))
                                adminUserPresenter.addUserAdmin(user.getUserUid(), UserViewHolder.this);
                            else
                                adminUserPresenter.delUserAdmin(user.getUserUid(), UserViewHolder.this);
                        }


                    }

                }
            });
            btnHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHouseClick(position);
                        }
                    }
                }
            });
        }


        @Override
        public void onFail() {

        }

        @Override
        public void onSuccess() {
            if (btnAdmin.getTag().equals(R.drawable.ic_baseline_admin_panel_settings_24)) {
                btnAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24_yellow);
                btnAdmin.setTag(R.drawable.ic_baseline_admin_panel_settings_24_yellow);
            } else {
                btnAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24);
                btnAdmin.setTag(R.drawable.ic_baseline_admin_panel_settings_24);
            }
        }

        @Override
        public void returnMyLocation(String location) {

        }

        @Override
        public void showSnackbar(String message) {
            userListActivity.showSnackbar(message);
        }
    }
}

