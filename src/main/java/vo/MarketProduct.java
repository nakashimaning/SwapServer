package vo;

import java.sql.Timestamp;
import java.util.List;

public class MarketProduct {
	private Integer productId;
	private Integer userId;
	private String userName;
	private Integer categoryId;
	private Timestamp createdDate;
	private Timestamp deleteDate;
	private String title;
	private Integer status;
	private String description;
	private List<String> imageList;
	private boolean isFavorite;
	private boolean isApplicationed;
    private String providerName; // 會員名稱
    private String providerPhotoUrl; // 新增的會員照片 URL 欄位

	public MarketProduct() {
	}

	public MarketProduct(Integer productId, Integer userId, Integer categoryId, Timestamp createdDate, Timestamp deleteDate,
			String title, Integer status, String description, List<String> imageList, boolean isFavorite, boolean isApplicationed) {
		this.productId = productId;
		this.userId = userId;
		this.categoryId = categoryId;
		this.createdDate = createdDate;
		this.deleteDate = deleteDate;
		this.title = title;
		this.status = status;
		this.description = description;
		this.imageList = imageList;
		this.isFavorite = isFavorite;
		this.isApplicationed = isApplicationed;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public boolean isFavorite() {
	    return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
	    this.isFavorite = isFavorite;
	}

	public boolean isApplicationed() {
	    return isApplicationed;
	}

	public void setApplicationed(boolean isApplicationed) {
	    this.isApplicationed = isApplicationed;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderPhotoUrl() {
		return providerPhotoUrl;
	}

	public void setProviderPhotoUrl(String providerPhotoUrl) {
		this.providerPhotoUrl = providerPhotoUrl;
	}
	
}
