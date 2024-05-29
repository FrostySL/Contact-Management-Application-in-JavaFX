package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a list of contacts.
 * Provides methods to add, remove, and retrieve contacts.
 *
 * @version 1.00 (20 May 2024)
 * author Lukas Sinning
 */
public class ContactList {

    /** List of contacts. */
    private ObservableList<Contact> list;

    /**
     * Constructor for ContactList.
     * Initializes the list of contacts.
     */
    public ContactList() {
        this.list = FXCollections.observableArrayList();
    }

    /**
     * Adds a new contact to the list.
     * 
     * @param name the first name of the contact
     * @param lastName the last name of the contact
     */
    public void addContact(String name, String lastName) {
        this.list.add(new Contact(name, lastName));
    }

    /**
     * Adds an existing contact to the list.
     * 
     * @param contact the contact to be added
     */
    public void addContact(Contact contact) {
        this.list.add(contact);
    }

    /**
     * Deletes a contact from the list by index.
     * 
     * @param index the index of the contact to be deleted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void delContact(int index) {
        if (index >= 0 && index < this.list.size()) {
            this.list.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.list.size());
        }
    }

    /**
     * Retrieves a contact from the list by index.
     * 
     * @param index the index of the contact to be retrieved
     * @return the contact at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Contact getContact(int index) {
        if (index >= 0 && index < this.list.size()) {
            return this.list.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.list.size());
        }
    }

    /**
     * Sets a contact at the specified index to a new contact.
     * 
     * @param index the index of the contact to be set
     * @param contact the contact to replace the existing contact
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setContactAtIndex(int index, Contact contact) {
        if (index >= 0 && index < this.list.size()) {
            this.list.set(index, contact);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.list.size());
        }
    }
    
    /**
     * Returns the list of contacts.
     * 
     * @return the list of contacts
     */
    public ObservableList<Contact> getList() {
        return this.list;
    }
}
