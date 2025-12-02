package Beans;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@ApplicationScoped
public class PointBean implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private boolean fromGraph = false;
    private Double graphX;
    private Double graphY;
    boolean lastHitResult;
    @Inject DataBaseService service;


    public String clear() {
        this.x = null;
        this.y = null;
        this.r = null;
        this.graphX = null;
        this.graphY = null;
        this.fromGraph = false;
        return null;
    }

    public String clearResults() {
        service.clearResults();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Успех", "Все результаты очищены"));
        return null;
    }

    public String checkHit() {
        boolean hasErrors = false;
        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("=== DEBUG CHECKHIT ===");
        System.out.println("fromGraph: " + fromGraph);

        if (fromGraph) {
            this.x = graphX;
            this.y = graphY;
            System.out.println("Using graph coordinates - X: " + graphX + ", Y: " + graphY);
        }

        if (r == null) {
            System.out.println("R validation failed");
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Выберите радиус R"));
            return null;
        }

        if (x == null) {
            System.out.println("X validation failed");
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Выберите координату X"));
            return null;
        }

        if (y == null) {
            System.out.println("Y is null");
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Введите координату Y"));
            hasErrors = true;
        } else {
            System.out.println("Y validation - fromGraph: " + fromGraph + ", y: " + y);
            if (!fromGraph && (y < -5 || y > 3)) {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка", "Y должен быть в диапазоне от -5 до 3"));
                hasErrors = true;
        }

        }
        if (hasErrors) {
            fromGraph = false;
            System.out.println("Has errors, returning");
            return null;
        }

        Point point = new Point(x, y, r);
        boolean isHit = AreaCheckerBean.isHit(point);
        this.lastHitResult = isHit;

        service.saveResult(x, y, r, isHit);

        String resultMessage = isHit ? "Попадание!" : "Промах!";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Результат", resultMessage));


        if (fromGraph) {
            this.graphX = null;
            this.graphY = null;
        }
        fromGraph = false;
        return null;
    }

    public List<ResultPoint> getResultsForCurrentRadius() {
        if (r == null) {
            return new ArrayList<>();
        }
        return service.getResultsByRadius(r);
    }

    public List<ResultPoint> getResults() {
        return service.getAllResults();
    }

    public int getResultsCount() {
        return service.getResultsCount();
    }

    public String checkHitFromGraph() {
        fromGraph = true;
        return checkHit();
    }

    public Boolean getLastHitResult() {
        return lastHitResult;
    }

    public void setLastHitResult(Boolean lastHitResult) {
        this.lastHitResult = lastHitResult;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setGraphY(Double graphY) {
        this.graphY = graphY;
    }

    public void setGraphX(Double graphX) {
        this.graphX = graphX;
    }

    public void setFromGraph(boolean fromGraph) {
        this.fromGraph = fromGraph;
    }

    public Double getGraphX() {
        return graphX;
    }

    public Double getGraphY() {
        return graphY;
    }

    public Double getY() {
        return y;
    }

    public Double getR() {
        return r;
    }

    public boolean isFromGraph() {
        return fromGraph;
    }

}
