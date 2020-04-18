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
    private final float[] mColor = new float[3];

    private final String mTag;

    private final float[] mTransformation = new float[9];

    protected Entity(IEntityData pData) {
        mX = new FloatProperty(pData.getX());
        mY = new FloatProperty(pData.getY());
        mWidth = new FloatProperty(pData.getWidth());
        mHeight = new FloatProperty(pData.getHeight());
        mTheta = new FloatProperty(pData.getTheta());
        mTag = pData.getTag().orElse(null);
        mTexture = pData.getTexture().orElse(null);
        if (pData.getColorR().isPresent() && pData.getColorG().isPresent() && pData.getColorB().isPresent()) {
            mColor[0] = pData.getColorR().get();
            mColor[1] = pData.getColorG().get();
            mColor[2] = pData.getColorB().get();
        }
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

        boolean hasTexture = mTexture != null;
        SHADER.loadHasTexture(hasTexture);
        if (hasTexture) {
            mTexture.load(0);
        } else {
            SHADER.loadColor(mColor);
        }

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

    public static AppearanceStep newDataBuilder(float pX, float pY, float pWidth, float pHeight) {
        return new Builder(pX, pY, pWidth, pHeight);
    }

    public interface AppearanceStep {
        BuildStep withTexture(Texture pTexture);
        BuildStep withColor(float pColorR, float pColorG, float pColorB);
    }

    public interface BuildStep {
        BuildStep withTheta(float pTheta);
        BuildStep withTag(String pTag);
        IEntityData build();
    }

    private static class Builder
            implements
            AppearanceStep,
            BuildStep,
            IEntityData {

        private final float mX;
        private final float mY;
        private final float mWidth;
        private final float mHeight;

        private Texture mTexture = null;
        private Float mColorR = null;
        private Float mColorG = null;
        private Float mColorB = null;

        private float mTheta = 0.0f;
        private String mTag = null;

        Builder(float pX, float pY, float pWidth, float pHeight) {
            mX = pX;
            mY = pY;
            mWidth = pWidth;
            mHeight = pHeight;
        }

        @Override
        public BuildStep withTexture(Texture pTexture) {
            mTexture = pTexture;
            return this;
        }

        @Override
        public BuildStep withColor(float pColorR, float pColorG, float pColorB) {
            mColorR = pColorR;
            mColorG = pColorG;
            mColorB = pColorB;
            return this;
        }

        @Override
        public BuildStep withTheta(float pTheta) {
            mTheta = pTheta;
            return this;
        }

        @Override
        public BuildStep withTag(String pTag) {
            mTag = pTag;
            return this;
        }

        @Override
        public IEntityData build() {
            return this;
        }

        @Override
        public float getX() {
            return mX;
        }

        @Override
        public float getY() {
            return mY;
        }

        @Override
        public float getWidth() {
            return mWidth;
        }

        @Override
        public float getHeight() {
            return mHeight;
        }

        @Override
        public Optional<Texture> getTexture() {
            return Optional.ofNullable(mTexture);
        }

        @Override
        public Optional<Float> getColorR() {
            return Optional.ofNullable(mColorR);
        }

        @Override
        public Optional<Float> getColorG() {
            return Optional.ofNullable(mColorG);
        }

        @Override
        public Optional<Float> getColorB() {
            return Optional.ofNullable(mColorB);
        }

        @Override
        public float getTheta() {
            return mTheta;
        }

        @Override
        public Optional<String> getTag() {
            return Optional.ofNullable(mTag);
        }

    }

}
