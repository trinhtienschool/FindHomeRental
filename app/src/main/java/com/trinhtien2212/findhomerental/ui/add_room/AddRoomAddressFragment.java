package com.trinhtien2212.findhomerental.ui.add_room;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.trinhtien2212.findhomerental.R;
import com.trinhtien2212.findhomerental.model.Room;

import java.util.HashMap;
import java.util.Map;


public class AddRoomAddressFragment extends Fragment {
    private View view;
    private EditText txt_province, txt_district, txt_commune, txt_street, txt_apart_number, txt_phone;
    private Button btn_next_info;
    private AddRoomActivity addRoomActivity = (AddRoomActivity) getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_room_address, container, false);
        addRoomActivity = (AddRoomActivity) getActivity();
        init();

        btn_next_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleClickBtn();

            }
        });
        return view;
    }
    private void handleClickBtn(){
        Map<String, Object> addressMap = getFieldData();
        if(addressMap !=null){
            addRoomActivity.getAddress(addressMap);
        }
    }
    private Map<String,Object> getFieldData() {
        String province = txt_province.getText().toString().trim();
        String district = txt_district.getText().toString().trim();
        String commune = txt_commune.getText().toString().trim();
        String street = txt_street.getText().toString().trim();
        String apart_number = txt_apart_number.getText().toString().trim();
        String phone = txt_phone.getText().toString().trim();
        if (province.isEmpty() || province.equals("Trường này không được bỏ trống")) {
            setError(txt_province);
            return null;
        }
        if (district.equals("Trường này không được bỏ trống") || district.isEmpty()) {
            setError(txt_district);
            return null;
        }
        if (commune.isEmpty() || commune.equals("Trường này không được bỏ trống")) {
            setError(txt_commune);
            return null;
        }
        if (street.isEmpty() || street.equals("Trường này không được bỏ trống")) {
            setError(txt_street);
            return null;
        }
        if (apart_number.isEmpty() || apart_number.equals("Trường này không được bỏ trống")) {
            setError(txt_apart_number);
            return null;
        }
        if (phone.isEmpty() || phone.equals("Trường này không được bỏ trống")) {
            setError(txt_phone);
            return null;
        }

        Map<String,Object>map = new HashMap<String, Object>();
        map.put("address",apart_number+" "+street+", "+commune+", "+district+", "+province);
        map.put("phone",phone);
        return map;
    }

    private void setError(EditText editText) {
        editText.setText("Lỗi: Trường này không được bỏ trống");
        editText.setTextColor(Color.RED);
        Toast.makeText(addRoomActivity,"Lỗi",Toast.LENGTH_SHORT).show();
    }

    private void init() {
        txt_province = view.findViewById(R.id.txt_province);
        txt_province.requestFocus();

        txt_district = view.findViewById(R.id.txt_district);

        txt_commune = view.findViewById(R.id.txt_commune);

        txt_street = view.findViewById(R.id.txt_street);

        txt_apart_number = view.findViewById(R.id.txt_apart_number);

        txt_phone = view.findViewById(R.id.txt_phone);

        Room room = addRoomActivity.sendRoom();
        if(addRoomActivity.sendRoom() != null){
           String address = room.getAddress();
           String[] arr = address.split(",");
           if(arr.length == 4){
               String apart_street = arr[0];
               txt_apart_number.setText(apart_street.substring(0,apart_street.indexOf(" ")));
               txt_street.setText(apart_street.substring(apart_street.indexOf(" ")+1));
               txt_commune.setText(arr[1]);
               txt_district.setText(arr[2]);
               txt_province.setText(arr[3]);
           }else{
               txt_province.setText(address);
           }
            txt_phone.setText(room.getPhone());
        }

        btn_next_info = view.findViewById(R.id.btn_next_info);

        setOnClickField(txt_province,txt_district);
        setOnClickField(txt_district,txt_commune);
        setOnClickField(txt_commune,txt_street);
        setOnClickField(txt_street,txt_apart_number);
        setOnClickField(txt_apart_number,txt_phone);

        txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_phone.setText("");
                txt_phone.setTextColor(Color.BLACK);
            }
        });
        txt_phone.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("KeyCode",""+keyCode);
                Log.e("KeyEvent",event.getAction()+"");
                // If the event is a key-down event on the "enter" button
                if (keyCode == 66)
                {
                    handleClickBtn();
                    return true;
                }
                return false;
            }
        });

    }
    private void setOnClickField(EditText editText,EditText nextEdit){
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                editText.setTextColor(Color.BLACK);
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66 && event.getAction() == 0){
                    Log.e("Edit: ",editText.getId()+" : "+nextEdit.getText().toString());
                    editText.clearFocus();
                    nextEdit.requestFocus();
                    return true;
                }
                return false;
            }
        });


    }

}