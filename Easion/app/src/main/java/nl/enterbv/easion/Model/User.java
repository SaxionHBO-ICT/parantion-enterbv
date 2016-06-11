package nl.enterbv.easion.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RubenW on 25-5-2016.
 */
public class User {
    private String username, firstname, middlename, lastname, email, outstreamProfile, profilePhotoString, lastLoginDate;
    private Date registrationDate;
    private int studentNummer;
    private List<Enquete> enqueteList;

    public User() {
        enqueteList = new ArrayList<>();
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void addEnquete(Enquete e) {
        enqueteList.add(e);
    }

    public List<Enquete> getEnqueteList() {
        return enqueteList;
    }

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


    public Date getRegistrationDate() {
        return registrationDate;
    }


}
