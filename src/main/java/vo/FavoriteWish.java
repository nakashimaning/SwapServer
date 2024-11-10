package vo;

import java.sql.Timestamp;

public class FavoriteWish {
    private Integer user_id;
    private Integer wishitem_id;
    private Timestamp created_date;

    public FavoriteWish() {
    }

    public FavoriteWish(Integer user_id, Integer wishitem_id, Timestamp created_date) {
        this.user_id = user_id;
        this.wishitem_id = wishitem_id;
        this.created_date = created_date;
    }

    // Getters and Setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getWishitem_id() {
        return wishitem_id;
    }

    public void setWishitem_id(Integer wishitem_id) {
        this.wishitem_id = wishitem_id;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }


}