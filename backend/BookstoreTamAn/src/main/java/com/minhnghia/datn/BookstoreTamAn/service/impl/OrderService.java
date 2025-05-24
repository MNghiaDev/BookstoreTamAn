package com.minhnghia.datn.BookstoreTamAn.service.impl;

import com.minhnghia.datn.BookstoreTamAn.dto.request.*;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderCreationResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderDetailResponse;
import com.minhnghia.datn.BookstoreTamAn.dto.response.OrderResponse;
import com.minhnghia.datn.BookstoreTamAn.exception.AppException;
import com.minhnghia.datn.BookstoreTamAn.exception.ErrorCode;
import com.minhnghia.datn.BookstoreTamAn.mapper.OrderDetailMapper;
import com.minhnghia.datn.BookstoreTamAn.mapper.OrderMapper;
import com.minhnghia.datn.BookstoreTamAn.model.Book;
import com.minhnghia.datn.BookstoreTamAn.model.Order;
import com.minhnghia.datn.BookstoreTamAn.model.OrderDetail;
import com.minhnghia.datn.BookstoreTamAn.repository.BookRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderDetailRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.OrderRepository;
import com.minhnghia.datn.BookstoreTamAn.repository.UserRepository;
import com.minhnghia.datn.BookstoreTamAn.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final EmailService emailService;
    private final OrderDetailMapper orderDetailMapper;

    private final OrderMapper orderMapper;

    @Override
    public OrderCreationResponse createOrder(OrderRequest request) {


        Order order = orderMapper.toOrder(request);
        log.info(order.getOrderCode());
        Order savedOrder = orderRepository.save(order);
        System.out.println("orderCode : " + order.getOrderCode());

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
            book.setStock(book.getStock() - detail.getQuantity());

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

        return new OrderCreationResponse(savedOrder.getId(), savedOrder.getTotalPrice(), savedOrder.getStatus());
    }



    @Override
    public Page<OrderResponse> getAll(PageRequest request) {
        return orderRepository.findAll(request).map(orderMapper::toOrderResponse);
    }

    private Order findId(Integer id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public OrderResponse updateStatus(Integer id, OrderUpdateStatusRequest request) {
        Order order = findId(id);
        order.setStatus(request.getStatus());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public Void delete(Integer id) {
        Order order = findId(id);
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(order.getId());
        if (orderDetails.isEmpty()) {
            throw new RuntimeException("Không tìm thấy chi tiết đơn hàng");
        }
        orderDetailRepository.deleteAll(orderDetails);
        orderRepository.delete(order);
        return null;
    }
    public List<PurchaseTrendDTO> getPurchaseTrend(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Object[]> result = orderRepository.calculatePurchaseTrends(start, end);

        return result.stream()
                .map(obj -> new PurchaseTrendDTO(
                        (LocalDate) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }
    public Order savePendingOrder(OrderRequest request) {
        Order order = orderMapper.toOrder(request);
        order.setStatus("PENDING"); // trạng thái tạm thời
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
        return orderRepository.save(savedOrder);
    }
    public Page<OrderResponse> getByStatus(String status, PageRequest request) {
        return orderRepository.findByStatus(status, request)
                .map(orderMapper::toOrderResponse);
    }
    public OrderResponse updateActive(Integer id, ActiveRequest request){
        Order order = findId(id);
        order.setActive(request.getActive());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    public Page<OrderResponse> getByUserId(Integer userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable).map(orderMapper::toOrderResponse);
    }

    public List<OrderDetailResponse> getAllByOrderId(Integer orderId) {
        return orderDetailRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderDetailMapper::toOrderDetailResponse)
                .toList();
    }

    public OrderResponse confirmReceived(Integer orderId){
        Order order = findId(orderId);
        order.setStatus("delivered");
        order.setPaid(true);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    public OrderResponse cancelOrder(Integer orderId){
        Order order = findId(orderId);
        if(order.getStatus().equals("pending")){
            order.setStatus("canceled");
            orderRepository.save(order);
        }
        return orderMapper.toOrderResponse(order);
    }
}
