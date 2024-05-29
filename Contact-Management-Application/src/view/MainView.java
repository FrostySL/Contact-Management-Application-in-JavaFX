package view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Contact;

/**
 * @version 1.00 (20 May 2024)
 * author Lukas Sinning
 */
public class MainView {
    /** Main layout of the view */
    private BorderPane view;
    
    /** Area for contact details */
    private VBox contactDetails;
    
    /** Controller for the main view */
    private MainController controller;
    
    /** Pop-up window for contact management */
    private PopUp popUp;
    
    /** List of contacts */
    private ListView<Contact> listView;
    
    /**
     * Constructor for MainView.
     * Initializes the view and its components.
     */
    public MainView() {
        this.popUp = new PopUp();
        this.controller = new MainController();
        initializeView();
    }
    
    /**
     * Initializes the view and its components.
     */
    private void initializeView() {
        view = new BorderPane();
        
        // Initializes the list of contacts
        listView = new ListView<>(controller.getContactList().getList());
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, o, n) -> showContactDetails(n));
        
        // Creates the buttons for contact management
        Button addBtn = createAddButton();
        Button editBtn = createEditButton();
        Button delBtn = createDeleteButton();
        
        VBox rightSide = new VBox(
                10,
                listView,
                addBtn,
                editBtn,
                delBtn
        );
        contactDetails = new VBox();
        
        rightSide.setPadding(new Insets(10));
        VBox.setVgrow(listView, Priority.ALWAYS);
        view.setCenter(contactDetails);
        view.setRight(rightSide);
                
    }

    /**
     * Creates the button to add a contact.
     * 
     * @return the button
     */
    private Button createAddButton() {
        Button button = new Button("Add Contact");
        button.setOnAction(event -> {
            // Opens the window to add a new contact
            Contact newContact = this.popUp.addContactWindow();
            if (newContact != null) {
                // Adds the new contact to the list and shows the details
                this.controller.getContactList().addContact(newContact);
                showContactDetails(newContact);
            }
            
        });
        return button;
    }
    
    /**
     * Creates the button to delete a contact.
     * 
     * @return the button
     */
    private Button createDeleteButton() {
        Button button = new Button("Delete Contact");
        button.setOnAction(event -> deleteContact());
        return button;
    }
    
    /**
     * Deletes the selected contact from the list.
     */
    private void deleteContact() {
        Contact selectedContact = this.listView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            // Show confirmation dialog
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Delete Contact");
            confirmDialog.setHeaderText("Are you sure you want to delete this contact?");
            if (confirmDialog.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                // Remove the contact from the contact list
                this.controller.getContactList().delContact(
                        this.listView.getSelectionModel().getSelectedIndex()
                );
                // Updates the contact details
                showContactDetails(null);
            }
        } else {
            // Show warning if no contact is selected
            this.popUp.showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Please select a contact to delete."
            );
        }
    }
    
    /**
     * Creates the button to edit a contact.
     * 
     * @return the button
     */
    private Button createEditButton() {
        Button button = new Button("Edit Contact");
        button.setOnAction(event -> {
            // Gets the selected contact
            Contact selectedContact = this.listView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                // Opens the window to edit the contact
                Contact editedContact = this.popUp.editContactWindow(selectedContact);
                if (editedContact != null) {
                    // Updates the contact and shows the details
                    this.controller.getContactList().setContactAtIndex(
                            this.listView.getSelectionModel().getSelectedIndex(),
                            editedContact);
                    showContactDetails(this.listView.getSelectionModel().getSelectedItem());
                }
            } else {
                // Show warning if no contact is selected
                this.popUp.showAlert(
                        Alert.AlertType.WARNING,
                        "No Selection",
                        "Please select a contact to edit."
                );
            }
        });
            
        
        return button;
    }
    /**
     * Shows the details of the selected contact.
     *
     * @param contact the selected contact
     */
    private void showContactDetails(Contact contact) {
        this.contactDetails.getChildren().clear();
        if (contact != null) {
            // Shows the contact details
            Label nameLabel = new Label("First Name: " + contact.getName());
            Label lastNameLabel = new Label("Last Name: " + contact.getLastName());
            Label emailLabel = new Label("Emails: " + String.join(", ", contact.getEmailList()));
            ImageView imageView = new ImageView(contact.getImage());
            imageView.setFitHeight(400);
            imageView.setFitWidth(400);
            this.contactDetails.getChildren().addAll(nameLabel, lastNameLabel, emailLabel, imageView);
        }
    }

    /**
     * Returns the view as a BorderPane.
     * 
     * @return the view
     */
    public BorderPane getView() {
        return this.view;
    }
}
