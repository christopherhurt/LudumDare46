package util;

import org.lwjgl.opengl.GL11;

public class ParticleTextureAtlas {

    private static final ParticleShader SHADER = ParticleShader.getInstance();

    private final int mTexture;
    private final int mSideLength;
    private final int mStageCount;

    public ParticleTextureAtlas(String pFilePath, int pSideLength, int pStageCount) {
        mTexture = new TextureAtlas(pFilePath, GL11.GL_LINEAR).getTexture();
        mSideLength = pSideLength;
        mStageCount = pStageCount;
    }

    void loadAndBind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mTexture);
        SHADER.loadTextureAtlas(0);
        SHADER.loadAtlasSize(mSideLength);
        SHADER.loadStageCount(mStageCount);
    }

}
