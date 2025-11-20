package org.example.repository;

import org.example.model.OrderItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRepository {

    private static OrderRepository instance;

    private final Map<Integer, OrderItem> storage = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    private OrderRepository() {}

    public static OrderRepository getInstance() {
        if (instance == null) instance = new OrderRepository();
        return instance;
    }

    /** 새로운 발주 ID 생성 */
    public int nextOrderId() {
        return idGenerator.incrementAndGet();
    }

    /** 저장 또는 갱신 */
    public void save(OrderItem item) {
        storage.put(item.getOrderId(), item);
    }

    /** ID로 찾기 */
    public Optional<OrderItem> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    /** 전체 조회 */
    public List<OrderItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    /** 키워드 검색 */
    public List<OrderItem> search(String keyword) {
        String key = keyword.toLowerCase();

        return storage.values().stream()
                .filter(o ->
                        String.valueOf(o.getOrderId()).contains(key) ||
                                String.valueOf(o.getStockId()).contains(key) ||
                                o.getStatus().name().toLowerCase().contains(key)
                )
                .toList();
    }
}
