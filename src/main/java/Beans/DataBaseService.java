package Beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class DataBaseService {
    @PersistenceContext(unitName = "web3PU")
    private EntityManager entityManager;


    public void saveResult(Double x, Double y, Double r, Boolean hit) {
        ResultPoint resultPoint = new ResultPoint(x, y, r, hit);
        entityManager.persist(resultPoint);
        System.out.println("=== SAVED TO DATABASE: " + x + ", " + y + ", " + r + " -> " + hit + " ===");
    }

    public List<ResultPoint> getAllResults() {
        TypedQuery<ResultPoint> query = entityManager.createQuery(
                "SELECT r FROM ResultPoint r ORDER BY r.timestamp DESC", ResultPoint.class);
        return query.getResultList();
    }

    public void clearResults() {
        entityManager.createQuery("DELETE FROM ResultPoint").executeUpdate();
        System.out.println("=== CLEARED ALL RESULTS FROM DATABASE ===");
    }

    public int getResultsCount() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(r) FROM ResultPoint r", Long.class);
        return query.getSingleResult().intValue();
    }

    public List<ResultPoint> getResultsByRadius(Double radius) {
        if (radius == null) {
            return getAllResults();
        }
        TypedQuery<ResultPoint> query = entityManager.createQuery(
                "SELECT r FROM ResultPoint r WHERE r.r = :radius ORDER BY r.timestamp DESC", ResultPoint.class);
        query.setParameter("radius", radius);
        return query.getResultList();
    }
}
