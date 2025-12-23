package com.example.ebookplatform.repository;

import com.example.ebookplatform.model.CartItem;
import com.example.ebookplatform.model.User;
import com.example.ebookplatform.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUserAndBook(User user, Book book);
}
