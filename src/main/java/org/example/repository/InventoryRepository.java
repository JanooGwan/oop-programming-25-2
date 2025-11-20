package org.example.repository;

import org.example.model.Stock;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InventoryRepository {

    private static InventoryRepository instance;

    private final Map<Integer, Stock> storage = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    private InventoryRepository() {}

    public static InventoryRepository getInstance() {
        if (instance == null) instance = new InventoryRepository();
        return instance;
    }

    /** ID 자동 생성 */
    public int getNextId() {
        return idGenerator.incrementAndGet();
    }

    /** 저장 (추가 or 업데이트) */
    public void save(Stock stock) {
        storage.put(stock.getId(), stock);
    }

    /** 삭제 */
    public void delete(int id) {
        storage.remove(id);
    }

    /** ID로 찾기 */
    public Optional<Stock> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    /** 전체 조회 */
    public List<Stock> findAll() {
        return new ArrayList<>(storage.values());
    }

    /** 이름 검색 */
    public List<Stock> findByNameContains(String keyword) {
        return storage.values().stream()
                .filter(s -> s.getName().contains(keyword))
                .toList();
    }

    public Optional<Stock> findByName(String name) {
        return storage.values().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();
    }

}
