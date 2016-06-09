package nl.enterbv.easion.Model;

import java.util.Date;
import java.util.Observable;

import nl.enterbv.easion.Fragments.HomeFragment;

/**
 * Created by RubenW on 25-5-2016.
 */
public class User{
    private String username, firstname, middlename, lastname, email, outstreamProfile, profilePhotoString;
    private Date lastLoginDate, registrationDate;
    private int studentNummer;


    public int getStudentNummer() {
        return studentNummer;
    }

    public void setStudentNummer(int studentNummer) {
        this.studentNummer = studentNummer;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getProfilePhotoString() {
        return profilePhotoString;
    }

    public void setProfilePhotoString(String profilePhotoString) {
        this.profilePhotoString = profilePhotoString;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setOutstreamProfile(String outstreamProfile) {
        this.outstreamProfile = outstreamProfile;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
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
