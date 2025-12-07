package tech.bobliu.assignment06.dao;

import tech.bobliu.assignment06.model.Goods;

import java.util.ArrayList;

public interface GoodsDao extends Dao {
    public void initGoodsTable();

    public Goods getGoodsById(int id);

    public int getGoodsCount(String keyword);

    public ArrayList<Goods> queryGoods(String keyword, int limit, int offset);

    public void saveGoods(Goods goods);

    public void deleteGoodsById(int id);
}
