package tech.bobliu.assignment06.service;

import tech.bobliu.assignment06.dao.ImageDao;
import tech.bobliu.assignment06.dao.ImageDaoImpl;

public class ImageService {
    ImageDao imageDao = new ImageDaoImpl();

    public ImageService() {
        imageDao.initImagesTable();
    }

    public void saveImage(String hash, String mime) {
        imageDao.saveImage(hash, mime);
    }

    public String getImageMimeByHash(String hash) {
        return imageDao.getImageMimeByHash(hash);
    }
}
