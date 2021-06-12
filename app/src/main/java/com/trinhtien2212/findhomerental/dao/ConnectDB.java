package com.trinhtien2212.findhomerental.dao;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class ConnectDB {
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
}
