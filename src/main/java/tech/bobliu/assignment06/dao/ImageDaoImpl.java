package tech.bobliu.assignment06.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageDaoImpl implements ImageDao {
    public void initImagesTable() {
        try (Connection conn = Dao.getConnection()) {
            conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS images (
                        hash CHAR(32) PRIMARY KEY,
                        mime VARCHAR(50) NOT NULL
                    )
                    """).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String hash, String mime) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    INSERT INTO images (hash, mime) VALUES (?, ?)
                    ON CONFLICT(hash) DO NOTHING
                    """);
            stmt.setString(1, hash);
            stmt.setString(2, mime);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getImageMimeByHash(String hash) {
        try (Connection conn = Dao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                    SELECT mime FROM images WHERE hash = ?
                    """);
            stmt.setString(1, hash);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rs.getString("mime");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
