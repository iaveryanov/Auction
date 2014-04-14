package com.modernwebcourse.auction.emulator.dao;

import com.modernwebcourse.auction.dao.IProductDao;
import com.modernwebcourse.auction.domain.Product;

import java.util.Optional;

public class ProductDao implements IProductDao {

    public ProductDao() {}

    @Override
    public void insert(Product product) {
        DB.PRODUCTS.put(product.id, product);
        System.out.println("inserted " + product);
    }

    @Override
    public Optional<Product> selectByProductId(int id) {
        return Optional.ofNullable(DB.PRODUCTS.get(id));
    }


}
