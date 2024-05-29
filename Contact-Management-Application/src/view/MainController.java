package view;

import model.ContactList;

/**
 * Controller for the main view of the application.
 * Manages the contact list.
 * Can be extended if ea3model is extended.
 * 
 * @version 1.00 (20 May 2024)
 * @author Lukas Sinning
 */
public class MainController {

    /** Contact list */
    private ContactList contactList;
    
    /**
     * Constructor for MainController.
     * Initializes the contact list.
     */
    protected MainController() {
        contactList = new ContactList();
    }
    
    /**
     * Returns the contact list.
     * 
     * @return the contact list
     */
    public ContactList getContactList(){
        return this.contactList;
    }
}
