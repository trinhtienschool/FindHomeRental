package com.trinhtien2212.findhomerental.ui.add_room;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.trinhtien2212.findhomerental.ui.Util;

import java.util.HashMap;
import java.util.Map;


public class AddRoomInfoFragment extends Fragment {
    private View view;
    private EditText txt_costPerMonth, txt_depositCost, txt_eleCost, txt_watCost, txt_description;
    private Button btn_NextImageFrag;
    private AddRoomActivity addRoomActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_room_info, container, false);
        addRoomActivity = (AddRoomActivity) getActivity();
        init();
        return view;
    }

    public void init() {
        txt_costPerMonth = view.findViewById(R.id.txt_costPerMonth);
        txt_costPerMonth.requestFocus();
        txt_depositCost = view.findViewById(R.id.txt_depositCost);
        txt_eleCost = view.findViewById(R.id.txt_eleCost);
        txt_watCost = view.findViewById(R.id.txt_watCost);
        txt_description = view.findViewById(R.id.txt_description);

        Room room = addRoomActivity.sendRoom();
        if(room !=null){
            txt_costPerMonth.setText(Util.formatCurrency(room.getCost()));
            txt_depositCost.setText(Util.formatCurrency(room.getDeposit()));
            txt_eleCost.setText(Util.formatCurrency(room.getEleCost()));
            txt_watCost.setText(Util.formatCurrency(room.getWatCost()));
            txt_description.setText(room.getDescription());
        }
        
        
        //click field
        setOnClickField(txt_costPerMonth);
        setOnClickField(txt_depositCost);
        setOnClickField(txt_eleCost);
        setOnClickField(txt_watCost);
        setOnClickField(txt_description);

        //travel next field
        setOnNextField(txt_costPerMonth, txt_depositCost);
        setOnNextField(txt_depositCost, txt_eleCost);
        setOnNextField(txt_eleCost, txt_watCost);
        setOnNextField(txt_watCost, txt_description);

        //format currency
        setFormatCurrency(txt_costPerMonth);
        setFormatCurrency(txt_depositCost);
        setFormatCurrency(txt_eleCost);
        setFormatCurrency(txt_watCost);

        btn_NextImageFrag = view.findViewById(R.id.btn_NextImageFrag);
        btn_NextImageFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickBtn();
            }
        });
    }

    private void handleClickBtn() {

        Map<String, Object> addressMap = getFieldData();
        if (addressMap != null) {
            addRoomActivity.getInfo(addressMap);
        }
    }

   

    private Map<String, Object> getFieldData() {
        boolean hasError = false;
        int costPM = checkError(txt_costPerMonth, 500000);
        if (costPM == -1)  hasError = true;

        int dCost = checkError(txt_depositCost, 100000);
        if (dCost == -1)  hasError = true;

        int eCost = checkError(txt_eleCost, 1000);
        if (eCost == -1)  hasError = true;

        int wCost = checkError(txt_watCost, 1000);
        if (wCost == -1)  hasError = true;

        String description = txt_description.getText().toString().trim();
        if (description.isEmpty() || description.startsWith("Lỗi:")) {
            setError(txt_description, "Trường này không được bỏ trống");
           hasError = true;
        }
        if(hasError) return null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("costPerMonth", costPM);
        map.put("description", description);
        map.put("dCost", dCost);
        map.put("eCost", eCost);
        map.put("wCost", wCost);
        return map;
    }

    private int checkError(EditText editText, int minimum) {
        String costString = editText.getText().toString().trim();
        if (costString.isEmpty() || costString.startsWith("Lỗi:")) {
            setError(editText, "Trường này không được bỏ trống");
            return -1;
        } else {

            int cost = Integer.parseInt(getNumberText(costString));
            if (cost <= minimum) {
                setError(editText, "Giá tiền phải trên " + Util.formatCurrency(minimum));
                return -1;
            }
            return cost;
        }
    }

    private void setFormatCurrency(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TRuoc",s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("S", s.toString());
                Log.e("Start", start + "");
                Log.e("before", before + "");
                Log.e("Count", count + "");

                if (count > 1) return;

                editText.removeTextChangedListener(this);
                editText.setTextColor(Color.BLACK);
                //Nhan su kien xoa
                String numText = "";
                numText = getNumberText(s.toString());
                if (count == 0 && !numText.isEmpty()) {
                    numText = numText.substring(0, numText.length() - 1);
                }
                if ((numText.length() >= 10 && !numText.startsWith("1"))) {
                    numText = numText.substring(0, numText.length() - 1);
                    Toast.makeText(addRoomActivity, "Tiền không được lớn hơn 1 tỷ VNĐ", Toast.LENGTH_SHORT).show();
                }
                String currency = "";
                if (numText.isEmpty()) {
                    editText.setText(currency);
                } else {
                    currency = Util.formatCurrency(Integer.parseInt(numText));
                    editText.setText(currency);
                }
                editText.setSelection(currency.length());
                editText.addTextChangedListener(this);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private String getNumberText(String s) {
        if(s.startsWith("Lỗi: ")) return "";
        String numberText = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                numberText += c;
            }
        }
        return numberText;
    }
    private void setOnClickField(EditText editText){
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.startsWith("Lỗi:")) {
                    editText.setText("");
                }else editText.setSelection(text.length());
                editText.setTextColor(Color.BLACK);
            }
        });
    }
    private void setOnNextField(EditText editText, EditText nextEdit) {
        if (editText.hasFocus()) editText.setText("");
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66 && event.getAction() == 0) {
                    editText.clearFocus();
                    nextEdit.requestFocus();
                    return true;
                }
                return false;
            }
        });


    }

    private void setError(EditText editText, String error) {
        editText.setText("Lỗi: " + error);
        editText.setTextColor(Color.RED);
        Toast.makeText(addRoomActivity, "Lỗi", Toast.LENGTH_SHORT).show();
    }
}