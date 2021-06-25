package com.trinhtien2212.findhomerental.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trinhtien2212.findhomerental.MainActivity;
import com.trinhtien2212.findhomerental.R;


public class ContactFragment extends Fragment {
    TextView textZalo,textGmail,textPhone,textPhone2;
    private View view;
    private MainActivity mainActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_contact, container, false);
        //Todo Hang
        textZalo=view.findViewById(R.id.text_zalo);
        textGmail=view.findViewById(R.id.text_gmail);
        textPhone=view.findViewById(R.id.text_phone);
        textPhone2=view.findViewById(R.id.text_phone2);
//        textZalo=view.findViewById(R.id.text_zalo);
        mainActivity = (MainActivity) getActivity();


        textPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0987654321"));
                startActivity(intent);
            }
        });
        textPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0332089065"));
                startActivity(intent);
            }
        });
        textGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "18130068@st.hcmuaf.edu.vn"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

        textZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookUrl = "https://www.facebook.com/NongLamUniversity";
                try {


                    int versionCode = mainActivity.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) {
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } else {
                        Uri uri = Uri.parse("fb://page/<id_here>");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }
            }


        });

        return view;
    }



}