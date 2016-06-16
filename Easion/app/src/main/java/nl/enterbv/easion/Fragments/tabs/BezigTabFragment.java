package nl.enterbv.easion.Fragments.tabs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.Enquete;
import nl.enterbv.easion.Model.TaskListAdapter;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;


public class BezigTabFragment extends Fragment {
    View mView;
    ListView busyTabListView;
    User user = AppModel.getInstance().getCurrentUser();
    TaskListAdapter adapter;

    public BezigTabFragment() {
        // Required empty public constructor
    }


    public static BezigTabFragment newInstance(String param1, String param2) {
        BezigTabFragment fragment = new BezigTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_busy_tab, container, false);
        List<Enquete> busyList = new ArrayList();
        for (Enquete e : user.getEnqueteList()) {

            if (e.getProgress() == 1) {
                busyList.add(e);
            }
        }

        adapter = new TaskListAdapter(getContext(), busyList);

        busyTabListView = (ListView) mView.findViewById(R.id.lv_busytab);
        busyTabListView.setAdapter(adapter);


        return mView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.notifyDataSetChanged();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
