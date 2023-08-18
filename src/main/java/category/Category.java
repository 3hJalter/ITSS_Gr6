package category;

public class Category {
    private Integer categoryId;
    private String categoryName;
    private Long bikePrice;
    private Double depositRate;
    private Long rentPrice;
    private Double priceMultiple;

    public Category(Integer categoryId, String categoryName, Long bikePrice, Double depositRate, Long rentPrice, Double priceMultiple) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.bikePrice = bikePrice;
        this.depositRate = depositRate;
        this.rentPrice = rentPrice;
        this.priceMultiple = priceMultiple;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getBikePrice() {
        return bikePrice;
    }

    public void setBikePrice(Long bikePrice) {
        this.bikePrice = bikePrice;
    }

    public Double getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(Double depositRate) {
        this.depositRate = depositRate;
    }

    public Long getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Double getPriceMultiple() {
        return priceMultiple;
    }

    public void setPriceMultiple(Double priceMultiple) {
        this.priceMultiple = priceMultiple;
    }
}
