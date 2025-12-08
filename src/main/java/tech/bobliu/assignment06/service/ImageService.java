package tech.bobliu.assignment06.service;

import tech.bobliu.assignment06.dao.ImageDao;
import tech.bobliu.assignment06.dao.ImageDaoImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;

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

    public String getImageFullPath(String hash) {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            String storagePath = (String) envContext.lookup("imageStoragePath");
            return storagePath + File.separator + hash;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
