package vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class ProductListSection {
    private int productId;
    private int userId;
    private int categoryId;
    private Timestamp createdDate;
    private Timestamp deleteDate;
    private String title;
    private int status;
    private String description;
    private List<Productimage> images;

    // Getter and Setter for productId
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter and Setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter for categoryId
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Getter and Setter for createdDate
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp timestamp) {
        this.createdDate = timestamp;
    }

    // Getter and Setter for deleteDate
    public Timestamp getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Timestamp timestamp) {
        this.deleteDate = timestamp;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for images
    public List<Productimage> getImages() {
        return images;
    }

    public void setImages(List<Productimage> images) {
        this.images = images;
    }
}