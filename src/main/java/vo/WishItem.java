package vo;

import java.sql.Timestamp;
import java.util.List;



public class WishItem {
    private Integer wishItemId;
    private Integer wishUserId;
    private Integer wishCategoryId;
    private Timestamp wishCreatedDate;
    private String wishItemTitle;
    private Integer wishStatus;
    private String wishDescription;
    private List<String> imageList;        // Added for images
    private List<Register> registerUsers;   // Added for registers

    // Default constructor
    public WishItem() {
    }

    // Constructor with basic fields
    public WishItem(Integer wishItemId, Integer wishUserId, Integer wishCategoryId, 
                   Timestamp wishCreatedDate, String wishItemTitle, Integer wishStatus, 
                   String wishDescription) {
        this.wishItemId = wishItemId;
        this.wishUserId = wishUserId;
        this.wishCategoryId = wishCategoryId;
        this.wishCreatedDate = wishCreatedDate;
        this.wishItemTitle = wishItemTitle;
        this.wishStatus = wishStatus;
        this.wishDescription = wishDescription;
    }

    // Constructor with all fields including lists
    public WishItem(Integer wishItemId, Integer wishUserId, Integer wishCategoryId, 
                   Timestamp wishCreatedDate, String wishItemTitle, Integer wishStatus, 
                   String wishDescription, List<String> imageList, List<Register> registerUsers) {
        this.wishItemId = wishItemId;
        this.wishUserId = wishUserId;
        this.wishCategoryId = wishCategoryId;
        this.wishCreatedDate = wishCreatedDate;
        this.wishItemTitle = wishItemTitle;
        this.wishStatus = wishStatus;
        this.wishDescription = wishDescription;
        this.imageList = imageList;
        this.registerUsers = registerUsers;
    }

    // Getters and Setters
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

    public Integer getWishCategoryId() {
        return wishCategoryId;
    }

    public void setWishCategoryId(Integer wishCategoryId) {
        this.wishCategoryId = wishCategoryId;
    }

    public Timestamp getWishCreatedDate() {
        return wishCreatedDate;
    }

    public void setWishCreatedDate(Timestamp wishCreatedDate) {
        this.wishCreatedDate = wishCreatedDate;
    }

    public String getWishItemTitle() {
        return wishItemTitle;
    }

    public void setWishItemTitle(String wishItemTitle) {
        this.wishItemTitle = wishItemTitle;
    }

    public Integer getWishStatus() {
        return wishStatus;
    }

    public void setWishStatus(Integer wishStatus) {
        this.wishStatus = wishStatus;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<Register> getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(List<Register> registerUsers) {
        this.registerUsers = registerUsers;
    }
}


//
//public class WishItem {
//	private Integer wishItemId;
//	private Integer wishUserId;
//	private Integer wishCategoryId;
//	private Timestamp wishCreatedDate;
//	private Timestamp wishDeleteDate;
//	private String wishItemTitle;
//	private Integer wishStatus;
//	private String wishDescription;
//	private List<String> wishImageList;
//	private List<User> registerList;
//    private User publisher; // Publisher info
//
//	public WishItem() {
//	}
//
//	public WishItem(Integer wishItemId, Integer wishUserId, Integer wishCategoryId, Timestamp wishCreatedDate,
//			Timestamp wishDeleteDate, String wishItemTitle, Integer wishStatus, String wishDescription,
//			List<String> wishImageList, List<User> registerList) {
//		this.wishItemId = wishItemId;
//		this.wishUserId = wishUserId;
//		this.wishCategoryId = wishCategoryId;
//		this.wishCreatedDate = wishCreatedDate;
//		this.wishDeleteDate = wishDeleteDate;
//		this.wishItemTitle = wishItemTitle;
//		this.wishStatus = wishStatus;
//		this.wishDescription = wishDescription;
//		this.wishImageList = wishImageList;
//		this.registerList = registerList;
//	}
//
//    public User getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(User publisher) {
//        this.publisher = publisher;
//    }
//    
//	public Integer getWishItemId() {
//		return wishItemId;
//	}
//
//	public void setWishItemId(Integer wishItemId) {
//		this.wishItemId = wishItemId;
//	}
//
//	public Integer getWishUserId() {
//		return wishUserId;
//	}
//
//	public void setWishUserId(Integer wishUserId) {
//		this.wishUserId = wishUserId;
//	}
//
//	public Integer getWishCategoryId() {
//		return wishCategoryId;
//	}
//
//	public void setWishCategoryId(Integer wishCategoryId) {
//		this.wishCategoryId = wishCategoryId;
//	}
//
//	public Timestamp getWishCreatedDate() {
//		return wishCreatedDate;
//	}
//
//	public void setWishCreatedDate(Timestamp wishCreatedDate) {
//		this.wishCreatedDate = wishCreatedDate;
//	}
//
//	public Timestamp getWishDeleteDate() {
//		return wishDeleteDate;
//	}
//
//	public void setWishDeleteDate(Timestamp wishDeleteDate) {
//		this.wishDeleteDate = wishDeleteDate;
//	}
//
//	public String getWishItemTitle() {
//		return wishItemTitle;
//	}
//
//	public void setWishItemTitle(String wishItemTitle) {
//		this.wishItemTitle = wishItemTitle;
//	}
//
//	public Integer getWishStatus() {
//		return wishStatus;
//	}
//
//	public void setWishStatus(Integer wishStatus) {
//		this.wishStatus = wishStatus;
//	}
//
//	public String getWishDescription() {
//		return wishDescription;
//	}
//
//	public void setWishDescription(String wishDescription) {
//		this.wishDescription = wishDescription;
//	}
//
//	public List<String> getWishImageList() {
//		return wishImageList;
//	}
//
//	public void setWishImageList(List<String> wishImageList) {
//		this.wishImageList = wishImageList;
//	}
//
//	public List<User> getRegisterList() {
//		return registerList;
//	}
//
//	public void setRegisterList(List<User> registrantList) {
//		this.registerList = registrantList;
//	}
//
//}
