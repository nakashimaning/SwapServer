package vo;

public class Wishitemimage {
    private Integer wishitemimage_id;
    private Integer wishitem_id;
    private String wishitemimage_url;

    public Wishitemimage() {
    }

    public Wishitemimage(Integer wishitemimage_id, Integer wishitem_id, String wishitemimage_url) {
        this.wishitemimage_id = wishitemimage_id;
        this.wishitem_id = wishitem_id;
        this.wishitemimage_url = wishitemimage_url;
    }

	public Integer getWishitemimage_id() {
		return wishitemimage_id;
	}

	public void setWishitemimage_id(Integer wishitemimage_id) {
		this.wishitemimage_id = wishitemimage_id;
	}

	public Integer getWishitem_id() {
		return wishitem_id;
	}

	public void setWishitem_id(Integer wishitem_id) {
		this.wishitem_id = wishitem_id;
	}

	public String getWishitemimage_url() {
		return wishitemimage_url;
	}

	public void setWishitemimage_url(String wishitemimage_url) {
		this.wishitemimage_url = wishitemimage_url;
	}
}
