package services;

import entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllOrders() {
        OrderService orderService = new OrderService();
        List<Order> orders;
        assertNotNull(orders = orderService.getAllOrders());
        orders.forEach(System.out::println);
    }
}