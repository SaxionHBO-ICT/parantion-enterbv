package nl.enterbv.easion.Model;

/**
 * Created by joepv on 05.jun.2016.
 */
public class AppModel {
    private User currentUser;
    private final String authentication_OID = "567dc9e3649c476a9e52bc8bf1ca30ea";

    private static AppModel ourInstance = new AppModel();

    public static AppModel getInstance() {
        return ourInstance;
    }

    private AppModel() {

    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getAuthentication_OID() {
        return authentication_OID;
    }
}
