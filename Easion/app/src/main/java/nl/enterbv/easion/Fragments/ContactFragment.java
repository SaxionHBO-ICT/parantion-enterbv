package nl.enterbv.easion.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class ContactFragment extends Fragment {
    private Button emailButton;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_contact, container, false);


        emailButton = (Button) myView.findViewById(R.id.bttn_email);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("message/rfc822");
                emailIntent.setAction(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + "info@parantion.com"));


                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[]{""});
                emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
                emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "");

                startActivity(emailIntent);
            }
        });

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Contact opnemen");
    }
}
