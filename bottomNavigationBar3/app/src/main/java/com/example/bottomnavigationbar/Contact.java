// done by Yap Xiang Ying and Loh Jun Xiang and Samantha Lok
package com.example.bottomnavigationbar;

public class Contact {
    private String email;
    private String comment;

    // Default constructor required for Firebase Realtime Database
    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue(Contact.class)
    }

    public Contact(String email, String comment) {
        this.email = email;
        this.comment = comment;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for comment
    public String getComment() {
        return comment;
    }

    // Setter for comment
    public void setComment(String comment) {
        this.comment = comment;
    }
}

