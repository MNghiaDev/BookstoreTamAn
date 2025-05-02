package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequest;
import com.minhnghia.datn.BookstoreTamAn.dto.request.OrderRequestItem;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderResponse;
import com.minhnghia.datn.BookstoreTamAn.mapper.OrderMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderDetailRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final EmailService emailService;

    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest request) {


        Order order = orderMapper.toOrder(request);

        Order savedOrder = orderRepository.save(order);

        double total = 0.0;
        for (OrderRequestItem item : request.getItems()) {
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new RuntimeException("Sách không tồn tại"));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setBook(book);
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(book.getPrice());
            orderDetailRepository.save(detail);

            total += book.getPrice() * item.getQuantity();
        }

        savedOrder.setTotalPrice(total);
        orderRepository.save(savedOrder);
        emailService.sendOrderConfirmation(
                request.getEmail(),
                "Xác nhận đơn hàng - Tâm An Store",
                "<h3>Chào " + request.getRecipientName() + ",</h3>"
                        + "<p>Cảm ơn bạn đã đặt hàng tại <b>Nhà sách Tâm An</b>.</p>"
                        + "<p>Đơn hàng của bạn đang được xử lý.</p>"
        );
        return new OrderResponse(savedOrder.getId(), savedOrder.getTotalPrice(), savedOrder.getStatus());
    }
}
