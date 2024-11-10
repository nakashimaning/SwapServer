package vo;

import java.sql.Timestamp;
import java.util.Date;

public class WishList {
	private Integer wishItemId;
	private Integer wishUserId;
	private String wishItemName;
	private Date createdDate;
	private Integer categoryId;
	private String description;
	private Integer wishItemStatus;
	private Timestamp deleteDate;

	public WishList() {
	}

	public WishList(Integer wishItemId, Integer wishUserId, String wishItemName, Date createdDate,
	                Integer categoryId, String description, Integer wishItemStatus, Timestamp deleteDate) {
		this.wishItemId = wishItemId;
		this.wishUserId = wishUserId;
		this.wishItemName = wishItemName;
		this.createdDate = createdDate;
		this.categoryId = categoryId;
		this.description = description;
		this.wishItemStatus = wishItemStatus;
		this.deleteDate = deleteDate;
	}

	public Integer getWishItemId() {
		return wishItemId;
	}

	public void setWishItemId(Integer wishItemId) {
		this.wishItemId = wishItemId;
	}

	public Integer getWishUserId() {
		return wishUserId;
	}

	public void setWishUserId(Integer wishUserId) {
		this.wishUserId = wishUserId;
	}

	public String getWishItemName() {
		return wishItemName;
	}

	public void setWishItemName(String wishItemName) {
		this.wishItemName = wishItemName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWishItemStatus() {
		return wishItemStatus;
	}

	public void setWishItemStatus(Integer wishItemStatus) {
		this.wishItemStatus = wishItemStatus;
	}

	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}
}