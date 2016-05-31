package nl.enterbv.easion.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.enterbv.easion.Model.MyFixedTabsPagerAdapter;
import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class EnquetesFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

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

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return myView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
