package util;

import org.lwjgl.opengl.GL20;

final class EntityShader extends Shader {

    private static class InstanceHolder {
        static final EntityShader INSTANCE = new EntityShader();
    }

    static EntityShader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String FRAGMENT_FILE = "src/shaders/entityFrag.glsl";

    private EntityShader() {
        super(FRAGMENT_FILE);
    }

    void loadTexture(int pTexture, float[] pVals) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uTexture"), pTexture);
        GL20.glUniform4fv(GL20.glGetUniformLocation(mProgram, "uTexCoordInfo"), pVals);
    }

    void loadHasTexture(boolean pHasTexture) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uHasTexture"), pHasTexture ? 1 : 0);
    }

    void loadColor(float pRed, float pGreen, float pBlue) {
        GL20.glUniform3f(GL20.glGetUniformLocation(mProgram, "uColor"), pRed, pGreen, pBlue);
    }

    void loadOpacity(float pOpacity) {
        GL20.glUniform1f(GL20.glGetUniformLocation(mProgram, "uOpacity"), pOpacity);
    }

}
