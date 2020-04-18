package util;

import org.lwjgl.opengl.GL11;

import java.util.Optional;

public abstract class Entity {

    private static final Shader SHADER = Shader.getInstance();

    protected final FloatProperty mX;
    protected final FloatProperty mY;
    protected final FloatProperty mWidth;
    protected final FloatProperty mHeight;
    protected final FloatProperty mTheta;

    private final Texture mTexture;
    private final float mColorR;
    private final float mColorG;
    private final float mColorB;

    private final String mTag;

    private final float[] mTransformation = new float[9];

    private Entity(float pX, float pY, float pWidth, float pHeight, float pTheta, String pTag,
                   Texture pTexture, float pColorR, float pColorG, float pColorB) {
        mX = new FloatProperty(pX);
        mY = new FloatProperty(pY);
        mWidth = new FloatProperty(pWidth);
        mHeight = new FloatProperty(pHeight);
        mTheta = new FloatProperty(pTheta);
        mTag = pTag;
        mTexture = pTexture;
        mColorR = pColorR;
        mColorG = pColorG;
        mColorB = pColorB;
    }

    protected abstract void update();

    public Optional<String> getTag() {
        return Optional.ofNullable(mTag);
    }

    void prepareAndRender() {
        if (mX.isDirty() || mY.isDirty() || mWidth.isDirty() || mHeight.isDirty() || mTheta.isDirty()) {
            buildTransformation();
            mX.markClean();
            mY.markClean();
            mWidth.markClean();
            mHeight.markClean();
            mTheta.markClean();
        }
        SHADER.loadTransformation(mTransformation);

        // TODO: prepare appearance (create stuff for color options)

        GL11.glDrawElements(GL11.GL_TRIANGLES, QuadMesh.INDEX_COUNT, GL11.GL_UNSIGNED_INT, 0);
    }

    private void buildTransformation() {
        double radians = Math.toRadians(mTheta.get());
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        mTransformation[0] = mWidth.get() * cos;
        mTransformation[1] = mWidth.get() * sin;
        mTransformation[2] = 0.0f;
        mTransformation[3] = mHeight.get() * -sin;
        mTransformation[4] = mHeight.get() * cos;
        mTransformation[5] = 0.0f;
        mTransformation[6] = mX.get();
        mTransformation[7] = mY.get();
        mTransformation[8] = 1.0f;
    }

    // TODO: build steps

    public static class Builder {

        private final float mX;
        private final float mY;
        private final float mWidth;
        private final float mHeight;

        public Builder(float pX, float pY, float pWidth, float pHeight) {
            mX = pX;
            mY = pY;
            mWidth = pWidth;
            mHeight = pHeight;
        }

        public Builder withTheta(float pTheta) {
            // TODO
            return this;
        }

        public Entity build() {
            return null; // TODO
        }

    }

}
