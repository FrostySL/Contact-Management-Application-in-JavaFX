package view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;

/**
 * Represents a pop-up window for managing contacts.
 * Provides functions for adding and editing contacts.
 * 
 * @version 1.00 (20 May 2024)
 * author Lukas Sinning
 */
public class PopUp {
    
    /** New contact being added */
    private Contact newContact;
    
    /** Contact being edited */
    private Contact editedContact;
    
    /**
     * Opens a window to add a new contact.
     * 
     * @return the added contact or null if the operation was cancelled
     */
    public Contact addContactWindow() {
        
        Stage window = new Stage();
        
        // Blocks interaction with other windows while this one is open
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Contact");
        
        // Text fields for first name and last name
        TextField textName = new TextField();
        TextField textLastName = new TextField();
        
        // Box for multiple email fields
        VBox emailBox = new VBox(5);
        Button btnAddEmail = new Button("Add Email");
        btnAddEmail.setOnAction(e -> {
            // Adds a new text field for an email address
            emailBox.getChildren().add(new TextField());
        });
        
        Button btnRemoveEmail = new Button("Remove Email");
        btnRemoveEmail.setOnAction(e -> {
            // Removes the last email text field if any
            if (emailBox.getChildren().size() > 0) {
                emailBox.getChildren().remove(emailBox.getChildren().size() - 1); 
            }
        });

        // Image selection
        FileChooser fileChooser = new FileChooser();
        Button btnChooseImage = new Button("Choose Image");
        ImageView imageView = new ImageView();
        btnChooseImage.setOnAction(e -> {
            // Opens a file dialog for image selection
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                // Loads and displays the selected image
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
            }
        });
        Button btnDelImage = new Button("Delete Image");
        btnDelImage.setOnAction(e -> {
            // Removes the currently displayed image
            imageView.setImage(null);
        });

        Button addButton = new Button("Add Contact");
        addButton.setOnAction(event -> {
            // Checks if the first name and last name fields are filled
            if (textName.getText().isEmpty() || textLastName.getText().isEmpty()) {
                this.newContact = null;
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill out all fields.");
                return;
            }
            // Creates a new contact with the entered values
            this.newContact = new Contact(textName.getText(), textLastName.getText());
            boolean validEmails = true;
            // Checks and adds each email from the emailBox
            for (Node node : emailBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField emailField = (TextField) node;
                    /*
                     * Adds the email and checks its validity
                     * Regular expression: "^[\\w]{1,64}@[\\w]{1,64}\\.[\\w]{1,64}$"
                     */
                    if (!this.newContact.addEmail(emailField.getText())) {
                        validEmails = false;
                    }
                }
            }
            // Sets the selected image for the contact
            this.newContact.setImage(imageView.getImage());
            // Shows an error if an invalid email was entered
            if (!validEmails) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter valid email addresses.");
                this.newContact = null;
                return;
            }
            // Closes the window after successfully adding the contact
            window.close();
        });
 
        // Layout of the pop-up window
        VBox layout = new VBox(10,
                createHBox("Enter First Name:", textName),
                createHBox("Enter Last Name:", textLastName),
                new Label("Emails:"),
                emailBox,
                new HBox(10, btnAddEmail, btnRemoveEmail),
                new HBox(10, btnChooseImage, imageView, btnDelImage),
                addButton
        );
        
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        window.setScene(new Scene(layout, 300, 400));
        window.showAndWait();
        return this.newContact;

    }
    
    /**
     * Opens a window to edit an existing contact.
     * 
     * @param contact the contact to be edited
     * @return the edited contact or null if the operation was cancelled
     */
    public Contact editContactWindow(Contact contact) {
        Stage window = new Stage();
        
        // Blocks interaction with other windows while this one is open
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Contact");
        
        // Text fields for first name and last name 
        TextField textName = new TextField(contact.getName());
        TextField textLastName = new TextField(contact.getLastName());
        
        // VBox for multiple email fields
        VBox emailBox = new VBox(5);
        // Adds the current email addresses of the contact
        for(String email : contact.getEmailList()) {
            emailBox.getChildren().add(new TextField(email));
        }
        Button btnAddEmail = new Button("Add Email");
        btnAddEmail.setOnAction(e -> {
            // Adds a new text field for an email address
            emailBox.getChildren().add(new TextField());
        });
        
        Button btnRemoveEmail = new Button("Remove Email");
        btnRemoveEmail.setOnAction(e -> {
            // Removes the last email text field if any
            if (emailBox.getChildren().size() > 0) {
                emailBox.getChildren().remove(emailBox.getChildren().size() - 1); 
            }
        });

        // Image selection
        FileChooser fileChooser = new FileChooser();
        Button btnChooseImage = new Button("Choose Image");
        ImageView imageView = new ImageView();
        // Sets the current image of the contact
        imageView.setImage(contact.getImage());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        btnChooseImage.setOnAction(e -> {
            // Opens a file dialog for image selection
            File file = fileChooser.showOpenDialog(window);
            if (file != null) {
                // Loads and displays the selected image
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
            }
        });
        Button btnDelImage = new Button("Delete Image");
        btnDelImage.setOnAction(e -> {
            // Removes the currently displayed image
            imageView.setImage(null);
        });

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            // Checks if the first name and last name fields are filled
            if (textName.getText().isEmpty() || textLastName.getText().isEmpty()) {
                this.editedContact = null;
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill out all fields.");
                return;
            }
            // Creates a new contact with the entered values
            this.editedContact = new Contact(textName.getText(), textLastName.getText());
            boolean validEmails = true;
            // Checks and adds each email from the emailBox
            for (Node node : emailBox.getChildren()) {
                if (node instanceof TextField) {
                    TextField emailField = (TextField) node;
                    /*
                     * Adds the email and checks its validity
                     * Regular expression: "^[\\w]{1,64}@[\\w]{1,64}\\.[\\w]{1,64}$"
                     */
                    if (!this.editedContact.addEmail(emailField.getText())) {
                        validEmails = false;
                    }
                }
            }
            // Sets the selected image for the contact
            this.editedContact.setImage(imageView.getImage());
            // Shows an error if an invalid email was entered
            if (!validEmails) {
                showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter valid email addresses.");
                this.editedContact = null;
                return;
            }
            // Closes the window after successfully saving the contact
            window.close();
        });
        
        // Layout of the pop-up window
        VBox layout = new VBox(10,
                createHBox("Enter First Name:", textName),
                createHBox("Enter Last Name:", textLastName),
                new Label("Emails:"),
                emailBox,
                new HBox(10, btnAddEmail, btnRemoveEmail),
                new HBox(10, btnChooseImage, imageView, btnDelImage),
                saveButton
        );
        
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        window.setScene(new Scene(layout, 300, 400));
        window.showAndWait();
        return this.editedContact;
    }
    
    /**
     * Creates an HBox layout with a label and a text field.
     * 
     * @param labelText the text for the label
     * @param textField the text field
     * @return the created HBox layout
     */
    private HBox createHBox(String labelText, TextField textField) {
        Label label = new Label(labelText);
        HBox hBox = new HBox(10, label, textField);
        hBox.setPadding(new Insets(10));
        return hBox;
    }
    
    /**
     * Shows an alert dialog.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param content the content of the alert
     */
    protected void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(content);
        alert.showAndWait();
    }
}
