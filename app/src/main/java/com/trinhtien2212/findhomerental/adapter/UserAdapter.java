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

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> mListUser;
//    private List<User> mListUserOld;


    public void setData(List<User> list){
        this.mListUser = list;
//        this.mListUserOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User User = mListUser.get(position);
        if(User == null){
            return;
        }
        holder.txtName.setText(User.getName());
        holder.txtEmail.setText(User.getEmail());
        holder.imgHome.setImageResource(User.getImg());
    }

    @Override
    public int getItemCount() {
        return mListUser == null ? 0 : mListUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtEmail;
        private ImageView imgHome;
        private ImageButton btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.titleName);
            txtEmail = itemView.findViewById(R.id.titleEmail);
            imgHome = itemView.findViewById(R.id.imageHome);
            btnDelete = itemView.findViewById(R.id.ImgViewDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListUser.remove(getAdapterPosition());
                }
            });
        }


    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String strSearch = constraint.toString();
//                if(strSearch.isEmpty()){
//                    mListUser = mListUserOld;
//                } else{
//                    List<User> list = new ArrayList<>();
//                    for(User r: mListUserOld){
//                        if(r.getName().toLowerCase().contains(strSearch.toLowerCase()) || r.getAddress().contains(strSearch.toLowerCase())){
//                            list.add(r);
//                        }
//                    }
//                    mListUser = list;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mListUser;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                mListUser = (List<User>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
}

