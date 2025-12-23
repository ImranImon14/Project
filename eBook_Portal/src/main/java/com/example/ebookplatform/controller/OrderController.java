package com.example.ebookplatform.controller;

import com.example.ebookplatform.model.Book;
import com.example.ebookplatform.model.CartItem;
import com.example.ebookplatform.model.Order;
import com.example.ebookplatform.model.OrderItem;
import com.example.ebookplatform.model.User;
import com.example.ebookplatform.service.CartService;
import com.example.ebookplatform.service.BookService;
import com.example.ebookplatform.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class OrderController {

    private final CartService cartService;
    private final BookService bookService;
    private final OrderService orderService;

    public OrderController(CartService cartService, BookService bookService, OrderService orderService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    public String checkoutPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";

        List<CartItem> cartItems = cartService.getCartItems(user);
        if(cartItems.isEmpty()) {
            model.addAttribute("emptyCart", "Please add items to your cart before checkout.");
            return "user/order";
        }

        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);

        return "user/order";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam String address,
                             @RequestParam String paymentMethod,
                             HttpSession session,
                             Model model) {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/login";

        List<CartItem> cartItems = cartService.getCartItems(user);
        if(cartItems.isEmpty()) {
            model.addAttribute("emptyCart", "No items to order.");
            return "user/order";
        }

        Order order = new Order();
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhone(user.getPhone());
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);

        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setBook(item.getBook());
            oi.setQuantity(item.getQuantity());
            oi.setOrder(order);
            orderItems.add(oi);
        }
        order.setOrderItems(orderItems);

        orderService.placeOrder(order);
        cartService.clearCart(user);

        model.addAttribute("orderSuccess", "✅ Your order has been successfully placed!");
        model.addAttribute("order", order);

        return "user/orderSuccess";
    }
}
