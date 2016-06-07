package nl.enterbv.easion.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.enterbv.easion.Fragments.ContactFragment;
import nl.enterbv.easion.Fragments.EnquetesFragment;
import nl.enterbv.easion.Fragments.HomeFragment;
import nl.enterbv.easion.Fragments.InfoFragment;
import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.R;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TestAsynctask testAsynctask = new TestAsynctask();
        testAsynctask.execute();

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

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
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

class TestAsynctask extends AsyncTask<String, Void, String> {

    public static String getCharacterDataFromElement(Element e) {

        NodeList list = e.getChildNodes();
        String data;

        for(int index = 0; index < list.getLength(); index++){
            if(list.item(index) instanceof CharacterData){
                CharacterData child = (CharacterData) list.item(index);
                data = child.getData();

                if(data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return "";
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String uid_value = "";
        String sid_value = "";
        String oid_value = "";


        try {

            final InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(stream);

            NodeList uID_nodes = doc.getElementsByTagName("Uid");
            NodeList sID_nodes = doc.getElementsByTagName("Sid");
            NodeList oID_nodes = doc.getElementsByTagName("Oid");

            for (int i = 0; i < uID_nodes.getLength(); i++){
                Element element = (Element)uID_nodes.item(i);
                if (element == null){
                    Log.e("testTag","element = null");
                }else{
                    Log.e("testTag","UID value  = " + getCharacterDataFromElement(element));
                    uid_value = getCharacterDataFromElement(element);
                }
            }

            for (int i = 0; i < sID_nodes.getLength(); i++){
                Element element = (Element)sID_nodes.item(i);
                if (element == null){
                    Log.e("testTag","element = null");
                }else{
                    Log.e("testTag","SID value  = " + getCharacterDataFromElement(element));
                    sid_value = getCharacterDataFromElement(element);


                }
            }

            for (int i = 0; i < oID_nodes.getLength(); i++){
                Element element = (Element)oID_nodes.item(i);
                if (element == null){
                    Log.e("testTag","element = null");
                }else{
                    Log.e("testTag","OID value  = " + getCharacterDataFromElement(element));
                    oid_value = getCharacterDataFromElement(element);

                }
            }


        } catch (SAXException e) {
            e.printStackTrace();
            Log.e("testTag","SAX Exception" + e.getMessage());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            Log.e("testTag","ParserConfigurationException" + e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }

    @Override
    protected String doInBackground(String... params) {
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;

        try {
            String urlString = "https://easion.parantion.nl/api?Action=Authenticate";
            String user, passwordRaw, passwordHashed;
            user = "saxmoeuse01";
            passwordRaw = "Welkom01";
            passwordHashed = new String(Hex.encodeHex(DigestUtils.md5(passwordRaw)));

            urlString += "&key=" + AppModel.getInstance().getAuthentication_OID();
            urlString += "&Username=" + user;
            urlString += "&Password=" + passwordHashed;


            URL url = new URL(urlString);


            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);


            //int responsecode = httpURLConnection.getResponseCode();

            is = new BufferedInputStream(httpURLConnection.getInputStream());
            String response = IOUtils.toString(is, "UTF-8");
            Log.e("testTag", "response = " + response);

            httpURLConnection.disconnect();
            return response;

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
