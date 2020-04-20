package util;

public class Animation {

    private final double mDuration;
    private final Texture[] mTextures;

    private double mTimer = 0.0;

    public Animation(double pDuration, Texture... pTextures) {
        mDuration = pDuration;
        mTextures = pTextures;
    }

    void restart() {
        mTimer = 0.0;
    }

    Texture updateAndGetCurrentTexture() {
        mTimer += Time.getInstance().getDelta();
        int index = (int)((mTimer % mDuration) / mDuration * mTextures.length);
        return mTextures[index];
    }

}
