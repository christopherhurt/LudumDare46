package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.Objects;

public class Texture {

    private final TextureAtlas mAtlas;
    private final float[] mCoordinates = new float[4];

    public Texture(TextureAtlas pAtlas, double pX, double pY, double pWidth, double pHeight) {
        mAtlas = Objects.requireNonNull(pAtlas);
        mCoordinates[0] = (float)pX;
        mCoordinates[1] = (float)pY;
        mCoordinates[2] = (float)pWidth;
        mCoordinates[3] = (float)pHeight;
    }

    void load(int pUnit) {
        GL13.glActiveTexture(pUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mAtlas.getTexture());
        EntityShader.getInstance().loadTexture(pUnit, mCoordinates);
    }

}
