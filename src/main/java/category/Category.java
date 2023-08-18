package category;

/**
 * The Category class represents a category of bikes with various attributes and pricing information.
 */
public class Category {
    private Integer categoryId;
    private String categoryName;
    private Long bikePrice;
    private Double depositRate;
    private Long rentPrice;
    private Double priceMultiple;

    /**
     * Constructs a new Category object with the specified attributes.
     *
     * @param categoryId   The unique identifier for the category.
     * @param categoryName The name of the category.
     * @param bikePrice    The base price of the bikes in this category.
     * @param depositRate  The deposit rate for renting bikes from this category.
     * @param rentPrice    The base rent price for bikes in this category.
     * @param priceMultiple The multiplier for calculating the final price based on the bike's attributes.
     */
    public Category(Integer categoryId, String categoryName, Long bikePrice, Double depositRate,
                    Long rentPrice, Double priceMultiple) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.bikePrice = bikePrice;
        this.depositRate = depositRate;
        this.rentPrice = rentPrice;
        this.priceMultiple = priceMultiple;
    }

    /**
     * Retrieves the unique identifier of the category.
     *
     * @return The category's unique identifier.
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the unique identifier of the category.
     *
     * @param categoryId The new unique identifier to set.
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Retrieves the name of the category.
     *
     * @return The name of the category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName The new name to set for the category.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Retrieves the base price of the bikes in this category.
     *
     * @return The base price of bikes in this category.
     */
    public Long getBikePrice() {
        return bikePrice;
    }

    /**
     * Sets the base price of the bikes in this category.
     *
     * @param bikePrice The new base price for bikes in this category.
     */
    public void setBikePrice(Long bikePrice) {
        this.bikePrice = bikePrice;
    }

    /**
     * Retrieves the deposit rate for renting bikes from this category.
     *
     * @return The deposit rate for bikes in this category.
     */
    public Double getDepositRate() {
        return depositRate;
    }

    /**
     * Sets the deposit rate for renting bikes from this category.
     *
     * @param depositRate The new deposit rate to set for the category.
     */
    public void setDepositRate(Double depositRate) {
        this.depositRate = depositRate;
    }

    /**
     * Retrieves the base rent price for bikes in this category.
     *
     * @return The base rent price for bikes in this category.
     */
    public Long getRentPrice() {
        return rentPrice;
    }

    /**
     * Sets the base rent price for bikes in this category.
     *
     * @param rentPrice The new base rent price to set for the category.
     */
    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    /**
     * Retrieves the price multiplier used to calculate the final price based on bike attributes.
     *
     * @return The price multiplier for this category.
     */
    public Double getPriceMultiple() {
        return priceMultiple;
    }

    /**
     * Sets the price multiplier used to calculate the final price based on bike attributes.
     *
     * @param priceMultiple The new price multiplier to set for the category.
     */
    public void setPriceMultiple(Double priceMultiple) {
        this.priceMultiple = priceMultiple;
    }
}
