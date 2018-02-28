package com.app.whiff.whiff.UI.HomePage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;

import com.app.whiff.whiff.ARPSpoofer.UI.ARPSpoofer;
import com.app.whiff.whiff.NonRootScanner.FileManager;
import com.app.whiff.whiff.R;
import com.app.whiff.whiff.RootScanner.UI.RootScanner;
import com.app.whiff.whiff.NonRootScanner.UI.NonRootScanner;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomePageViewInterface {

    // Buttons to launch other activities
    public Button RootScannerButton;
    public Button NonRootScannerButton;
    public Button TransportProtocolButton;
    public Button ImportButton;
    public Button ARPSpooferButton;
    public Button HelpFaqButton;

    // Reference to presenter
    public HomePagePresenterInterface presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectWithPresenter(); // RootScannerPresenter object

        Context context = getApplicationContext();

        RootScannerButton = (Button) findViewById(R.id.RootScannerButton);
        RootScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.RootScannerButtonClicked();
                Intent RootScannerActivity = new Intent(view.getContext(), RootScanner.class);
                startActivity(RootScannerActivity);
            }
        });

        NonRootScannerButton = (Button) findViewById(R.id.NonRootScannerButton);
        NonRootScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.NonRootScannerButtonClicked();
                // Intent NonRootScannerActivity = new Intent(view.getContext(), NonRootScanner.class);
                Intent NonRootScannerActivity = new Intent(view.getContext(), com.app.whiff.whiff.NonRootScanner.UI.NonRootScanner.class);
                startActivity(NonRootScannerActivity);
            }
        });

        TransportProtocolButton = (Button) findViewById(R.id.NonRootScannerTransportButton);
        TransportProtocolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TransportProtocolActivity = new Intent(view.getContext(), com.app.whiff.whiff.UI.PacketDb.PacketDbPage.class);
                startActivity(TransportProtocolActivity);
            }
        });

        ImportButton = (Button) findViewById(R.id.ImportButton);
        ImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ImportIntent = new Intent (view.getContext(), com.app.whiff.whiff.UI.ImportPacketFile.ImportPacketFilePage.class);
                startActivity(ImportIntent);
            }
        });

        HelpFaqButton = (Button) findViewById(R.id.HelpButton);
        HelpFaqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://whiffuow.wixsite.com/home"));
                startActivity(browserIntent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showMessage(String message) {
    }

    public void connectWithPresenter() {
        // presenter = new RootScannerPresenter(this, handler);
        presenter = new HomePagePresenter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.filter_settings) {
            return true;
        }

        if (id == R.id.export_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_root_packet_capture) {
            Intent RootScannerActivity = new Intent(this, RootScanner.class);
            startActivity(RootScannerActivity);

        } else if (id == R.id.nav_non_root_packet_capture) {
            Intent NonRootScannerActivity = new Intent(this, com.app.whiff.whiff.NonRootScanner.UI.NonRootScanner.class);
            startActivity(NonRootScannerActivity);

        } else if (id == R.id.nav_non_root_sniffer_transport) {
            Intent NonRootTransportActivity = new Intent(this, com.app.whiff.whiff.UI.PacketDb.PacketDbPage.class);
            startActivity(NonRootTransportActivity);

        } else if (id == R.id.nav_Import_File) {
            Intent ImportActivity = new Intent (this, com.app.whiff.whiff.UI.ImportPacketFile.ImportPacketFilePage.class);
            startActivity(ImportActivity);

        } else if (id == R.id.nav_help_faq) {
            // Intent helpActivity = new Intent (this, com.app.whiff.whiff.help.class);
            // startActivity(helpActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
