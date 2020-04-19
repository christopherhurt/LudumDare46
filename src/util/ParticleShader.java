package util;

import org.lwjgl.opengl.GL20;

final class ParticleShader extends Shader {

    private static class InstanceHolder {
        static final ParticleShader INSTANCE = new ParticleShader();
    }

    static ParticleShader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String FRAGMENT_FILE = "src/shaders/particleFrag.glsl";

    private ParticleShader() {
        super(FRAGMENT_FILE);
    }

    void loadTextureAtlas(int pUnit) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uTextureAtlas"), pUnit);
    }

    void loadAtlasSize(int pAtlasSize) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uAtlasSize"), pAtlasSize);
    }

    void loadStageCount(int pStageCount) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uStageCount"), pStageCount);
    }

    void loadStageProgress(float pStageProgress) {
        GL20.glUniform1f(GL20.glGetUniformLocation(mProgram, "uStageProgress"), pStageProgress);
    }

}
