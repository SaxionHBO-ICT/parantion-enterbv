package nl.enterbv.easion.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import nl.enterbv.easion.Fragments.ContactFragment;
import nl.enterbv.easion.Fragments.EnquetesFragment;
import nl.enterbv.easion.Fragments.HomeFragment;
import nl.enterbv.easion.Fragments.InfoFragment;
import nl.enterbv.easion.Fragments.SettingsFragment;
import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    public static String finalUsername;
    public static String finalPassword;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recieve intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        finalUsername = intent.getStringExtra("finalUsername");
        finalPassword = intent.getStringExtra("finalPassword");

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View headerView = navigationView.getHeaderView(0);


        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        navigationView.getMenu().getItem(0).setChecked(true);
    }


    /**
     * Makes sure the navigationdrawer functions properly by synchronizing the layout with its drawer.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , new HomeFragment())
                .commit();


    }


    /**
     * Logs the user out
     */
    public void logOut() {
        logOutDialog();

    }

    private void logOutDialog() {

        new AlertDialog.Builder(MainActivity.this, R.style.StyledDialog)
                .setTitle("Uitloggen")
                .setMessage("Weet je zeker dat je wilt uitloggen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("testTag", "user wished to log out.");

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        AppModel.logout();
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("testTag", "user did not wish to log out.");

                    }
                })
                .setIcon(R.drawable.alert)
                .show();

    }

    /**
     * Handles on-click-events in the navigation-drawer-menu.
     *
     * @param item The item that was clicked
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.e("testTag", "id = " + item.getTitle());
        Log.e("testTag", "id = " + item.getItemId());
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        item.setCheckable(true);
        item.setChecked(true);

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_enquetes) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new EnquetesFragment())
                    .commit();
        } else if (id == R.id.nav_info) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new InfoFragment())
                    .commit();
        } else if (id == R.id.nav_contact) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ContactFragment())
                    .commit();
        } else if (id == R.id.nav_settings) {
            Log.e("testTag", "id = nav_settings");

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SettingsFragment())
                    .commit();
        } else if (id == R.id.nav_logout) {

            logOut();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Prevents user from going to the login activity when back button is pressed.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);

            if (f instanceof HomeFragment) {
                Log.e("testTag", "was home fragment");
                logOut();

            }else{
                navigationView.getMenu().getItem(0).setChecked(true);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new HomeFragment())
                        .commit();
            }


            // moveTaskToBack(true);
        }
    }

}
