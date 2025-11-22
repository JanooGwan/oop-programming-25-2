package org.example.service;

import org.example.model.IngredientStock;
import org.example.model.NonIngredientStock;
import org.example.model.Stock;
import org.example.repository.InventoryRepository;
import org.example.repository.LogRepository;
import org.example.service.sort.SortStrategy;

import java.time.LocalDate;
import java.util.List;

public class InventoryService {

    // 🔥 1) 싱글톤 인스턴스
    private static final InventoryService instance = new InventoryService();

    // 🔥 2) 생성자를 private 으로 막기
    private InventoryService() {}

    // 🔥 3) 인스턴스 반환 메서드
    public static InventoryService getInstance() {
        return instance;
    }

    private final InventoryRepository repo = InventoryRepository.getInstance();
    private final NotificationService notifier = new NotificationService();

    /** 재고 생성 */
    public void createStock(String name, int qty, int price, LocalDate expiry) {
        int id = repo.getNextId();

        Stock stock;
        if (expiry != null) {
            stock = new IngredientStock(id, name, qty, price, 10, expiry);
        } else {
            stock = new NonIngredientStock(id, name, qty, price, 10);
        }

        repo.save(stock);
        LogRepository.getInstance().add(
                "[재고 추가] " + name + ", 수량=" + qty);
    }

    /** 수동 재고 추가 */
    public void manualAdd(int stockId, int amount) {
        repo.findById(stockId).ifPresent(stock -> {
            stock.increaseStock(amount);
            repo.save(stock);
        });
    }

    /** 유통기한 검사 + 알림 */
    public void auditExpiry() {
        repo.findAll().stream()
                .filter(s -> s instanceof IngredientStock)
                .map(s -> (IngredientStock) s)
                .filter(ing -> ing.daysUntilExpiry() <= 3)
                .forEach(notifier::notifyExpiry);
    }

    /** 재고 임박 검사 */
    public void checkLowStock() {
        repo.findAll().stream()
                .filter(Stock::isLowStock)
                .forEach(notifier::notifyLowStock);
    }

    public void preloadSampleData() {

        if (!repo.findAll().isEmpty()) return; // 이미 데이터 있으면 스킵

        // 식재료
        createStock("양상추", 5, 2000, LocalDate.now().plusDays(2));  // 임박
        createStock("토마토", 20, 1500, LocalDate.now().plusDays(5));
        createStock("베이컨", 8, 3000, LocalDate.now().plusDays(1)); // 매우 임박

        // 비식재료
        createStock("콜라 500ml", 50, 1200, null);
        createStock("펩시제로콜라", 99, 1100, null);
        createStock("빵 포장지", 5, 200, null); // 낮은 재고 = 부족 테스트 가능

        System.out.println("[초기 재고 자동 생성 완료]");
    }


    /** 전체 조회 */
    public List<Stock> getAll() {
        return repo.findAll();
    }

    /** ID로 재고 조회 */
    public Stock getById(int id) {
        return repo.findById(id).orElse(null);
    }

    /** 정렬 전략 패턴 */
    public List<Stock> sort(SortStrategy strategy) {
        return strategy.sort(repo.findAll());
    }

    /** 재고 삭제 */
    public void deleteStock(int stockId) {
        repo.delete(stockId);
        LogRepository.getInstance().add(
                "[재고 삭제] ID=" + stockId);
    }
}
