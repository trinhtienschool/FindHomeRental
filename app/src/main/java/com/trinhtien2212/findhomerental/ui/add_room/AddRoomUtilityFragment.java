package com.trinhtien2212.findhomerental.ui.add_room;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;
import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;


public class AddRoomUtilityFragment extends Fragment {
    AddRoomActivity addRoomActivity;

    View view;
    TableLayout tb ;
    ImageButton ib_image1,ib_image2,ib_image3,ib_image4,ib_image5,ib_image6;
    CheckBox cb_tivi,cb_ward,cb_wifi,cb_fre,cb_ac,cb_att,cb_hotWater,cb_wc,cb_freeTime,cb_security,cb_park;
    Button btn_add_room;
    List<String>imageUris;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_room_utility, container, false);
        addRoomActivity = (AddRoomActivity) getActivity();
        init();


        return view;
    }
    private void init(){
        cb_tivi = view.findViewById(R.id.cb_tivi);
        cb_ward = view.findViewById(R.id.cb_wardrobe);
        cb_wifi = view.findViewById(R.id.cb_wifi);
        cb_fre = view.findViewById(R.id.cb_fre);
        cb_ac = view.findViewById(R.id.cb_air_con);
        cb_att = view.findViewById(R.id.cb_attic);
        cb_hotWater = view.findViewById(R.id.cb_hotWater);
        cb_wc = view.findViewById(R.id.cb_wc);
        cb_freeTime = view.findViewById(R.id.cb_free_time);
        cb_security = view.findViewById(R.id.cb_security);
        cb_park = view.findViewById(R.id.cb_park);

        imageUris = new ArrayList<String>();
        tb = view.findViewById(R.id.table_layout);
        ib_image1 = view.findViewById(R.id.ib_image1);
        setImageButtonClickListener(ib_image1);
        ib_image2 = view.findViewById(R.id.ib_image2);
        setImageButtonClickListener(ib_image2);
        ib_image3 = view.findViewById(R.id.ib_image3);
        setImageButtonClickListener(ib_image3);
        ib_image4 = view.findViewById(R.id.ib_image4);
        setImageButtonClickListener(ib_image4);
        ib_image5 = view.findViewById(R.id.ib_image5);
        setImageButtonClickListener(ib_image5);
        ib_image6 = view.findViewById(R.id.ib_image6);
        setImageButtonClickListener(ib_image6);

        btn_add_room = view.findViewById(R.id.btn_save_room);

        Room room = addRoomActivity.sendRoom();
        //ToDo
        if(room!=null){
            List<String>images = room.getImages();
            if(images.size()>0) setUpdateImage(ib_image1,images.get(0));
            if(images.size()>1) setUpdateImage(ib_image2,images.get(1));
            if(images.size()>2) setUpdateImage(ib_image3,images.get(2));
            if(images.size()>3) setUpdateImage(ib_image4,images.get(3));
            if(images.size()>4) setUpdateImage(ib_image5,images.get(4));
            if(images.size()>5) setUpdateImage(ib_image6,images.get(5));


            if(room.isTivi()) setChecked(cb_tivi);
            if(room.isWardrobe()) setChecked(cb_ward);
            if(room.isWifi()) setChecked(cb_wifi);
            if(room.isFre()) setChecked(cb_fre);
            if(room.isAttic()) setChecked(cb_att);
            if(room.isAirCondition()) setChecked(cb_ac);
            if(room.isHotWater()) setChecked(cb_hotWater);
            if(room.isWC()) setChecked(cb_wc);
            if(room.isFreeTime()) setChecked(cb_freeTime);
            if(room.isFence()) setChecked(cb_security);
            if(room.isPark()) setChecked(cb_park);

            btn_add_room.setText("Cập nhật");
            imageUris = room.getImages();


        }

        btn_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickSaveButton();
            }
        });
    }
    private void setChecked(CheckBox cb){
        cb.setChecked(true);
    }
    private void setUpdateImage(ImageButton ib,String url){
        Util.setImage(ib,url);
    }
    private void handleClickSaveButton(){
        List<String>utilities = getAllCheckedCheckbox();
        if(checkError(utilities)) return;
        Map<String,Object>utilitiesMap = new HashMap<String,Object>();
        utilitiesMap.put("images",imageUris);
        utilitiesMap.put("utilities",utilities);
       addRoomActivity.saveRoom(utilitiesMap);


    }
    private boolean checkError(List<String>utilities){
        boolean hasError = false;
        if(imageUris.size()<5){
            Toast.makeText(addRoomActivity,"Bạn phải chọn ít nhất 5 ảnh",Toast.LENGTH_SHORT).show();
            hasError = true;
        }

        if(utilities.isEmpty()){
            Toast.makeText(addRoomActivity,"Bạn phải chọn ít nhất 1 Tiện ích",Toast.LENGTH_LONG).show();
            hasError = true;
        }
        return hasError;
    }
    private List<String> getAllCheckedCheckbox(){
        List<String> utilities = new ArrayList<String>();

        for (int i = 0; i < tb.getChildCount(); i++) {
            View child = tb.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                for (int x = 0; x < row.getChildCount(); x++) {
                    View view = row.getChildAt(x);
                    if(view instanceof CheckBox){
                        CheckBox checkBox = (CheckBox)view;
                        if(checkBox.isChecked()){
                            utilities.add(checkBox.getText().toString());
                            Log.e("Checked",checkBox.getText().toString());
                        }
                    }
                }
            }
        }
        return utilities;
    }
    private void setImageButtonClickListener(ImageButton ib){
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(ib);
            }
        });
    }
    private void requestPermission(ImageButton ib){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(addRoomActivity, "Chọn 1 ảnh", Toast.LENGTH_SHORT).show();
                openGallery(ib);
            }


            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(addRoomActivity, "Không đồng ý cấp quyền truy cập thiết bị", Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(addRoomActivity)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Nếu bạn không đồng ý, bạn không thể chọn được ảnh")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();


    }

    private void openGallery(ImageButton ib){
        TedBottomPicker.with(addRoomActivity)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {


                        Bitmap bMap = BitmapFactory.decodeFile(uri.getPath());
                        Log.e("Size Before",bMap.getWidth()+" : "+bMap.getHeight());
                        if (ib.equals(ib_image1)) {
                            bMap = Util.getResizedBitmap(bMap, 512,uri.getPath());
                        }else{
                            bMap = Util.getResizedBitmap(bMap,140,uri.getPath());
                        }
                        Log.e("Size After",bMap.getWidth()+" : "+bMap.getHeight());
//                        ib.setImageURI(uri);
                        ib.setRotation(90);
                        Log.e("Uri",uri.getPath());
                        ib.setImageBitmap(bMap);
//                        Log.e("Da set","Da set");
                        imageUris.add(uri.getPath());
                    }
                });
    }
}