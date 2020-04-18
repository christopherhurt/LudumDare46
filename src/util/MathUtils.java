package util;

public final class MathUtils {

    public static float getDistance(float pX1, float pY1, float pX2, float pY2) {
        float dX = pX2 - pX1;
        float dY = pY2 - pY1;
        return (float)Math.sqrt(dX * dX + dY * dY);
    }

    private MathUtils() {
    }

}
