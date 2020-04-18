package util;

public final class Time {

    private static class InstanceHolder {
        static final Time INSTANCE = new Time();
    }

    public static Time getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private long mLastTime = System.nanoTime();
    private double mDelta = 0.0;

    void markFrame() {
        long thisTime = System.nanoTime();
        mDelta = (thisTime - mLastTime) / 1000000000.0;
        mLastTime = thisTime;
    }

    public double getDelta() {
        return mDelta;
    }

    private Time() {
    }

}
