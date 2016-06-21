package nl.enterbv.easion.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.Enquete;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

/**
 * Created by user on 12/31/15.
 */
public class HomeFragment extends Fragment {
    private View mView;
    private TextView naam;
    private AppModel model = AppModel.getInstance();
    private User user = model.getCurrentUser();
    private int taskCounter = 0;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_v2, container, false);
        naam = (TextView) mView.findViewById(R.id.hometwo_tv_name);

        //checks whether user has a middlename
        if (user.getMiddlename() != null) {
            updateView();
        } else {
            naam.setText("default name");
        }

        //Checks if user has a profile image set and sets the imageview as such when that is the case.
        if (!user.getProfilePhotoString().isEmpty()) {
            ImageView profileImage = (ImageView) mView.findViewById(R.id.hometwo_iv_profile);
            StringBuilder profileImageUrl = new StringBuilder();
            profileImageUrl.append("https://easion.parantion.nl/");
            profileImageUrl.append(user.getProfilePhotoString());
            profileImageUrl.append("&uid=");
            profileImageUrl.append(model.getAuthentication_UID());

            Picasso.with(getContext())
                    .load(profileImageUrl.toString())
                    .into(profileImage);


        }

        final TextView amountOfTasks = (TextView) mView.findViewById(R.id.hometwo_tv_taskcounter);
        final Handler handler = new Handler();


        //Keeps checking if enquete-list has been retreived. When it has, stop checking.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (Enquete e : user.getEnqueteList()) {
                    if (e.getProgress() == 0 || e.getProgress() == 1) {
                        taskCounter++;
                    }
                }
                if (taskCounter == 1) {
                    amountOfTasks.setText("Je hebt " + taskCounter + " open taak op je wachten.");
                } else {
                    amountOfTasks.setText("Je hebt " + taskCounter + " open taken op je wachten.");
                }
                Log.e("testTag1337", "ping");
                handler.postDelayed(this, 1000);
                if (taskCounter > 0) {
                    handler.removeCallbacksAndMessages(null);
                }


            }
        }, 1000);


        /*
            If user has these profile variables, show them. Otherwise, make these views invissible.
         */
        TextView uitstroomProfiel = (TextView) mView.findViewById(R.id.hometwo_tv_uitstroomprofiel);
        if (!Strings.isNullOrEmpty(user.getOutstreamProfile())) {
            uitstroomProfiel.setText(user.getOutstreamProfile());
        } else {
            TextView uitstroomProfielLabel = (TextView) mView.findViewById(R.id.hometwo_tv_uitstroomlabel);
            uitstroomProfielLabel.setVisibility(View.INVISIBLE);
            uitstroomProfiel.setVisibility(View.INVISIBLE);
        }
        TextView studentNummer = (TextView) mView.findViewById(R.id.hometwo_tv_studentnummer);
        TextView studentNummerLabel = (TextView) mView.findViewById(R.id.hometwo_tv_studentnummerlabel);

        TextView username = (TextView) mView.findViewById(R.id.hometwo_tv_gebruikersnaam);
        username.setText(user.getUsername());

        TextView email = (TextView) mView.findViewById(R.id.hometwo_tv_emailaddress);
        email.setText(user.getEmail());


        if (user.getStudentNummer() != 0) {
            studentNummer.setText(user.getStudentNummer() + "");
        } else {
            studentNummer.setVisibility(View.INVISIBLE);
            studentNummerLabel.setVisibility(View.INVISIBLE);
        }


        return mView;
    }

    public void updateView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user.getFirstname() + " ");

        if (!Strings.isNullOrEmpty(user.getMiddlename())) {
            stringBuilder.append(user.getMiddlename() + " ");
        }

        stringBuilder.append(user.getLastname());
        naam.setText(stringBuilder.toString());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home");
    }


}
