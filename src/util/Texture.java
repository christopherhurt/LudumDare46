package util;

import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class Texture {

    private final TextureAtlas mAtlas;
    private final float[] mCoordinates = new float[4];

    public Texture(TextureAtlas pAtlas, float pX, float pY, float pWidth, float pHeight) {
        mAtlas = Objects.requireNonNull(pAtlas);
        mCoordinates[0] = pX;
        mCoordinates[1] = pY;
        mCoordinates[2] = pWidth;
        mCoordinates[3] = pHeight;
    }

    void load() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mAtlas.getTexture());
        Shader.getInstance().loadTexture(mAtlas.getTexture(), mCoordinates);
    }

}
