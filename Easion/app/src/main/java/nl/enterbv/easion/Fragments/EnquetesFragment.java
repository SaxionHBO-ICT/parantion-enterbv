package nl.enterbv.easion.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.enterbv.easion.Activities.EnqueteWebViewActivity;
import nl.enterbv.easion.Activities.LoginActivity;
import nl.enterbv.easion.Controller.OnSwipeTouchListener;
import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.Enquete;
import nl.enterbv.easion.Model.TaskListAdapter;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class EnquetesFragment extends Fragment {
    AppModel model = AppModel.getInstance();
    User user = model.getCurrentUser();
    TabLayout tabLayout;
    ListView taskList;
    private int currentTabNr = 0;
    List<Enquete> enqList = new ArrayList<>();
    TaskListAdapter taskListAdapter;
    View mView;
    TextView taskCounter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_enquetes, container, false);
        enqList.clear();
        enqList.addAll(user.getEnqueteList());


        tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Alles"));
        tabLayout.addTab(tabLayout.newTab().setText("Nieuw"));
        tabLayout.addTab(tabLayout.newTab().setText("Bezig"));
        tabLayout.addTab(tabLayout.newTab().setText("Klaar"));
        currentTabNr = tabLayout.getSelectedTabPosition();

        final TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        final TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        final TabLayout.Tab tab2 = tabLayout.getTabAt(2);
        final TabLayout.Tab tab3 = tabLayout.getTabAt(3);
        taskListAdapter = new TaskListAdapter(getContext(), enqList);

        taskList = (ListView) mView.findViewById(R.id.taskLV);
        taskList.setAdapter(taskListAdapter);
        taskList.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if (currentTabNr < 3) {
                    currentTabNr++;
                    switch (currentTabNr) {
                        case 0:
                            tab0.select();
                            break;
                        case 1:
                            tab1.select();
                            break;
                        case 2:
                            tab2.select();
                            break;
                        case 3:
                            tab3.select();
                            break;
                    }
                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if (currentTabNr > 0) {
                    currentTabNr--;

                    switch (currentTabNr) {
                        case 0:
                            tab0.select();
                            break;
                        case 1:
                            tab1.select();
                            break;
                        case 2:
                            tab2.select();
                            break;
                        case 3:
                            tab3.select();
                            break;
                    }

                }
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        currentTabNr = 0;
                        break;
                    case 1:
                        currentTabNr = 1;
                        break;
                    case 2:
                        currentTabNr = 2;
                        break;
                    case 3:
                        currentTabNr = 3;
                        break;
                    default:
                        currentTabNr = 0;
                        break;
                }

                changeTab(currentTabNr);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tab0.select();
        taskCounter = (TextView) mView.findViewById(R.id.tv_enquetes_count);
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                taskCounter.setText("" + enqList.size());
                break;
            case 1:
                taskCounter.setText("" + enqList.size());
                break;
            case 2:
                taskCounter.setText("" + enqList.size());
                break;
            case 3:
                taskCounter.setText("" + enqList.size());
                break;
        }

        setHasOptionsMenu(true);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Enquete e = (Enquete) taskList.getAdapter().getItem(position);

                Intent i = new Intent(getContext(), EnqueteWebViewActivity.class);
                i.putExtra("URL",e.getLink());
                startActivity(i);
            }
        });


        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                refreshData();

                //shows popup with text "Lijsten ophalen...", text aligned in center of popup
                Snackbar snack = Snackbar.make(mView,"Lijsten ophalen...",Snackbar.LENGTH_SHORT);
                View view = snack.getView();
                TextView tv = (TextView)view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snack.show();
                return true;
            case R.id.legenda:
                //shows popup with text "Lijsten ophalen...", text aligned in center of popup
                Snackbar snackbar = Snackbar.make(mView,"groen = nieuw,    oranje = bezig,    rood = klaar",Snackbar.LENGTH_SHORT);
                View v = snackbar.getView();
                TextView textView = (TextView)v.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackbar.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void refreshData() {

        TaskRetreiver taskRetreiver = new TaskRetreiver();
        taskRetreiver.execute();
    }


    private void changeTab(int pos) {
        enqList.clear();
        switch (pos) {
            case 0:
                enqList.addAll(user.getEnqueteList());
                break;
            case 1:
                for (Enquete e : user.getEnqueteList()) {
                    if (e.getProgress() == 0) {
                        enqList.add(e);
                    }
                }
                break;
            case 2:
                for (Enquete e : user.getEnqueteList()) {
                    if (e.getProgress() == 1) {
                        enqList.add(e);
                    }
                }
                break;
            case 3:
                for (Enquete e : user.getEnqueteList()) {
                    if (e.getProgress() == 2) {
                        enqList.add(e);
                    }
                }
                break;
        }

        taskListAdapter.notifyDataSetChanged();
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                taskCounter.setText("" + enqList.size());
                break;
            case 1:
                taskCounter.setText("" + enqList.size());
                break;
            case 2:
                taskCounter.setText("" + enqList.size());
                break;
            case 3:
                taskCounter.setText("" + enqList.size());
                break;
        }
        mView.invalidate();

        Log.e("testTag12", "list size = " + enqList.size());
        Log.e("testTag12", "user list size = " + user.getEnqueteList().size());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Enquetes");


    }


    class TaskRetreiver extends AsyncTask<Void, Void, Boolean> {
        private String responseString = "";
        AppModel model = AppModel.getInstance();
        User user = model.getCurrentUser();

        public TaskRetreiver() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
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
            enqList.clear();
            user.getEnqueteList().clear();

            if (success) {
                try {
                    final InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(stream);

                    NodeList nodeList = doc.getElementsByTagName("Tasks");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element element = (Element) nodeList.item(i);
                        if (element == null) {
                        } else {
                            NodeList tasksList = element.getChildNodes();

                            for (int z = 0; z < tasksList.getLength(); z++) {
                                Element e = (Element) tasksList.item(z);

                                NodeList taskNL = e.getChildNodes();
                                //tempArray not actually used: exists only for clarity. These are the possible fields retrievable from API
                                final String[] tempArray = {"Id", "Date", "Sender", "Label", "Message", "Progress", "Link", "Fid"};

                                Enquete tempEnquete = new Enquete();

                                for (int q = 0; q < taskNL.getLength(); q++) {
                                    Element el = (Element) taskNL.item(q);
                                    String res = LoginActivity.getCharacterDataFromElement(el);
                                    switch (el.getNodeName()) {
                                        case "Id":
                                            tempEnquete.setUnique_ID(Integer.parseInt(res));
                                            break;
                                        case "Date":
                                            tempEnquete.setCreationDate(res);
                                            break;
                                        case "Sender":
                                            tempEnquete.setSender(res);
                                            break;
                                        case "Label":
                                            tempEnquete.setLabel(res);
                                            break;
                                        case "Message":
                                            tempEnquete.setMessage(res);
                                            break;
                                        case "Progress":
                                            tempEnquete.setProgress(Integer.parseInt(res));
                                            break;
                                        case "Link":
                                            tempEnquete.setLink(res);
                                            break;
                                        case "Fid":
                                            tempEnquete.setFid(res);
                                            break;
                                    }
                                }
                                user.addEnquete(tempEnquete);


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
            taskListAdapter.notifyDataSetChanged();
            changeTab(currentTabNr);
            Log.e("testTag12", "refreshed succesfully");
        }
    }

}
