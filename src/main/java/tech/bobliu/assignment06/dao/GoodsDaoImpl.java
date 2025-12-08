package tech.bobliu.assignment06.dao;

import tech.bobliu.assignment06.model.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsDaoImpl implements GoodsDao {
    private static void setGoodsStmt(Goods goods, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, goods.getName());
        stmt.setString(2, goods.getDescription());
        stmt.setBigDecimal(3, goods.getPrice());
        stmt.setBoolean(4, goods.isSold());
        stmt.setInt(5, goods.getPublisherId());
        stmt.setString(6, goods.getImageHash());
    }

    public void initGoodsTable() {
        try (Connection conn = Dao.getConnection()) {
            conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS goods (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        price DECIMAL(10, 2) NOT NULL,
                        is_sold BOOLEAN DEFAULT FALSE,
                        publisher_id INT NOT NULL,
                        image_hash VARCHAR(255) DEFAULT NULL,
                        FOREIGN KEY (publisher_id) REFERENCES users(id),
                        FOREIGN KEY (image_hash) REFERENCES images(hash)
                    )
                    """).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Goods getGoodsById(int id) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT id, name, description, price, is_sold, publisher_id, image_hash FROM goods WHERE id = ?
                    """);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Goods(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getBoolean("is_sold"),
                    rs.getInt("publisher_id"),
                    rs.getString("image_hash")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getGoodsCount(String keyword) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT COUNT(*) AS count FROM goods
                    WHERE name ILIKE ? OR description ILIKE ?
                    """);
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Goods> queryGoods(String keyword, int limit, int offset) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT id, name, description, price, is_sold, publisher_id, image_hash FROM goods
                    WHERE name ILIKE ? OR description ILIKE ?
                    ORDER BY is_sold ASC, id DESC
                    LIMIT ? OFFSET ?
                    """);
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Goods> goodsList = new ArrayList<>();
            while (rs.next()) {
                goodsList.add(new Goods(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_sold"),
                        rs.getInt("publisher_id"),
                        rs.getString("image_hash")
                ));
            }
            return goodsList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGoods(Goods goods) {
        try (Connection conn = Dao.getConnection()) {
            if (goods.getId() == 0) {
                PreparedStatement stmt = conn.prepareStatement("""
                        INSERT INTO goods (name, description, price, is_sold, publisher_id, image_hash)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """);
                setGoodsStmt(goods, stmt);
                stmt.executeUpdate();
            } else {
                PreparedStatement stmt = conn.prepareStatement("""
                        UPDATE goods
                        SET name = ?, description = ?, price = ?, is_sold = ?, publisher_id = ?, image_hash = ?
                        WHERE id = ?
                        """);
                setGoodsStmt(goods, stmt);
                stmt.setInt(7, goods.getId());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGoodsById(int id) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    DELETE FROM goods WHERE id = ?
                    """);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
