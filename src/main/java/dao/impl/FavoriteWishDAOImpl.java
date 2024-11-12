package dao.impl;

import dao.FavoriteWishDAO;
import vo.FavoriteWish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import common.ServiceLocator;

public class FavoriteWishDAOImpl implements FavoriteWishDAO {
	private DataSource dataSource;

	public FavoriteWishDAOImpl() throws Exception {


		try {
//			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/swapapp");
			dataSource = ServiceLocator.getInstance().getDataSource();

		} catch (Exception e) {
			throw new Exception("Error initializing DataSource", e);

		}
	}

    @Override
    public boolean isWishItemFavorited(Integer userId, Integer wishItemId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isFavorited = false;

        try {
            conn = dataSource.getConnection();
            String sql = "SELECT 1 FROM Favorite_Wish WHERE user_id = ? AND wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                isFavorited = true;
            }
        } catch (Exception e) {
            throw new Exception("Error checking favorite status", e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }

        return isFavorited;
    }

    @Override
    public void addFavoriteWish(FavoriteWish favoriteWish) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "INSERT INTO Favorite_Wish (user_id, wishitem_id, created_date) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, favoriteWish.getUser_id());
            stmt.setInt(2, favoriteWish.getWishitem_id());
            stmt.setTimestamp(3, favoriteWish.getCreated_date());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error adding to favorite", e);
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    @Override
    public void removeFavoriteWish(Integer userId, Integer wishItemId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = dataSource.getConnection();
            String sql = "DELETE FROM Favorite_Wish WHERE user_id = ? AND wishitem_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, wishItemId);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error removing from favorite", e);
        } finally {
            if (stmt != null) stmt.close();
        }
    }
}