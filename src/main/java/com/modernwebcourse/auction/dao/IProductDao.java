package com.modernwebcourse.auction.dao;


import com.modernwebcourse.auction.domain.Product;

import java.util.Optional;

public interface IProductDao {

    void insert(Product product);

    Optional<Product> selectByProductId(int id);
}
