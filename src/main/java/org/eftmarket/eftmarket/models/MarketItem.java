package org.eftmarket.eftmarket.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a market item in the EFT Market.
 * Each item contains details such as category ID, name, description, image link, and base price.
 */
public class MarketItem {

    @SerializedName("bsgCategoryId")
    private String bsgCategoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("baseImageLink")
    private String baseImageLink;

    @SerializedName("basePrice")
    private int basePrice;

    /**
     * Gets the category ID of the item.
     *
     * @return The item's category ID.
     */
    public String getBsgCategoryId() {
        return bsgCategoryId;
    }

    /**
     * Sets the category ID of the item.
     *
     * @param bsgCategoryId The category ID to set.
     */
    public void setBsgCategoryId(String bsgCategoryId) {
        this.bsgCategoryId = bsgCategoryId;
    }

    /**
     * Gets the name of the item.
     *
     * @return The item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the item.
     *
     * @return The item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the image link for the item.
     *
     * @return The item's image link.
     */
    public String getBaseImageLink() {
        return baseImageLink;
    }

    /**
     * Sets the image link for the item.
     *
     * @param baseImageLink The image link to set.
     */
    public void setBaseImageLink(String baseImageLink) {
        this.baseImageLink = baseImageLink;
    }

    /**
     * Gets the base price of the item.
     *
     * @return The item's base price.
     */
    public int getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the base price of the item.
     *
     * @param basePrice The base price to set.
     */
    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }
}