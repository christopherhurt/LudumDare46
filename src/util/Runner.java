package util;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public final class Runner {

    static final int WINDOW_SIZE = 800;

    public static void main(String[] pArgs) {
        GLFWErrorCallback.createPrint(System.err).set();
        GLFW.glfwInit();

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        long window = GLFW.glfwCreateWindow(WINDOW_SIZE, WINDOW_SIZE, "Epic Game", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        GL11.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);

        EntityManager.getInstance().reset();

        while (!GLFW.glfwWindowShouldClose(window)) {
            Time.getInstance().markFrame();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            Input.getInstance().poll(window);
            EntityManager.getInstance().updateAll();
            EntityManager.getInstance().renderAll();
            GLFW.glfwSwapBuffers(window);
        }

        AudioFactory.destroy();
        GLFW.glfwTerminate();
    }

    private Runner() {}

}
