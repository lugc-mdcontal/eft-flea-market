package org.eftmarket.eftmarket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.eftmarket.eftmarket.models.MarketItem;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the EFT Market application.
 * This class initializes the application, fetches market data, and sets up the main UI.
 */
public class Main extends Application {

    // Main application stage
    public static Stage mainStage;

    // Scene for the market UI
    public static Scene marketScene;

    // Currently selected market item
    public static MarketItem selectedItem;

    // List of market items fetched from the API
    public static List<MarketItem> marketItemList = new ArrayList<>();

    /**
     * Fetches market data from the API.
     *
     * @return JSON response as a string
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    private String fetchMarket() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();

        // GraphQL query to fetch item data
        String query = "{ \"query\": \"{ items(lang: en) { bsgCategoryId, id, name, description, baseImageLink, basePrice } }\" }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.tarkov.dev/graphql"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Loads market items by fetching data from the API and parsing the JSON response.
     * Adds the fetched items to the {@code marketItemList}.
     */
    private void loadMarketItems() {
        try {
            String jsonString = fetchMarket();

            // Parse the JSON response using Gson
            Gson gson = new Gson();
            JsonObject index = gson.fromJson(jsonString, JsonObject.class);
            JsonObject data = index.getAsJsonObject("data");

            // Deserialize JSON array to an array of MarketItem objects
            MarketItem[] marketItems = gson.fromJson(data.getAsJsonArray("items"), MarketItem[].class);

            // Add the items to the market item list
            for (MarketItem marketItem : marketItems) {
                marketItemList.add(marketItem);
            }
        } catch (IOException | InterruptedException e) {
            // Handle exceptions during API fetch or parsing
            e.printStackTrace();
        }
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage Primary stage for the application
     * @throws IOException if there is an error loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the main stage
        mainStage = stage;

        // Show a temporary loading title
        stage.setTitle("Loading...");
        stage.setResizable(false);
        stage.show();

        // Load market items from the API
        loadMarketItems();

        // Load the market.fxml UI
        FXMLLoader marketLoader = new FXMLLoader(Main.class.getResource("market.fxml"));
        marketScene = new Scene(marketLoader.load(), 900, 700);

        // Set the application title and scene
        stage.setTitle("EFT Market");

        // Set the icon for the stage
        stage.getIcons().add(new Image("https://i.redd.it/gn1spzopl0c71.png"));

        // Set the scene for the stage
        stage.setScene(marketScene);
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}