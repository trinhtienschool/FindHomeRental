package com.trinhtien2212.findroomrentalmobile.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.trinhtien2212.findroomrentalmobile.MainActivity;
import com.trinhtien2212.findroomrentalmobile.R;
import com.trinhtien2212.findroomrentalmobile.model.User;
import com.trinhtien2212.findroomrentalmobile.ui.home.IGetMyLocation;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Util {
    public static String formatDistance(double distance){
        double meter = distance*1000;
        // lam tron len gom 3 so thap phan, nhan va chia cho 1000
        double meterAround = (double) Math.round(meter * 10) / 10;
        String dis = meterAround +" m";
        return dis;
    }

    public static String formateDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
    public static String formatDateConnectServer(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
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
                .transform(new PiccassoTranform())
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
    public static void showSnackbar(View view, String message)
    {
        // Create snackbar
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);

        // Set an action on it, and a handler
        snackbar.setAction("Ẩn", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }
    public static boolean checkGPS(Context context, IGetMyLocation getMyLocation){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            return false;
        }
        if(!gps_enabled) getMyLocation.showSnackbar("Không thể lấy vị trí hiện tại");
        return gps_enabled;

    }
    public static boolean checkNetwork(Context context, IGetMyLocation getMyLocation) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnect = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if(!isConnect) getMyLocation.showSnackbar("Không thể kết nối Internet");
        return isConnect;
    }
    public static void getMyLocation(Context context, IGetMyLocation getMyLocation){
        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Log.e("Location","Location1");
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            //init geoCoder
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                            //get address list
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            // Set Latitiude on text view
                            Log.e("AddressLIne",addresses.get(0).getAddressLine(0));
                            getMyLocation.returnMyLocation(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
