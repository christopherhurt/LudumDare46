package util;

public final class Time {

    private static class InstanceHolder {
        static final Time INSTANCE = new Time();
    }

    public static Time getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private long mLastTime = System.nanoTime();
    private float mDelta = 0.0f;

    void markFrame() {
        long thisTime = System.nanoTime();
        mDelta = (float)((thisTime - mLastTime) / 1000000000.0);
        mLastTime = thisTime;
    }

    public float getDelta() {
        return mDelta;
    }

    private Time() {
    }

}
