package nl.enterbv.easion.Controller.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.enterbv.easion.Model.AppModel;
import nl.enterbv.easion.Model.User;
import nl.enterbv.easion.R;

import static nl.enterbv.easion.R.id.username;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {
    private Intent intent;
    private AppModel model = AppModel.getInstance();
    private String finalUsername;
    private String finalPassword;
    public static final String PREF_USERNAME = "username";
    public static final String PREF_PASSWORD = "password";


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     **/
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                if (id == R.id.login || id == EditorInfo.IME_ACTION_GO) {

                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUsernameSignInButton = (Button) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Button devLogin = (Button) findViewById(R.id.devButton);
        devLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsernameView.setText("saxmoeuse01");
                mPasswordView.setText("Welkom01");
                attemptLogin();
            }
        });

        Button devLoginJoep = (Button) findViewById(R.id.devLogin_joep);
        devLoginJoep.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsernameView.setText("joep.vander.staaij");
                mPasswordView.setText("T8qapane");
                attemptLogin();
            }
        });


        ProgressBar spinner = (ProgressBar) findViewById(R.id.login_progress);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7FC8EC"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        Button lostPassword = (Button) findViewById(R.id.bttn_lostpassword);

        lostPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://scorion3.parantion.nl/mailpassword/";
                Intent lostPwIntent = new Intent(LoginActivity.this, EnqueteWebViewActivity.class);
                lostPwIntent.putExtra("URL", url);
                startActivity(lostPwIntent);

            }
        });


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            Log.e("testTag1337", "sharedprefs is null");
            mUsernameView.setText(username);
            mPasswordView.setText(password);
            attemptLogin();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }


        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();


        //stores login
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        pref.edit()
                .putString(PREF_USERNAME, username)
                .putString(PREF_PASSWORD, password)
                .commit();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("This field is required");
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            //hides keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            finalUsername = username;
            //finalPassword = MD5.encodeMD5(password);
            finalPassword = new String(Hex.encodeHex(DigestUtils.md5(password)));

            Log.d("username", "" + finalUsername);
            Log.d("password", "" + finalPassword);

            mAuthTask = new UserLoginTask(finalUsername, finalPassword);
            mAuthTask.execute((Void) null);

        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
            focusView.requestFocus();
        }
    }


    public static String getCharacterDataFromElement(Element e) {

        NodeList list = e.getChildNodes();
        String data;

        for (int index = 0; index < list.getLength(); index++) {
            if (list.item(index) instanceof CharacterData) {
                CharacterData child = (CharacterData) list.item(index);
                data = child.getData();

                if (data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return "";
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 3000;
            Log.e("testTag4", "shortAnimTime =" + shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public void addUsernamesToAutoComplete(List<String> usernameAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usernameAddressCollection);

        mUsernameView.setAdapter(adapter);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String[] userFields = {"firstname", "middlename", "lastname", "email", "username", "profile_photo", "studentnummer", "uitstroom", "last_login"};
        private final String mUsername;
        private final String mPassword;
        private String responseString = "";


        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream is;
            HttpURLConnection httpURLConnection;
            String urlString = "https://easion.parantion.nl/api?Action=Authenticate";
            try {
                urlString += "&key=" + AppModel.getInstance().getAuthentication_OID();
                urlString += "&Username=" + mUsername;
                urlString += "&Password=" + mPassword;

                URL url = new URL(urlString);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setDoOutput(true);

                is = new BufferedInputStream(httpURLConnection.getInputStream());
                String response = IOUtils.toString(is, "UTF-8");
                Log.e("testTag", "response = " + response);
                is.close();
                int responseCode = httpURLConnection.getResponseCode();
                Log.e("testTag", " responsecode = " + responseCode);
                httpURLConnection.disconnect();

                if (response.contains("Error") || response.contains("error")) {
                    Log.e("testTag", "response DOES contain error");
                    return false;
                } else {
                    Log.e("testTag", "response does NOT contain error");
                    responseString += response;
                }


                return true;
                //Thread.sleep(2000);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("testTag", "failed to authenticate");
            return false;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //shows popup with text "logging in", text aligned in center of popup
            Snackbar snack = Snackbar.make(mProgressView, "Logging in....", Snackbar.LENGTH_SHORT);
            View view = snack.getView();
            TextView snackTV = (TextView)view.findViewById(android.support.design.R.id.snackbar_text);
            snackTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snack.show();


        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            String uid_value = "";
            String sid_value = "";

            if (success) {
                Log.e("testTag4", " succes");
            } else {
                Log.e("testTag4", "not succes");
            }
            if (success) {
                Log.e("testTag", "Login:onpostexec: response = " + responseString);
                try {

                    final InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(stream);

                    NodeList uID_nodes = doc.getElementsByTagName("Uid");
                    NodeList sID_nodes = doc.getElementsByTagName("Sid");

                    for (int i = 0; i < uID_nodes.getLength(); i++) {
                        Element element = (Element) uID_nodes.item(i);
                        if (element == null) {
                            Log.e("testTag", "element = null");
                        } else {
                            Log.e("testTag", "UID value  = " + getCharacterDataFromElement(element));
                            uid_value = getCharacterDataFromElement(element);
                        }
                    }

                    for (int i = 0; i < sID_nodes.getLength(); i++) {
                        Element element = (Element) sID_nodes.item(i);
                        if (element == null) {
                            Log.e("testTag", "element = null");
                        } else {
                            Log.e("testTag", "SID value  = " + getCharacterDataFromElement(element));
                            sid_value = getCharacterDataFromElement(element);

                        }
                    }

                    LoadProfileTask loadProfileTask;
                    Log.e("testTag", "Logged in succesfully as user '" + mUsername + "'.");

                    model.setAuthentication_SID(sid_value);
                    model.setAuthentication_UID(uid_value);

                    for (String s : userFields) {
                        loadProfileTask = new LoadProfileTask(s);
                        loadProfileTask.execute();
                    }


                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    Log.e("testTag4", "parserconfig exception");
                } catch (SAXException e) {
                    e.printStackTrace();
                    Log.e("testTag4", "SAX  exception");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("testTag4", "IO exception");

                }

//                intent = new Intent(LoginActivity.this, MainActivity.class);
//
//                startActivity(intent);


            } else {
                Log.e("testTag", "fail");
                if (isNetworkAvailable()) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                } else {
                    mPasswordView.setError(getString(R.string.error_no_internet));

                }

                mPasswordView.requestFocus();

            }
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    class LoadProfileTask extends AsyncTask<Void, Void, Boolean> {
        //https://easion.parantion.nl/api?Action=GetUserField   &Key=fbf59197049aa6a9726228394cdca84e   &Uid=1892e6753cdced36020cb494f48b668f   &Var=profile_photo    <-- sid, uid, var
        private String userfield;
        private String responseString = "";
        AppModel model = AppModel.getInstance();
        User user = model.getCurrentUser();

        public LoadProfileTask(String s) {
            userfield = s;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            InputStream is = null;
            Log.e("testTag2", "sid =" + AppModel.getInstance().getAuthentication_SID());
            HttpURLConnection httpURLConnection = null;
            String urlString = "https://easion.parantion.nl/api/?Action=GetUserField";
            urlString += "&Key=" + model.getAuthentication_SID();
            urlString += "&Uid=" + model.getAuthentication_UID();
            urlString += "&Var=" + userfield;

            try {
                URL url = new URL(urlString);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setDoOutput(true);

                is = new BufferedInputStream(httpURLConnection.getInputStream());
                String response = IOUtils.toString(is, StandardCharsets.UTF_8);
                Log.e("testTag2", "userfield:" + userfield + " = " + response);

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
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(is);
                httpURLConnection.disconnect();
            }


            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            String tempString = "";
            if (success) {
                try {

                    final InputStream stream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));

                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document doc = builder.parse(stream);

                    NodeList nodeList = doc.getElementsByTagName("Data");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element e = (Element) nodeList.item(i);
                        if (e == null) {
                            Log.e("testTag2", "getuserdata: element = null @" + userfield);
                        } else {
                            tempString += getCharacterDataFromElement(e);
                            Log.e("testTag2", "getuserdata: element = " + userfield + " was succesfull : " + tempString);

//{"firstname", "middlename", "lastname", "email", "username", "profile_photo", "studentnummer", "uitstroom"};

                            switch (userfield) {
                                case "firstname":
                                    user.setFirstname(tempString);
                                    break;
                                case "middlename":
                                    user.setMiddlename(tempString);
                                    break;
                                case "lastname":
                                    user.setLastname(tempString);
                                    break;
                                case "email":
                                    user.setEmail(tempString);
                                    break;
                                case "username":
                                    user.setUsername(tempString);
                                    break;
                                case "profile_photo":
                                    user.setProfilePhotoString(tempString);
                                    break;
                                case "studentnummer":
                                    if (!tempString.isEmpty())
                                        user.setStudentNummer(Integer.parseInt(tempString));
                                    break;
                                case "uitstroom":
                                    user.setOutstreamProfile(tempString);
                                    Log.e("testTag1337", "outstroom =" + tempString);
                                    break;
                                case "last_login":
                                    user.setLastLoginDate(tempString);
                                    break;
                                default:
                                    Log.e("testTag4", " HALP");
                                    Log.e("testTag4", " considering " + userfield + " = " + tempString);
                                    break;
                            }

                            Log.e("testTag4", "just updated :" + tempString);
                            if (userfield == "uitstroom") {
                                intent = new Intent(LoginActivity.this, MainActivity.class);

                                startActivity(intent);
                                finish();
                                mPasswordView.setError(null);

                            }

                        }

                    }


                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }

    }
}




