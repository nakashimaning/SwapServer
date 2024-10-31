package vo;

import java.sql.Timestamp;
import java.util.List;

public class ApplicationProduct {
	private Integer applicationProductId;
	private Integer userId;
	private String userAvatar;
	private Integer categoryId;
	private Timestamp createdDate;
	private Timestamp deleteDate;
	private String title;
	private Integer status;
	private String description;
	private List<String> imageList;

	public ApplicationProduct() {
	}
	
	public ApplicationProduct(Integer applicationProductId, Integer userId, String userAvatar, Integer categoryId,
			Timestamp createdDate, Timestamp deleteDate, String title, Integer status, String description,
			List<String> imageList) {
		super();
		this.applicationProductId = applicationProductId;
		this.userId = userId;
		this.userAvatar = userAvatar;
		this.categoryId = categoryId;
		this.createdDate = createdDate;
		this.deleteDate = deleteDate;
		this.title = title;
		this.status = status;
		this.description = description;
		this.imageList = imageList;
	}

	public Integer getApplicationProductId() {
		return applicationProductId;
	}

	public void setApplicationProductId(Integer applicationProductId) {
		this.applicationProductId = applicationProductId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

}
