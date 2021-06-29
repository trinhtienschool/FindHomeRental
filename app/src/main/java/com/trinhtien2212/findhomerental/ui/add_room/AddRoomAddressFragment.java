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

import javax.security.auth.login.LoginException;


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
            Log.e("address",addressMap.toString());
            addRoomActivity.setAddress(addressMap);
        }
    }

    private Map<String,Object> getFieldData() {
        boolean hasError = false;
        String province = checkError(txt_province);
        if(province ==null) hasError = true;
        String district = checkError(txt_district);
        if(district ==null) hasError = true;
        String commune =checkError(txt_commune);
        if (commune == null) hasError = true;
        String street = checkError(txt_street);
        if(street == null) hasError = true;
        String apart_number = checkError(txt_apart_number);
        if(apart_number == null) hasError = true;
        String phone = checkError(txt_phone);
        if(phone == null) hasError = true;
//        Log.e("Phone",phone.length()+"");
        if(phone != null && !(phone.length() ==10 || phone.length() ==11)){
            Log.e("VoDayTRuoc","Vodaytruoc");
            hasError = true;
            txt_phone.clearFocus();
            txt_phone.setTextColor(Color.RED);
            txt_phone.setText("Lỗi: Số điện thoại không hợp lệ");
        }

        if(hasError) return null;

        Map<String,Object>map = new HashMap<String, Object>();
        map.put("address",apart_number+" "+street+", "+commune+", "+district+", "+province);
        map.put("phone",phone);
        return map;
    }
    private String checkError(EditText editText){
        String text = editText.getText().toString().trim();
        Log.e("Text",text);
        if (text.isEmpty() || text.contains("Lỗi")) {
            Log.e("DangVaoError",text);
            setError(editText);
            return null;
        }
        return text;
    }
    private void setError(EditText editText) {
        editText.setText("Lỗi: Trường này không được bỏ trống");
        editText.setTextColor(Color.RED);
        Toast.makeText(addRoomActivity,"Lỗi",Toast.LENGTH_SHORT).show();
    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }
    private void init() {
        txt_province = view.findViewById(R.id.txt_province);
//        disableEditText(txt_province);

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
                String text = txt_phone.getText().toString();
                if(text.contains("Lỗi")) {
                    txt_phone.setText("");
                }
                txt_phone.setTextColor(Color.BLACK);
            }
        });
        txt_phone.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("KeyCode",""+keyCode);
                Log.e("KeyEvent",event.getAction()+"");
                // If the event is a key-down event on the "enter" button
                if (keyCode == 66 && event.getAction() == 1)
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
                String text = editText.getText().toString();
                if(text.startsWith("Lỗi")) {
                    editText.setText("");
                }
                editText.setTextColor(Color.BLACK);
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66 && event.getAction() == 1){
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