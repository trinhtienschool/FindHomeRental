package com.trinhtien2212.mobilefindroomrental.presenter;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trinhtien2212.mobilefindroomrental.ui.home.GoogleSignIn;

public class SignInPresenter {
    GoogleSignInClient mGoogleSignInClient;
    final int RC_SIGN_IN= 12345;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Context context;
    public SignInPresenter(Context context){
        this.context =context;
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("127210357343-05dpqllrk3t6r3srb6m7alediirnifqt.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso);

    }

}
