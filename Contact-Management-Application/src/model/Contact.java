package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Represents a contact with first name, last name, image, and email addresses.
 * 
 * @version 1.0 (May 23 2024)
 * author Lukas Sinning
 */
public class Contact {
    
    /** First name of the contact */
    private String name;
    
    /** Last name of the contact */
    private String lastName;
    
    /** Image of the contact */
    private Image image;
    
    /** List of email addresses */
    private ObservableList<String> emailList;
    
    /**
     * Constructor for Contact.
     * Initializes the contact with first name and last name.
     * 
     * @param name the first name of the contact
     * @param lastName the last name of the contact
     */
    public Contact(String name, String lastName) {
        this(name, lastName, null, null);
    }
    
    /**
     * Constructor for Contact.
     * 
     * @param name the first name of the contact
     * @param lastName the last name of the contact
     * @param email the email of the contact
     */
    public Contact(String name, String lastName, String email) {
        this(name, lastName, email, null);
    }
    
    /**
     * Constructor for Contact.
     * 
     * @param name the first name of the contact
     * @param lastName the last name of the contact
     * @param email the email of the contact
     * @param image the image of the contact
     */
    public Contact(String name, String lastName, String email, Image image) {
        this.name = name;
        this.lastName = lastName;
        this.emailList = FXCollections.observableArrayList();
        this.addEmail(email);
        this.image = image;
    }
    
    /**
     * Sets the contact details.
     * 
     * @param contact the contact to be set
     */
    public void setContact(Contact contact) {
        this.name = contact.name;
        this.lastName = contact.lastName;
        this.emailList = contact.emailList;
        this.image = contact.image;
    }
    
    /**
     * Sets the first name of the contact.
     * 
     * @param name the first name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the last name of the contact.
     * 
     * @param lastName the last name to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Sets the image of the contact.
     * 
     * @param image the image to be set
     */
    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * Adds an email to the contact's email list.
     * 
     * @param email the email to be added
     * @return true if the email is valid and added, false otherwise
     */
    public boolean addEmail(String email) {
        if (isValidEmail(email)) {
            emailList.add(email);
            return true;
        }
        return false;
    }
    
    /**
     * Removes an email from the contact's email list by index.
     * 
     * @param index the index of the email to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void removeEmail(int index) {
        if (index >= 0 && index < emailList.size()) {
            emailList.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + emailList.size());
        }
    }
    
    /**
     * Retrieves the first name of the contact.
     * 
     * @return the first name of the contact
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Retrieves the last name of the contact.
     * 
     * @return the last name of the contact
     */
    public String getLastName() {
        return this.lastName;
    }
    
    /**
     * Retrieves the image of the contact.
     * 
     * @return the image of the contact
     */
    public Image getImage() {
        return this.image;
    }
    
    /**
     * Retrieves an email from the contact's email list by index.
     * 
     * @param index the index of the email to be retrieved
     * @return the email at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String getEmail(int index) {
        if (index < 0 || index >= emailList.size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + emailList.size());
        }
        return emailList.get(index);
    }
    
    /**
     * Returns the list of email addresses.
     * 
     * @return the list of email addresses
     */
    public ObservableList<String> getEmailList() {
        return this.emailList;
    }
    
    /**
     * Checks the validity of an email address.
     * 
     * @param email the email to be checked
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            String regex = "^[\\w]{1,64}@[\\w]{1,64}\\.[\\w]{1,64}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return email.length() >= 3
                    && email.length() <= 256
                    && matcher.matches();
        }
    }
    
    /**
     * Returns a string representation of the contact.
     * 
     * @return a string representation of the contact
     */
    @Override
    public String toString() {
        return name + " " + lastName;
    }
}
