package nl.enterbv.easion.Model;

import java.util.Date;

/**
 * Created by RubenW on 25-5-2016.
 */
public class Enquete {
    private int unique_ID;
    private Date creationDate;
    private String sender, label, message, link;
    private int progress, studentnumber;

    public Enquete(int unique_ID, Date creationDate, String sender, String label, String message, String link, int progress, int studentnumber) {
        this.unique_ID = unique_ID;
        this.creationDate = creationDate;
        this.sender = sender;
        this.label = label;
        this.message = message;
        this.link = link;
        this.progress = progress;
        this.studentnumber = studentnumber;
    }

    public int getUnique_ID() {
        return unique_ID;
    }

    public Date getCreationDate() {
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

    public int getStudentnumber() {
        return studentnumber;
    }
}
