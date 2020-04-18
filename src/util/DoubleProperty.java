package util;

public class DoubleProperty {

    private double mValue;
    private boolean mDirty = true;

    DoubleProperty(double pValue) {
        mValue = pValue;
    }

    public double get() {
        return mValue;
    }

    public void set(double pValue) {
        mValue = pValue;
        mDirty = true;
    }

    boolean isDirty() {
        return mDirty;
    }

    void markClean() {
        mDirty = false;
    }

}
