package vo;

public class Productimage {
    private Integer productimage_id;
    private Integer product_id;
    private String productimage_url;

    public Productimage() {
    }

    public Productimage(Integer productimage_id, Integer product_id, String productimage_url) {
        this.productimage_id = productimage_id;
        this.product_id = product_id;
        this.productimage_url = productimage_url;
    }

	public Integer getProductimage_id() {
		return productimage_id;
	}

	public void setProductimage_id(Integer productimage_id) {
		this.productimage_id = productimage_id;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getProductimage_url() {
		return productimage_url;
	}

	public void setProductimage_url(String productimage_url) {
		this.productimage_url = productimage_url;
	}
}
