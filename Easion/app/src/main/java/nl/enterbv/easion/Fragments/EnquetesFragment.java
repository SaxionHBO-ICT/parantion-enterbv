package nl.enterbv.easion.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.io.IOUtils;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.MyFixedTabsPagerAdapter;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class EnquetesFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RetreiveTasks taskRetreiver = new RetreiveTasks();
        taskRetreiver.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_enquetes, container, false);
        viewPager = (ViewPager) myView.findViewById(R.id.view_pager);
        FragmentManager fragmentManager = getFragmentManager();
        PagerAdapter pagerAdapter = new MyFixedTabsPagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) myView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setText("test");
//        tabLayout.getTabAt(1).setText("test");
//        tabLayout.getTabAt(2).setText("test");

        Log.i("testTag", "" + tabLayout.getTabAt(0).getText());
        Log.i("testTag", "" + tabLayout.getTabAt(1).getText());
        Log.i("testTag", "" + tabLayout.getTabAt(2).getText());
        Log.i("testTag", "" + tabLayout.getTabAt(3).getText());

        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Enquetes");
    }

    public class RetreiveTasks extends AsyncTask<Void, Void, Boolean> {
        private String responseString = "";
        AppModel model = AppModel.getInstance();
        User user = model.getCurrentUser();

        public RetreiveTasks() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection httpURLConnection = null;

            String urlString = "https://easion.parantion.nl/api?Action=GetTasks";
            urlString += "&Key=" + model.getAuthentication_SID();
            urlString += "&Uid=" + model.getAuthentication_UID();
            Log.e("testTag10", "url = " + urlString);

            try {
                URL url = new URL(urlString);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setDoOutput(true);

                is = new BufferedInputStream(httpURLConnection.getInputStream());

                String response = IOUtils.toString(is, StandardCharsets.UTF_8);

                if (response.contains("Error") || response.contains("error")) {
                    return false;
                } else {
                    responseString += response;
                }
                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(is);
                httpURLConnection.disconnect();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            String tempString = "";

            if (success) {
                try {
                    final InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(stream);


                    NodeList nodeList = doc.getElementsByTagName("Tasks");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element element = (Element) nodeList.item(i);
                        if (element == null) {
                            Log.e("testTag10", "retreiveTasks: element = null @ first loop");
                        } else {
                            //tempString+= getCharacterDataFromElement(element);

                            NodeList tasksList = element.getChildNodes();

                            for (int z = 0; z < tasksList.getLength(); z++) {
                                Log.e("testTag10", "this node = " + tasksList.item(z).getNodeName() + "with index = " + z);
                                Element e = (Element) tasksList.item(z);

                                NodeList taskNL = e.getChildNodes();

                                for (int q = 0; q < taskNL.getLength(); q++) {
                                    Log.e("testTag10", "arr, " + taskNL.item(q).getNodeName());


                                    //TODO parse xml to Enquete object
                                }


                            }


                        }


                    }


                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }


            }


        }
    }

}
