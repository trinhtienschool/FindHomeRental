package com.trinhtien2212.findhomerental.ui;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.User;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    public static String formatDistance(double distance){
        double meter = distance*1000;
        // lam tron len gom 3 so thap phan, nhan va chia cho 1000
        double meterAround = (double) Math.round(meter * 10) / 10;
        String dis = meterAround +" m";
        return dis;
    }
    public static  String formatCurrency(int price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return numberFormat.format(price);
    }
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize, String fileName) {
        //rotate

//                Matrix matrix = new Matrix();
//                matrix.postRotate(90);
//                image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);


        //scale

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }


        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static void setImage(ImageView ib, String url){
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.image_holder)
                .error(R.drawable.image_holder)
                .into(ib);

    }

    public static User checkSignIn(){

        FirebaseUser currentUser =  FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser !=null){
            User user = new User();
            user.setDisplayName(currentUser.getDisplayName());
            user.setEmail(currentUser.getEmail());
            user.setPhotoUrl(currentUser.getPhotoUrl().toString());
            user.setUserUid(currentUser.getUid());
            return user;
        }
        return null;
    }
}
