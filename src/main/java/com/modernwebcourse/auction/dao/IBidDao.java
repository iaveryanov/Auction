package com.modernwebcourse.auction.dao;

import com.modernwebcourse.auction.domain.Bid;

import java.util.List;
import java.util.Optional;

public interface IBidDao {

    void insert(Bid bid);

    Optional<Bid> selectByBidId(int bidId);

    List<Bid> selectByProductId(int productId);
}
