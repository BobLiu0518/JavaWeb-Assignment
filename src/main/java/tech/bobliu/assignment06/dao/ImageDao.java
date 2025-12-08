package tech.bobliu.assignment06.dao;

public interface ImageDao extends Dao {
    public void initImagesTable();

    public void saveImage(String hash, String mime);

    public String getImageMimeByHash(String hash);
}
