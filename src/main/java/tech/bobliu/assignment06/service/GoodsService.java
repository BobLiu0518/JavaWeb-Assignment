package tech.bobliu.assignment06.service;

import tech.bobliu.assignment06.dao.GoodsDao;
import tech.bobliu.assignment06.dao.GoodsDaoImpl;
import tech.bobliu.assignment06.model.Goods;

import java.math.BigDecimal;
import java.util.ArrayList;

public class GoodsService {
    GoodsDao goodsDao = new GoodsDaoImpl();

    public GoodsService() {
        goodsDao.initGoodsTable();
    }

    public int getGoodsCount(String keyword) {
        return goodsDao.getGoodsCount(keyword);
    }

    public ArrayList<Goods> queryGoodsList(String keyword, int page) {
        return goodsDao.queryGoods(keyword, 8, (page - 1) * 8);
    }

    public Goods getGoodsById(int id) {
        return goodsDao.getGoodsById(id);
    }

    public void deleteGoodsById(int id) {
        goodsDao.deleteGoodsById(id);
    }

    public Goods addGoods(String name, String description, BigDecimal price, int publisherId, String imageHash) {
        Goods goods = new Goods(0, name, description, price, false, publisherId, imageHash);
        goodsDao.saveGoods(goods);
        return goods;
    }

    public Goods modifyGoods(Goods goods) {
        goodsDao.saveGoods(goods);
        return goods;
    }
}
