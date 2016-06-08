package nl.enterbv.easion.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.enterbv.easion.R;

/**
 * Created by joepv on 08.jun.2016.
 */

public class SettingsFragment extends Fragment {
    View myView;
    public SettingsFragment() {
        Log.e("testTag", "settingsfragment constructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_settings, container, false);



        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("testTag", " onviewcreated @ settingsfragment");
        getActivity().setTitle("Profiel");

    }
}
