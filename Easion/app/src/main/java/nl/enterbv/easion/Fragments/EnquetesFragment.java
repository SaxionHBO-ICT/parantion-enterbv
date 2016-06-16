package nl.enterbv.easion.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        tabLayout.addTab(tabLayout.newTab().setText("TodO"));
        tabLayout.addTab(tabLayout.newTab().setText("Busy"));
        tabLayout.addTab(tabLayout.newTab().setText("Done"));
        currentTabNr = tabLayout.getSelectedTabPosition();

        final TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        final TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        final TabLayout.Tab tab2 = tabLayout.getTabAt(2);
        final TabLayout.Tab tab3 = tabLayout.getTabAt(3);
        taskListAdapter = new TaskListAdapter(getContext(), enqList);

        taskList = (ListView) mView.findViewById(R.id.taskLV);
        taskList.setAdapter(taskListAdapter);
        Log.e("testTag12", taskList.getAdapter().getCount() + "");
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
        return mView;
    }

    private void changeTab(int pos) {
        enqList.clear();
        Toast.makeText(getContext(), "tab gewisseld :D", Toast.LENGTH_SHORT).show();
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
        mView.invalidate();

        Log.e("testTag12", "list size = " + enqList.size());
        Log.e("testTag12", "user list size = " + user.getEnqueteList().size());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Enquetes");


    }


}
