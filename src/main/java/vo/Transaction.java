package vo;

import java.sql.Timestamp;

public class Transaction {
    private Integer transaction_id;
    private Integer provider_product_id;
    private Integer seeker_product_id;
    private Integer status;
    private Timestamp transaction_date;
    private String provider_review;
    private String seeker_review;
    private Integer provider_star;
    private Integer seeker_star;
    private Integer providerUserId;
    private Integer seekerUserId;

    // Getters and Setters
    public Integer getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(Integer providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Integer getSeekerUserId() {
        return seekerUserId;
    }

    public void setSeekerUserId(Integer seekerUserId) {
        this.seekerUserId = seekerUserId;
    }
    
    public Transaction() {
    }

    public Transaction(Integer transaction_id, Integer provider_product_id, Integer seeker_product_id, Integer status,
                          Timestamp transaction_date, String provider_review, String seeker_review,
                          Integer provider_star, Integer seeker_star) {
        this.transaction_id = transaction_id;
        this.provider_product_id = provider_product_id;
        this.seeker_product_id = seeker_product_id;
        this.status = status;
        this.transaction_date = transaction_date;
        this.provider_review = provider_review;
        this.seeker_review = seeker_review;
        this.provider_star = provider_star;
        this.seeker_star = seeker_star;
    }

	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Integer getProvider_product_id() {
		return provider_product_id;
	}

	public void setProvider_product_id(Integer provider_product_id) {
		this.provider_product_id = provider_product_id;
	}

	public Integer getSeeker_product_id() {
		return seeker_product_id;
	}

	public void setSeeker_product_id(Integer seeker_product_id) {
		this.seeker_product_id = seeker_product_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(Timestamp transaction_date) {
		this.transaction_date = transaction_date;
	}

	public String getProvider_review() {
		return provider_review;
	}

	public void setProvider_review(String provider_review) {
		this.provider_review = provider_review;
	}

	public String getSeeker_review() {
		return seeker_review;
	}

	public void setSeeker_review(String seeker_review) {
		this.seeker_review = seeker_review;
	}

	public Integer getProvider_star() {
		return provider_star;
	}

	public void setProvider_star(Integer provider_star) {
		this.provider_star = provider_star;
	}

	public Integer getSeeker_star() {
		return seeker_star;
	}

	public void setSeeker_star(Integer seeker_star) {
		this.seeker_star = seeker_star;
	}
	
	 public boolean isUserInvolved(Integer userId) {
		    return providerUserId != null && providerUserId.equals(userId)
		           || seekerUserId != null && seekerUserId.equals(userId);
		}
}
