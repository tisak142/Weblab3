package Beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;


@Named
@ApplicationScoped
public class AreaCheckerBean {

    public static boolean isHit(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        if ( ((x * x + y * y) <= (r * r)) && (x >=0) && (y >= 0)) {
            return true;
        }
        if ( (x<= 0) && (y >= 0) && (y <= (x + r/2)) ) {
            return true;
        }
        if ( (x <= 0) && (y <= 0) && (x >= -r) && (y >= (-r/2)) ) {
            return true;
        }
        return false;
    }
}
