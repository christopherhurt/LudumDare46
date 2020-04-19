package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

abstract class Shader {

    private static final String VERTEX_FILE = "src/shaders/entityVert.glsl";

    final int mProgram;

    Shader(String pFragmentFile) {
        int vertex = loadShader(VERTEX_FILE, GL20.GL_VERTEX_SHADER);
        int fragment = loadShader(pFragmentFile, GL20.GL_FRAGMENT_SHADER);

        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertex);
        GL20.glAttachShader(program, fragment);
        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);

        GL20.glDeleteShader(vertex);
        GL20.glDeleteShader(fragment);

        mProgram = program;
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

    void use() {
        GL20.glUseProgram(mProgram);
    }

    void loadTransformation(float[] pVals) {
        GL20.glUniformMatrix3fv(GL20.glGetUniformLocation(mProgram, "uTransformation"), false, pVals);
    }

}
