package nl.enterbv.easion.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class InfoFragment extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_info, container, false);


        ImageView iv_icon = (ImageView)myView.findViewById(R.id.frag_info_icon_iv);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.parantion.com/"));
                startActivity(browserIntent);
            }
        });


        return myView;
    }
}
