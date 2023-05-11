package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // 지연로딩된 실제 객체를 가져옴 = Lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
        // 무한루프
        // 지연로딩
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // ORDER 2개
        // N + 1 문제 -> 1 + 회원 N(2개) + 배송 N(2개)
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;
    }

    // v1과 v2 는 Lazy 로딩으로 인한 너무 많은 쿼리를 요청
    // fetch join 사용
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName(); //LAZY 초기화
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getDelivery().getAddress(); //LAZY 초기화
        }
    }

}
