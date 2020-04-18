package util;

public class FloatProperty {

    private float mValue;
    private boolean mDirty = true;

    FloatProperty(float pValue) {
        mValue = pValue;
    }

    public float get() {
        return mValue;
    }

    public void set(float pValue) {
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
