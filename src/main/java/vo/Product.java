package vo;

import java.sql.Timestamp;

public class Product {
    private Integer product_id;
    private Integer user_id;
    private Integer category_id;
    private Timestamp created_date;
    public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Timestamp getDelete_date() {
		return delete_date;
	}

	public void setDelete_date(Timestamp delete_date) {
		this.delete_date = delete_date;
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

	private Timestamp delete_date;
    private String title;
    private Integer status;
    private String description;

    public Product() {
    }

    public Product(Integer product_id, Integer user_id, Integer category_id, Timestamp created_date,
                      Timestamp delete_date, String title, Integer status, String description) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.created_date = created_date;
        this.delete_date = delete_date;
        this.title = title;
        this.status = status;
        this.description = description;
    }
}
