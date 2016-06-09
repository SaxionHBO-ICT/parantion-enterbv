package nl.enterbv.easion.Model;

/**
 * Created by joepv on 05.jun.2016.
 */
public class AppModel {
    private  User currentUser;
    private final String authentication_OID = "567dc9e3649c476a9e52bc8bf1ca30ea";
    private String authentication_SID;
    private String authentication_UID;
    private static AppModel ourInstance = new AppModel();

    public static AppModel getInstance() {
        return ourInstance;
    }

    private AppModel() {
        currentUser = new User();
    }

    public  User getCurrentUser() {
        return currentUser;
    }


    public String getAuthentication_OID() {
        return authentication_OID;
    }

    public String getAuthentication_SID() {
        return authentication_SID;
    }

    public void setAuthentication_SID(String authentication_SID) {
        this.authentication_SID = authentication_SID;
    }

    public String getAuthentication_UID() {
        return authentication_UID;
    }

    public void setAuthentication_UID(String authentication_UID) {
        this.authentication_UID = authentication_UID;
    }
}
