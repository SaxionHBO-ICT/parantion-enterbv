package nl.enterbv.easion.Model;

import java.util.Date;

/**
 * Created by RubenW on 25-5-2016.
 */
public class User {
    private String username, firstname, prefix, lastname, email, outstreamProfile;
    private Date lastLoginDate, registrationDate;


    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String firstname, String prefix, String lastname, String email, String outstreamProfile, Date lastLoginDate, Date registrationDate) {
        this.username = username;
        this.firstname = firstname;
        this.prefix = prefix;
        this.lastname = lastname;
        this.email = email;
        this.outstreamProfile = outstreamProfile;

        this.registrationDate = registrationDate;this.lastLoginDate = lastLoginDate;
}

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getOutstreamProfile() {
        return outstreamProfile;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
