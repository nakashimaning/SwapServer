package vo;

public class WishItemRegister {
    private Integer registerId;       // 登記ID
    private Integer wishitemId;       // 心願商品ID
    private Integer registrantUserId; // 登記使用者ID

    // Getters 和 Setters
    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public Integer getWishitemId() {
        return wishitemId;
    }

    public void setWishitemId(Integer wishitemId) {
        this.wishitemId = wishitemId;
    }

    public Integer getRegistrantUserId() {
        return registrantUserId;
    }

    public void setRegistrantUserId(Integer registrantUserId) {
        this.registrantUserId = registrantUserId;
    }
}