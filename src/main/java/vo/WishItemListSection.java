package vo;

import java.sql.Timestamp;
import java.util.List;

public class WishItemListSection {
    private int wishItemId;
    private int wishUserId;
    private String wishItemName;
    private Timestamp createdDate;
    private Timestamp deleteDate;
    private int categoryId;
    private String description;
    private int status;
    private List<Wishitemimage> images;


    // Getter and Setter for wishItemId
    public int getWishItemId() {
        return wishItemId;
    }

    public void setWishItemId(int wishItemId) {
        this.wishItemId = wishItemId;
    }

    // Getter and Setter for wishUserId
    public int getWishUserId() {
        return wishUserId;
    }

    public void setWishUserId(int wishUserId) {
        this.wishUserId = wishUserId;
    }

    // Getter and Setter for wishItemName
    public String getWishItemName() {
        return wishItemName;
    }

    public void setWishItemName(String wishItemName) {
        this.wishItemName = wishItemName;
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

    // Getter and Setter for categoryId
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Getter and Setter for images
    public List<Wishitemimage> getImages() {
        return images;
    }

    public void setImages(List<Wishitemimage> images) {
        this.images = images;
    }

    
}