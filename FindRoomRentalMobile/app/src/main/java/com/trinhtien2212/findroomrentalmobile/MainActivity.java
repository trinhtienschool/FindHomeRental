package com.trinhtien2212.findroomrentalmobile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FieldValue;
import com.trinhtien2212.findroomrentalmobile.dao.RoomDB;
import com.trinhtien2212.findroomrentalmobile.dao.RoomDB_Test;
import com.trinhtien2212.findroomrentalmobile.model.Notification;
import com.trinhtien2212.findroomrentalmobile.model.Room;
import com.trinhtien2212.findroomrentalmobile.model.User;
import com.trinhtien2212.findroomrentalmobile.presenter.AdminUserPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.BookmarkPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.IUserResult;
import com.trinhtien2212.findroomrentalmobile.presenter.NotificationPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.NotificationResult;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomPresenter;
import com.trinhtien2212.findroomrentalmobile.presenter.RoomsResult;
import com.trinhtien2212.findroomrentalmobile.presenter.StatusResult;
import com.trinhtien2212.findroomrentalmobile.presenter.UserManagerPresenter;
import com.trinhtien2212.findroomrentalmobile.ui.Util;
import com.trinhtien2212.findroomrentalmobile.ui.add_room.AddRoomActivity;
import com.trinhtien2212.findroomrentalmobile.ui.home.IGetMyLocation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StatusResult, IGetMyLocation{
    //Google sign in
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 12345;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    View hView;
    ImageView iv_header_avatar;
    TextView tv_header_name, tv_header_email;
    private MenuItem menuItem;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private AdminUserPresenter adminUserPresenter;
//    private RealtimeBlurView realtimeBlurView;
//    private ProgressBar pv_saving;
    private FrameLayout frameLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        realtimeBlurView = findViewById(R.id.realtimeBlurView);
//        pv_saving = findViewById(R.id.pb_saving);
        frameLayout = findViewById(R.id.main_activity_baseView);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(!Util.checkNetwork(MainActivity.this,MainActivity.this)) return;
                Log.e("Co", "Co vo dang nahp");
                if(currentUser !=null){
                    startActivity(new Intent(MainActivity.this,AddRoomActivity.class));
                }else Toast.makeText(MainActivity.this,"Bạn phải đăng nhập để đăng phòng trọ",Toast.LENGTH_LONG).show();
                //Test
//                if(currentUser !=null) {
//                    Room room = new Room("12345","84 Lê Văn Chí, Linh Trung, Thủ Đức, Tp. Hồ Chí Minh","03333",null,"Tien",new Date(),false,true,true,true,true,true,true,true,true,true,true,53.3f,435345,2345324,42334,23423,false);
//                    List<String> locations = new ArrayList<String>();
//                    locations.add("https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin.jpg");
//                    locations.add("https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin-22m2.jpg");
//                    locations.add("https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin-15m2-1.jpg");
//                    locations.add("https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin-dep.jpg");
//                    locations.add("https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin-1.jpg");
//                    room.setImages(locations);
//                    room.setRoomID("0Gfbjblv9QYMYnUfNgqW");
//                    Intent intent = new Intent(MainActivity.this,AddRoomActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("room",room);
//                    intent.putExtras(bundle);
//                    Toast.makeText(MainActivity.this, "Thêm bài đăng phòng trọ", Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(MainActivity.this,"Bạn phải đăng để đăng phòng trọ",Toast.LENGTH_LONG).show();
//                }
//                startActivity(new Intent(MainActivity.this, GoogleSignIn.class));
                //Test delete room
//                Room room = new Room();
//                room.setRoomID("0Gfbjblv9QYMYnUfNgqW");
//                RoomPresenter roomPresenter = new RoomPresenter(null,room);
//                roomPresenter.deleteRoom();

//                BookmarkPresenter bookmarkPresenter = new BookmarkPresenter(null);
////                bookmarkPresenter.addBookmark("0aKAzRCirqYW44OFJg4S","At137YkMB7OXy99UzZINGbVExY72");
//                bookmarkPresenter.getAllBookmarks("At137YkMB7OXy99UzZINGbVExY72");


            }
        });
        //Test note
//        NotificationPresenter notificationPresenter = new NotificationPresenter(MainActivity.this);
//        Notification notification = new Notification("84 Lê Văn Chí, Linh Trung","Phòng trọ sai thông tin chi tiết, sai quy định","1234567890");
////        notificationPresenter.addNotification(notification);
//        notificationPresenter.getNotifications("1234567890");


//        //Test user

//        UserManagerPresenter userManagerPresenter = new UserManagerPresenter(this,this);
//        userManagerPresenter.getAllUsers();
//        User user = new User();
//        user.setUserUid("At137YkMB7OXy99UzZINGbVExY72");
//        userManagerPresenter.deleteUser(user);

//        //updateRoom
//        RoomDB_Test roomDB = RoomDB_Test.getInstance();
//        roomDB.getRandomRooms(null);


        //Test filter admin
//        RoomPresenter roomPresenter = new RoomPresenter((RoomsResult) null);
//        roomPresenter.filterRoom(0,5);

        //Test
//        RoomDB_Test roomDB_test = RoomDB_Test.getInstance();
//        roomDB_test.getRandomRooms(null);
//        Log.e("Da xong","Done");
//        Date date = roomDB_test.between();
//        Log.e("Date",date.toString());
//        Log.e("Day", String.valueOf(date.getDay()));
//        Log.e("Month", String.valueOf(date.getMonth()));
//        Log.e("Year",String.valueOf(date.ge));

       adminUserPresenter = AdminUserPresenter.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        iv_header_avatar = (ImageView) hView.findViewById(R.id.iv_header_avatar);
        tv_header_name = (TextView) hView.findViewById(R.id.tv_header_name);
        tv_header_email = (TextView) hView.findViewById(R.id.tv_header_email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e("title", item.getTitle() + "");
                return true;
            }
        });
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myroom, R.id.nav_love, R.id.nav_contactus,
                R.id.nav_warning, R.id.nav_admin)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        showItemAdmin(false);

        //Config google signin
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1063184706225-r7c5nksts21gift065lj2b1i7cvv04al.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build();       

        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
        //end


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("onCreateOptionsMenu","Da vo");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.action_login);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) updateUi(true);
        else updateUi(false);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() == null) return false;
        if(!Util.checkNetwork(this,this)) return true;
        if (item.getTitle().equals("Đăng nhập")) {
//            startActivity(new Intent(MainActivity.this,GoogleSignIn.class));
//            item.setTitle("Đăng xuất");
            signIn();
        } else {
            signOut();
//            item.setTitle("Đăng nhập");
        }
        return super.onOptionsItemSelected(item);
    }


    //Google sign in
    public void signOut() {
        updateUi(false);
        FirebaseAuth.getInstance().signOut();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) updateUi(true);
//        else updateUi(false);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) updateUi(true);
//        else updateUi(false);
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("ErrorSignin", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ErrorSignin", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Error signin", "signInWithCredential:success");
                            currentUser = mAuth.getCurrentUser();
//                            updateUI(user);
                            updateUi(true);
                            //ToDo
                        } else {
                            Toast.makeText(MainActivity.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
//                            Log.w("Error signig", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });

    }

    private void updateUi(boolean isLogin) {
        if (isLogin) {
            setShowAdmin();
            showItemLogin(true);
            menuItem.setTitle("Đăng xuất");
            Util.setImage(iv_header_avatar, currentUser.getPhotoUrl().toString());
            tv_header_name.setText(currentUser.getDisplayName());
            tv_header_email.setText(currentUser.getEmail());
        } else {
            showItemAdmin(false);
            showItemLogin(false);
            menuItem.setTitle("Đăng nhập");
            iv_header_avatar.setImageResource(R.drawable.house);
            tv_header_name.setText("Trang chủ");
            tv_header_email.setText("");
        }
//        nav_user.setText(user);
    }
    private void showItemAdmin(boolean show)
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_admin).setVisible(show);
        nav_Menu.findItem(R.id.nav_myroom).setVisible(show);
        nav_Menu.findItem(R.id.nav_love).setVisible(show);
        nav_Menu.findItem(R.id.nav_warning).setVisible(show);
    }
    private void showItemLogin(boolean show){
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_myroom).setVisible(show);
        nav_Menu.findItem(R.id.nav_love).setVisible(show);
        nav_Menu.findItem(R.id.nav_warning).setVisible(show);
    }






    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void returnMyLocation(String location) {

    }
    @Override
    public void showSnackbar(String message) {
        Util.showSnackbar(frameLayout,message);
    }

    public void setShowAdmin() {
        boolean isAdmin = adminUserPresenter.checkIsAdmin(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(isAdmin){
            showItemAdmin(true);
        }
    }
//    public void showWaiting(int waiting){
//        realtimeBlurView.setVisibility(waiting);
//        pv_saving.setVisibility(waiting);
//    }
}