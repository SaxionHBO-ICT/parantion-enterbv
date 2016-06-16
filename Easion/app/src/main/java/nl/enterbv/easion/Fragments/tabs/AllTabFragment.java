package nl.enterbv.easion.Fragments.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.TaskListAdapter;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;


public class AllTabFragment extends Fragment {
    View mView;
    ListView allTabListView;
    User user = AppModel.getInstance().getCurrentUser();
    TaskListAdapter adapter;

    public AllTabFragment() {
        // Required empty public constructor
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    public static AllTabFragment newInstance(String param1, String param2) {
        AllTabFragment fragment = new AllTabFragment();
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


        mView = inflater.inflate(R.layout.fragment_all_tab, container, false);
        adapter = new TaskListAdapter(getContext(), user.getEnqueteList());

        allTabListView = (ListView) mView.findViewById(R.id.lv_alltab);
        allTabListView.setAdapter(adapter);


        return mView;
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }



}
