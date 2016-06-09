package nl.enterbv.easion.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class HomeFragment extends Fragment{
    private View myView;
    private TextView naam;
    private AppModel model = AppModel.getInstance();
    private User user = model.getCurrentUser();

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        naam = (TextView) myView.findViewById(R.id.home_tv_gebruiker);

        if (user.getMiddlename() != null){
            updateView();
        }else{
            naam.setText("default name");
        }



        return myView;
    }

    public void updateView(){
        String name = user.getFirstname();
        if (!user.getMiddlename().isEmpty() || !user.getMiddlename().contentEquals(" ")) {
            name += " " + user.getMiddlename();
        }
        name += " " + user.getLastname();

        naam.setText(name);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle("Home");
    }


}
