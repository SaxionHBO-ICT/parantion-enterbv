package nl.enterbv.easion.Model;

import java.util.Date;

/**
 * Created by RubenW on 25-5-2016.
 */
public class Enquete {
    private int unique_ID;
    private String sender, label, message, link, fid, creationDate;
    private int progress;

    public Enquete(int unique_ID, String creationDate, String sender, String label, String message, String link, int progress, int studentnumber, String fid) {
        this.unique_ID = unique_ID;
        this.creationDate = creationDate;
        this.sender = sender;
        this.label = label;
        this.message = message;
        this.link = link;
        this.progress = progress;
        this.fid = fid;
    }


    public void setUnique_ID(int unique_ID) {
        this.unique_ID = unique_ID;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Enquete() {

    }

    public String getFid() {
        return fid;
    }

    public int getUnique_ID() {
        return unique_ID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getSender() {
        return sender;
    }

    public String getLabel() {
        return label;
    }

    public String getMessage() {
        return message;
    }

    public String getLink() {
        return link;
    }

    public int getProgress() {
        return progress;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
