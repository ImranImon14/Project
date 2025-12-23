package com.example.ebookplatform.service;

import com.example.ebookplatform.model.Book;
import com.example.ebookplatform.model.CartItem;
import com.example.ebookplatform.model.User;
import com.example.ebookplatform.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(User user, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(User user, Book book) {
        cartRepository.deleteByUserAndBook(user, book);
    }

    public void clearCart(User user) {
        List<CartItem> items = cartRepository.findByUser(user);
        cartRepository.deleteAll(items);
    }
}
