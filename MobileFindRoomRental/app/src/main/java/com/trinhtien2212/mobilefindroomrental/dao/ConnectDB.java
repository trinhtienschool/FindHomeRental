package com.trinhtien2212.mobilefindroomrental.dao;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public abstract class ConnectDB {
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();


}
