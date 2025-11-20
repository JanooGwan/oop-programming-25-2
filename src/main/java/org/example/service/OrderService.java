package org.example.service;

import org.example.model.OrderItem;
import org.example.model.OrderStatus;
import org.example.model.Stock;
import org.example.repository.InventoryRepository;
import org.example.repository.OrderRepository;
import org.example.service.command.*;

import java.time.LocalDate;
import java.util.List;

public class OrderService {

    private final OrderRepository repo = OrderRepository.getInstance();
    private final InventoryRepository stockRepo = InventoryRepository.getInstance();

    /** 발주 요청 */
    public OrderItem requestOrder(int stockId, int qty, LocalDate date) {
        int id = repo.nextOrderId();
        // 새 주문은 "REQUESTED" 상태, 배송일 없음
        OrderItem order = new OrderItem(id, stockId, qty, date);
        repo.save(order);
        return order;
    }

    /** 배송 준비 */
    public void prepare(OrderItem order) {
        // 커맨드 패턴: 내부에서 order.markPreparing() 등 호출한다고 가정
        new PrepareCommand().execute(order);
        repo.save(order);
    }

    /** 배송 시작 */
    public void start(OrderItem order) {
        new StartDeliveryCommand().execute(order);
        repo.save(order);
    }

    /** 배송 완료 */
    public void complete(OrderItem order) {
        new CompleteOrderCommand().execute(order);
        repo.save(order);

        // 재고 반영
        stockRepo.findById(order.getStockId()).ifPresent(stock -> {
            stock.increaseStock(order.getQuantity());
            stockRepo.save(stock);
        });
    }

    /** 주문 취소 */
    public void cancel(OrderItem order, String reason) {
        new CancelOrderCommand(reason).execute(order);
        repo.save(order);
    }

    /**
     * 공장 화면에서 직접 상태를 골라 바꾸고 싶을 때 사용하는 메서드
     * (FactoryOrderController.changeState 에서 호출)
     */
    public void updateStatus(OrderItem order, OrderStatus newStatus) {
        order.setStatus(newStatus);

        // "배송중" 으로 바뀌는 순간 배송일 세팅
        if (newStatus == OrderStatus.DELIVERING) {
            order.setDeliveryDate(LocalDate.now());
        }

        repo.save(order);
    }

    public void preloadSampleOrders() {

        if (!repo.findAll().isEmpty()) return;

        requestOrder(1, 10, LocalDate.now().minusDays(1)); // 어제 요청
        requestOrder(2, 5, LocalDate.now());
        requestOrder(3, 30, LocalDate.now());
    }


    public List<OrderItem> getAll() {
        return repo.findAll();
    }

    public List<OrderItem> search(String keyword) {
        return repo.search(keyword);
    }
}
