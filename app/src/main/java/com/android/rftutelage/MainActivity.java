package com.android.rftutelage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.rftutelage.Activities.ActivitesFragment;
import com.android.rftutelage.Attendance.FragmentAttendance;
import com.android.rftutelage.Calendar.calendar;
import com.android.rftutelage.Profile.profile_main_Activity;
import com.android.rftutelage.Staff.fragment_staff_view;
import com.android.rftutelage.Subjects.FragmentSubjects;
import com.android.rftutelage.Timetable.Fragmenttimetable;
import com.android.rftutelage.ui.CollegeBlog.CollegeBlogDashboard;
import com.android.rftutelage.ui.home.HomeFragment;
import com.android.rftutelage.ui.Meeting.MeetingFragment;
import com.android.rftutelage.utils.PreferenceUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_SELECTED;
import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    static final float END_SCALE = 0.7f;
    public static final String semester = "4";

    ImageView menuIcon;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView navView;
    Boolean ischecked;

    GoogleSignInClient mGoogleSignInClient;

    Handler mHandler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        //Hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_bar_close);
        navigationView = findViewById(R.id.navigation_view);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                        navView.getMenu().setGroupCheckable(0, true, true);
                        navView.setLabelVisibilityMode(LABEL_VISIBILITY_SELECTED);
                        navigationView.setCheckedItem(R.id.menu_none);

                        break;

                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CollegeBlogDashboard()).commit();

                        navView.getMenu().setGroupCheckable(0, true, true);
                        navView.setLabelVisibilityMode(LABEL_VISIBILITY_SELECTED);
                        navigationView.setCheckedItem(R.id.menu_none);

                        break;
                    case R.id.navigation_notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MeetingFragment()).commit();

                        navView.getMenu().setGroupCheckable(0, true, true);
                        navView.setLabelVisibilityMode(LABEL_VISIBILITY_SELECTED);
                        navigationView.setCheckedItem(R.id.menu_none);

                        break;
                }

                return true;
            }
        });


        naviagtionDrawer();


    }

    //Navigation Drawer Functions
    private void naviagtionDrawer() {

        //Naviagtion Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.nav_profile);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

// Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

// Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new profile_main_Activity()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                //navView.setItemTextAppearanceActive();
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;
            case R.id.nav_activities:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ActivitesFragment()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;
            case R.id.nav_timetable:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new Fragmenttimetable()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new calendar()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;

            case R.id.nav_logout:
                signOut();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;

            case R.id.nav_subjects:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FragmentSubjects()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;

            case R.id.nav_staff:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new fragment_staff_view()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;

            case R.id.nav_attendance:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new FragmentAttendance()).commit();
                navView.getMenu().setGroupCheckable(0, false, true);
                navView.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }


    private void signOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        PreferenceUtils.saveEmail("",MainActivity.this);
                        PreferenceUtils.saveRollNumber("",MainActivity.this);
                        startActivity(new Intent(MainActivity.this, LoginActitvity.class));
                    }
                });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
}
