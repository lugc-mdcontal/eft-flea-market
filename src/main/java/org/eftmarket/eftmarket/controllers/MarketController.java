package org.eftmarket.eftmarket.controllers;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.eftmarket.eftmarket.Main;
import org.eftmarket.eftmarket.models.MarketItem;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Controller for the Market view.
 * Handles displaying market items categorized into weapons and ammunition.
 * Enables interaction with items to view their details.
 */
public class MarketController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox ammunitionItemList;

    @FXML
    private HBox weaponItemList;

    /**
     * Adds an item to the specified {@link HBox} container.
     *
     * @param list      The {@link HBox} to which the item will be added.
     * @param itemName  Name of the item.
     * @param itemPrice Price of the item.
     * @param imagePath URL of the item's image.
     */
    private void addItem(HBox list, String itemName, String itemPrice, String imagePath) {
        // Create a container for the item
        VBox container = new VBox();
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.setSpacing(5);
        container.getStyleClass().add("item-container");

        // Create and configure the image view
        ImageView image = new ImageView();
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.getStyleClass().add("image-view");

        // Load the image from the URL
        try (InputStream inputStream = new URL(imagePath).openStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            image.setImage(fxImage);
        } catch (IOException e) {
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }

        // Create the name label
        Label nameLabel = new Label(itemName);
        nameLabel.getStyleClass().add("item-name");

        // Create the price label
        Label priceLabel = new Label("$" + itemPrice);
        priceLabel.getStyleClass().add("item-price");

        // Add components to the container
        container.getChildren().addAll(image, nameLabel, priceLabel);

        // Add click event to open item details
        container.setOnMouseClicked(event -> {
            // Find the selected item by name
            Main.selectedItem = Main.marketItemList.stream()
                    .filter(item -> item.getName().equals(itemName))
                    .findFirst()
                    .orElse(null);

            // Load and display the item details view
            try {
                FXMLLoader itemDetailsLoader = new FXMLLoader(Main.class.getResource("itemDetails.fxml"));
                Scene itemDetailsScene = new Scene(itemDetailsLoader.load(), 400, 400);

                Stage itemDetailsStage = new Stage();
                itemDetailsStage.setScene(itemDetailsScene);
                itemDetailsStage.setTitle("Item Details");
                itemDetailsStage.getIcons().add(new Image("https://i.redd.it/gn1spzopl0c71.png"));
                itemDetailsStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add the container to the item list
        list.getChildren().add(container);
    }

    /**
     * Initializes the market view by populating items into their respective categories.
     */
    @FXML
    void initialize() {
        // Iterate through all market items and add them to appropriate categories
        for (MarketItem marketItem : Main.marketItemList) {
            String itemName = marketItem.getName();
            String itemPrice = String.valueOf(marketItem.getBasePrice());
            String imagePath = marketItem.getBaseImageLink();

            // Categorize items based on their BSG category ID
            if (marketItem.getBsgCategoryId().equals("5447b5f14bdc2d61278b4567")) {
                // Add to weapon item list
                addItem(weaponItemList, itemName, itemPrice, imagePath);
            } else if (marketItem.getBsgCategoryId().equals("543be5cb4bdc2deb348b4568")) {
                // Add to ammunition item list
                addItem(ammunitionItemList, itemName, itemPrice, imagePath);
            }
        }
    }
}