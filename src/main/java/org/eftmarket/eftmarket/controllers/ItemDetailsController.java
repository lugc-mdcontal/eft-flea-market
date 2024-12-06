package org.eftmarket.eftmarket.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.eftmarket.eftmarket.Main;

import javax.imageio.ImageIO;

/**
 * Controller for the Item Details view.
 * Displays detailed information about a selected market item, including its name, description, price, and image.
 */
public class ItemDetailsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label itemDescription;

    @FXML
    private ImageView itemLogo;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    /**
     * Closes the item details view.
     * Currently, this method does not implement specific functionality and can be extended to handle window closing.
     *
     * @param event The event triggered when the close button is clicked.
     */
    @FXML
    void onClose(ActionEvent event) {
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Initializes the Item Details view.
     * Populates the view with the details of the currently selected market item from {@link Main#selectedItem}.
     */
    @FXML
    void initialize() {
        // Ensure the selected item exists
        if (Main.selectedItem == null) {
            System.err.println("No item selected for details view.");
            return;
        }

        // Populate item details
        itemName.setText(Main.selectedItem.getName());
        itemDescription.setText(Main.selectedItem.getDescription());
        itemPrice.setText("Price: " + Main.selectedItem.getBasePrice() + " Roubles");

        // Load and display the item's image
        try (InputStream inputStream = new URL(Main.selectedItem.getBaseImageLink()).openStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            itemLogo.setImage(fxImage);
        } catch (IOException e) {
            System.err.println("Error loading image: " + Main.selectedItem.getBaseImageLink());
            e.printStackTrace();
        }
    }
}