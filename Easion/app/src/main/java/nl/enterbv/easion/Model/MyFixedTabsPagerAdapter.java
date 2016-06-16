package nl.enterbv.easion.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import nl.enterbv.easion.Fragments.tabs.AllTabFragment;
import nl.enterbv.easion.Fragments.tabs.BezigTabFragment;
import nl.enterbv.easion.Fragments.tabs.KlaarTabFragment;
import nl.enterbv.easion.Fragments.tabs.TodoTabFragment;

/**
 * Created by joepv on 31.mei.2016.
 */

public class MyFixedTabsPagerAdapter extends FragmentStatePagerAdapter {


    public MyFixedTabsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alles";
            case 1:
                return "Te doen";
            case 2:
                return "Bezig";
            case 3:
                return "Klaar";
            default:
                return "error";

        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
