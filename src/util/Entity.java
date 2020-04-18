package util;

import org.lwjgl.opengl.GL11;

import java.util.Optional;

public abstract class Entity {

    private static final Shader SHADER = Shader.getInstance();

    public final DoubleProperty mX;
    public final DoubleProperty mY;
    public final DoubleProperty mWidth;
    public final DoubleProperty mHeight;
    public final DoubleProperty mTheta;

    private Texture mTexture;
    private double mColorR = 0.0;
    private double mColorG = 0.0;
    private double mColorB = 0.0;
    private double mOpacity;

    private final String mTag;

    private final float[] mTransformation = new float[9];

    protected Entity(IEntityData pData) {
        mX = new DoubleProperty(pData.getX());
        mY = new DoubleProperty(pData.getY());
        mWidth = new DoubleProperty(pData.getWidth());
        mHeight = new DoubleProperty(pData.getHeight());
        mTheta = new DoubleProperty(pData.getTheta());
        mTag = pData.getTag().orElse(null);
        mTexture = pData.getTexture().orElse(null);
        if (pData.getColorR().isPresent() && pData.getColorG().isPresent() && pData.getColorB().isPresent()) {
            setColor(pData.getColorR().get(), pData.getColorG().get(), pData.getColorB().get());
        }
        mOpacity = pData.getOpacity();
    }

    protected abstract void update();

    public Optional<String> getTag() {
        return Optional.ofNullable(mTag);
    }

    public void setTexture(Texture pTexture) {
        mTexture = pTexture;
    }

    public void setColor(double pColorR, double pColorG, double pColorB) {
        mColorR = pColorR;
        mColorG = pColorG;
        mColorB = pColorB;
    }

    public void setOpacity(double pOpacity) {
        mOpacity = pOpacity;
    }

    void prepareAndRender() {
        if (mWidth.get() > 0.0 && mHeight.get() > 0.0) {
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
                SHADER.loadColor((float)mColorR, (float)mColorG, (float)mColorB);
            }

            SHADER.loadOpacity((float)mOpacity);

            GL11.glDrawElements(GL11.GL_TRIANGLES, QuadMesh.INDEX_COUNT, GL11.GL_UNSIGNED_INT, 0);
        }
    }

    private void buildTransformation() {
        double radians = Math.toRadians(mTheta.get());
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        mTransformation[0] = (float)(mWidth.get() * cos);
        mTransformation[1] = (float)(mWidth.get() * sin);
        mTransformation[2] = 0.0f;
        mTransformation[3] = (float)(mHeight.get() * -sin);
        mTransformation[4] = (float)(mHeight.get() * cos);
        mTransformation[5] = 0.0f;
        mTransformation[6] = (float)mX.get();
        mTransformation[7] = (float)mY.get();
        mTransformation[8] = 1.0f;
    }

    public static AppearanceStep newDataBuilder(double pX, double pY, double pWidth, double pHeight) {
        return new Builder(pX, pY, pWidth, pHeight);
    }

    public interface AppearanceStep {
        BuildStep withTexture(Texture pTexture);
        BuildStep withColor(double pColorR, double pColorG, double pColorB);
    }

    public interface BuildStep {
        BuildStep withTheta(double pTheta);
        BuildStep withTag(String pTag);
        BuildStep withOpacity(double pOpacity);
        IEntityData build();
    }

    private static class Builder
            implements
            AppearanceStep,
            BuildStep,
            IEntityData {

        private final double mX;
        private final double mY;
        private final double mWidth;
        private final double mHeight;

        private Texture mTexture = null;
        private Double mColorR = null;
        private Double mColorG = null;
        private Double mColorB = null;

        private double mTheta = 0.0;
        private String mTag = null;
        private double mOpacity = 1.0;

        Builder(double pX, double pY, double pWidth, double pHeight) {
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
        public BuildStep withColor(double pColorR, double pColorG, double pColorB) {
            mColorR = pColorR;
            mColorG = pColorG;
            mColorB = pColorB;
            return this;
        }

        @Override
        public BuildStep withTheta(double pTheta) {
            mTheta = pTheta;
            return this;
        }

        @Override
        public BuildStep withTag(String pTag) {
            mTag = pTag;
            return this;
        }

        @Override
        public BuildStep withOpacity(double pOpacity) {
            mOpacity = pOpacity;
            return this;
        }

        @Override
        public IEntityData build() {
            return this;
        }

        @Override
        public double getX() {
            return mX;
        }

        @Override
        public double getY() {
            return mY;
        }

        @Override
        public double getWidth() {
            return mWidth;
        }

        @Override
        public double getHeight() {
            return mHeight;
        }

        @Override
        public Optional<Texture> getTexture() {
            return Optional.ofNullable(mTexture);
        }

        @Override
        public Optional<Double> getColorR() {
            return Optional.ofNullable(mColorR);
        }

        @Override
        public Optional<Double> getColorG() {
            return Optional.ofNullable(mColorG);
        }

        @Override
        public Optional<Double> getColorB() {
            return Optional.ofNullable(mColorB);
        }

        @Override
        public double getOpacity() {
            return mOpacity;
        }

        @Override
        public double getTheta() {
            return mTheta;
        }

        @Override
        public Optional<String> getTag() {
            return Optional.ofNullable(mTag);
        }

    }

}
