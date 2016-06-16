package nl.enterbv.easion.Model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nl.enterbv.easion.R;

/**
 * Created by joepv on 16.jun.2016.
 */

public class CustomViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ListView mListView;

    public CustomViewPagerAdapter(Context context, ListView listView) {
        mContext = context;
        mListView=listView;

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        ViewGroup layout = (ViewGroup) layoutInflater.inflate(R.layout.fragment_all_tab, container, false);
        container.addView(layout);

        return layout;
    }


    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return true;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Alles";
            case 1:
                return "Nieuw";
            case 2:
                return "Bezig";
            case 3:
                return "Klaar";


        }


        return super.getPageTitle(position);

    }
}
