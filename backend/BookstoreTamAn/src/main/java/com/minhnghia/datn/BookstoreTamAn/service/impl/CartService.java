package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.AddToCartRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.UpdateCartRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.response.CartItemResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.CartMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Cart;
import com.minhnghia.datn.BookstoreTamAn.model.CartItem;
import com.minhnghia.datn.BookstoreTamAn.model.User;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.CartItemRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.CartRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    public CartItemResponse addToCart(int userId, AddToCartRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart không tồn tại"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Sách không tồn tại"));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book).orElse(null);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }else{
            cartItem = CartItem.builder()
                    .cart(cart)
                    .book(book)
                    .quantity(request.getQuantity())
                    .build();
        }
        cartItemRepository.save(cartItem);
        return cartMapper.toCartItemResponse(cartItem);
    }

    public List<CartItemResponse> viewCart(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart không tồn tại"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream().map(cartItem -> new CartItemResponse(
                cartItem.getId(),
                cartItem.getBook().getId(),
                cartItem.getBook().getTitle(),
                cartItem.getBook().getImageUrl(), // Giả sử Book có field image
                cartItem.getQuantity(),
                cartItem.getBook().getPrice() // Giả sử Book có field price
        )).toList();
    }

    public void updateCartItem(UpdateCartRequest request){
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new RuntimeException("CartItem không tồn tại"));

        if(request.getQuantity() <= 0){
            cartItemRepository.delete(cartItem);
        }else{
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
        }
    }
    public void removeCartItem(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem không tồn tại"));
        cartItemRepository.delete(cartItem);
    }

    public void clearCart(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart không tồn tại"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(cartItems);
    }
    public double calculateTotalPrice(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart không tồn tại"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        return cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
    }
}
