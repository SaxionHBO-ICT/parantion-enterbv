package nl.enterbv.easion.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

/**
 * Created by joepv on 08.jun.2016.
 */

public class SettingsFragment extends Fragment {
    private AppModel model = AppModel.getInstance();
    private User user = AppModel.getInstance().getCurrentUser();

    private View myView;
    private EditText et_Email;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView tv_Username, tv_Firstname, tv_Middlename, tv_Lastname, tv_Lastlogin;

        tv_Username = (TextView) myView.findViewById(R.id.tv_username);
        tv_Username.setText(user.getUsername());

        tv_Firstname = (TextView) myView.findViewById(R.id.tv_firstname);
        tv_Firstname.setText(user.getFirstname());

        tv_Middlename = (TextView) myView.findViewById(R.id.tv_middlename);
        tv_Middlename.setText(user.getMiddlename());

        tv_Lastname = (TextView) myView.findViewById(R.id.tv_lastname);
        tv_Lastname.setText(user.getLastname());

        et_Email = (EditText) myView.findViewById(R.id.et_email);
        et_Email.setText(user.getEmail());


        tv_Lastlogin = (TextView) myView.findViewById(R.id.tv_lastlogin);
        tv_Lastlogin.setText(user.getLastLoginDate());

        LinearLayout focus = (LinearLayout) myView.findViewById(R.id.linearlay_first);
        focus.requestFocus();

        Button saveButton = (Button) myView.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (!et_Email.getText().toString().contentEquals(user.getEmail())) {

                    if (isEmailValid(et_Email.getText().toString())) {
                        Log.e("testTag7", "email is valid");
                        UserFieldChangeTask fieldChangeTask = new UserFieldChangeTask("email", et_Email.getText().toString());
                        fieldChangeTask.execute();
                    } else {
                        Log.e("testTag7", "email invalid");
                        Snackbar.make(myView, "Foutief email-adres", Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("testTag7", "nothing changed in email field");
                    Snackbar.make(myView,"Geen veranderingen in email-veld",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Button undoButton = (Button) myView.findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_Email.getText().toString().contentEquals(user.getEmail()))
                    et_Email.setText(user.getEmail());
            }
        });


        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("testTag", " onviewcreated @ settingsfragment");
        getActivity().setTitle("Profiel");


    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    class UserFieldChangeTask extends AsyncTask<Void, Void, Boolean> {
        final String userfield;
        final String value;
        private String responseString = "";

        public UserFieldChangeTask(String field, String value) {
            userfield = field;
            this.value = value;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection httpURLConnection = null;

            String urlString = "https://easion.parantion.nl/api?Action=SetUserField&Key=";
            urlString += model.getAuthentication_SID();
            urlString += "&Uid=" + model.getAuthentication_UID();
            urlString += "&Var=" + userfield;
            urlString += "&Value=" + value;

            Log.e("testTag7", "settingsfrag:doInBackGround: link = " + urlString);

            try {
                URL url = new URL(urlString);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setDoOutput(true);

                is = new BufferedInputStream(httpURLConnection.getInputStream());
                String response = IOUtils.toString(is, StandardCharsets.UTF_8);

                is.close();
                httpURLConnection.disconnect();

                if (response.contains("Error") || response.contains("error")) {
                    return false;
                } else {
                    responseString += response;
                }

                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Snackbar.make(myView, "Veld succesvol aangepast", Snackbar.LENGTH_SHORT).show();
                user.setEmail(et_Email.getText().toString());
            }


        }
    }
}
