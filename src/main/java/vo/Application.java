package vo;

import java.sql.Timestamp;

public class Application {
    private Integer application_id;
    private Integer user_id;
    private Integer product_id;
    private Integer tobetraded_product_id;
    private Integer applying_product_id;
    private Timestamp application_date;
    private Integer application_status;

    public Application() {
    }

    public Application(Integer application_id, Integer user_id, Integer product_id, Integer tobetraded_product_id,
                          Integer applying_product_id, Timestamp application_date, Integer application_status) {
        this.application_id = application_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.tobetraded_product_id = tobetraded_product_id;
        this.applying_product_id = applying_product_id;
        this.application_date = application_date;
        this.application_status = application_status;
    }

	public Integer getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Integer application_id) {
		this.application_id = application_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getTobetraded_product_id() {
		return tobetraded_product_id;
	}

	public void setTobetraded_product_id(Integer tobetraded_product_id) {
		this.tobetraded_product_id = tobetraded_product_id;
	}

	public Integer getApplying_product_id() {
		return applying_product_id;
	}

	public void setApplying_product_id(Integer applying_product_id) {
		this.applying_product_id = applying_product_id;
	}

	public Timestamp getApplication_date() {
		return application_date;
	}

	public void setApplication_date(Timestamp application_date) {
		this.application_date = application_date;
	}

	public Integer getApplication_status() {
		return application_status;
	}

	public void setApplication_status(Integer application_status) {
		this.application_status = application_status;
	}
}

