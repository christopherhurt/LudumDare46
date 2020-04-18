package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

final class Shader {

    private static class InstanceHolder {
        static final Shader INSTANCE = new Shader();
    }

    static Shader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String VERTEX_FILE = "src/shaders/vert.glsl";
    private static final String FRAGMENT_FILE = "src/shaders/frag.glsl";

    private int mProgram;

    private Shader() {
        int vertex = loadShader(VERTEX_FILE, GL20.GL_VERTEX_SHADER);
        int fragment = loadShader(FRAGMENT_FILE, GL20.GL_FRAGMENT_SHADER);

        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertex);
        GL20.glAttachShader(program, fragment);
        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);

        GL20.glDeleteShader(vertex);
        GL20.glDeleteShader(fragment);

        GL20.glUseProgram(program);
        mProgram = program;
        setup();
    }

    private static int loadShader(String pPath, int pType) {
        int shader = GL20.glCreateShader(pType);

        try {
            String source = Files.lines(Paths.get(pPath)).reduce("", (pAcc, pLine) -> pAcc + pLine + "\n");
            GL20.glShaderSource(shader, source);
            GL20.glCompileShader(shader);

            int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
            if(status == GL11.GL_FALSE) {
                String log = GL20.glGetShaderInfoLog(shader);
                throw new IllegalStateException("Error in shader " + pPath + ":\n" + log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shader;
    }

    private void setup() {
        GL20.glBindAttribLocation(mProgram, 0, "iPos");
    }

    void loadTransformation(float[] pVals) {
        GL20.glUniformMatrix3fv(GL20.glGetUniformLocation(mProgram, "uTransformation"), false, pVals);
    }

    void loadTexture(int pTexture, float[] pVals) {
        GL20.glUniform1i(GL20.glGetUniformLocation(mProgram, "uTexture"), pTexture);
        GL20.glUniform4fv(GL20.glGetUniformLocation(mProgram, "uTexCoordInfo"), pVals);
    }

}
