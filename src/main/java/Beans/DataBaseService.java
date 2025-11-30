package Beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataBaseService {
    private List<ResultPoint> results = new CopyOnWriteArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);


    public void saveResult(Double x, Double y, Double r, Boolean hit) {
        ResultPoint resultPoint = new ResultPoint(x, y, r, hit);
        resultPoint.setId(idCounter.getAndIncrement());
        results.add(0, resultPoint);
        System.out.println("=== SAVED TO MEMORY: " + x + ", " + y + ", " + r + " -> " + hit + " ===");
        System.out.println("=== TOTAL RESULTS: " + results.size() + " ===");
    }

    public List<ResultPoint> getAllResults() {
        return new ArrayList<>(results);
    }

    public void clearResults() {
        results.clear();
        idCounter.set(1);
        System.out.println("=== CLEARED ALL RESULTS ===");
    }

    public int getResultsCount() {
        return results.size();
    }

    public List<ResultPoint> getResultsByRadius(Double radius) {
        if (radius == null) {
            return getAllResults();
        }
        return results.stream()
                .filter(result -> radius.equals(result.getR()))
                .collect(Collectors.toList());
    }
}
