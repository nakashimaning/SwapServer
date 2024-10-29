package vo;

import java.sql.Timestamp;

public class Wishitem {
    private Integer wishitem_id;
    private Integer wish_user_id;
    private String wishitem_name;
    private Timestamp created_date;
    public Integer getWishitem_id() {
		return wishitem_id;
	}

	public void setWishitem_id(Integer wishitem_id) {
		this.wishitem_id = wishitem_id;
	}

	public Integer getWish_user_id() {
		return wish_user_id;
	}

	public void setWish_user_id(Integer wish_user_id) {
		this.wish_user_id = wish_user_id;
	}

	public String getWishitem_name() {
		return wishitem_name;
	}

	public void setWishitem_name(String wishitem_name) {
		this.wishitem_name = wishitem_name;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Integer category_id;
    private String description;

    public Wishitem() {
    }

    public Wishitem(Integer wishitem_id, Integer wish_user_id, String wishitem_name, Timestamp created_date,
                       Integer category_id, String description) {
        this.wishitem_id = wishitem_id;
        this.wish_user_id = wish_user_id;
        this.wishitem_name = wishitem_name;
        this.created_date = created_date;
        this.category_id = category_id;
        this.description = description;
    }
}

