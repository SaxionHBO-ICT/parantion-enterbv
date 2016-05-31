package nl.enterbv.easion.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nl.enterbv.easion.Fragments.tabs.AllTabFragment;
import nl.enterbv.easion.Fragments.tabs.BezigTabFragment;
import nl.enterbv.easion.Fragments.tabs.KlaarTabFragment;
import nl.enterbv.easion.Fragments.tabs.TodoTabFragment;

/**
 * Created by joepv on 31.mei.2016.
 */

public class MyFixedTabsPagerAdapter extends FragmentStatePagerAdapter {

    public MyFixedTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new AllTabFragment();
            case 1:
                return new TodoTabFragment();
            case 2:
                return new BezigTabFragment();
            case 3:
                return new KlaarTabFragment();
        }


        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 4;
    }
}
