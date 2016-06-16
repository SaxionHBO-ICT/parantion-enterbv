package nl.enterbv.easion.Fragments.tabs;

import android.content.Context;
import android.os.Bundle;
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


public class TodoTabFragment extends Fragment {
    View mView;
    ListView toDoTabListView;
    User user = AppModel.getInstance().getCurrentUser();
    TaskListAdapter adapter;

    public TodoTabFragment() {
        // Required empty public constructor
    }


    public static TodoTabFragment newInstance(String param1, String param2) {
        TodoTabFragment fragment = new TodoTabFragment();
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
        mView = inflater.inflate(R.layout.fragment_todo_tab, container, false);
        List<Enquete> toDoList = new ArrayList();
        for (Enquete e : user.getEnqueteList()) {

            if (e.getProgress() == 0) {
                toDoList.add(e);
            }
        }

        adapter = new TaskListAdapter(getContext(), toDoList);

        toDoTabListView = (ListView) mView.findViewById(R.id.lv_todotab);
        toDoTabListView.setAdapter(adapter);


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


}
