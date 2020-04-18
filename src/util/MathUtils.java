package util;

public final class MathUtils {

    public static double getDistance(double pX1, double pY1, double pX2, double pY2) {
        double dX = pX2 - pX1;
        double dY = pY2 - pY1;
        return Math.sqrt(dX * dX + dY * dY);
    }

    private MathUtils() {
    }

}
