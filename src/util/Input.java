package util;

import org.lwjgl.glfw.GLFW;

public final class Input {

    private static class InstanceHolder {
        static final Input INSTANCE = new Input();
    }

    public static Input getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final int NUM_KEYS = 349;
    private static final int NUM_BUTTONS = 8;
    private static final double[] X_HOLDER = new double[1];
    private static final double[] Y_HOLDER = new double[1];

    private final boolean[] mKeysDown = new boolean[NUM_KEYS];
    private final boolean[] mKeysPressed = new boolean[NUM_KEYS];
    private final boolean[] mKeysReleased = new boolean[NUM_KEYS];

    private final boolean[] mButtonsDown = new boolean[NUM_BUTTONS];
    private final boolean[] mButtonsPressed = new boolean[NUM_BUTTONS];
    private final boolean[] mButtonsReleased = new boolean[NUM_BUTTONS];

    private float mMouseX = 0.0f;
    private float mMouseY = 0.0f;

    void poll(long pWindow) {
        GLFW.glfwPollEvents();

        for (int key = 32; key < NUM_KEYS; key++) {
            boolean keyDown = GLFW.glfwGetKey(pWindow, key) == GLFW.GLFW_PRESS;
            mKeysReleased[key] = mKeysDown[key] && !keyDown;
            mKeysPressed[key] = !mKeysDown[key] && keyDown;
            mKeysDown[key] = keyDown;
        }

        for (int button = 0; button < NUM_BUTTONS; button++) {
            boolean buttonDown = GLFW.glfwGetMouseButton(pWindow, button) == GLFW.GLFW_PRESS;
            mButtonsReleased[button] = mButtonsDown[button] && !buttonDown;
            mButtonsPressed[button] = !mButtonsDown[button] && buttonDown;
            mButtonsDown[button] = buttonDown;
        }

        GLFW.glfwGetCursorPos(pWindow, X_HOLDER, Y_HOLDER);
        mMouseX = (float)(X_HOLDER[0] / Runner.WINDOW_SIZE * 2.0 - 1.0);
        mMouseY = (float)(Y_HOLDER[0] / Runner.WINDOW_SIZE * -2.0 + 1.0);
    }

    public boolean isKeyDown(int pKey) {
        return mKeysDown[pKey];
    }

    public boolean isKeyPressed(int pKey) {
        return mKeysPressed[pKey];
    }

    public boolean isKeyReleased(int pKey) {
        return mKeysReleased[pKey];
    }

    public boolean isButtonDown(int pButton) {
        return mButtonsDown[pButton];
    }

    public boolean isButtonPressed(int pButton) {
        return mButtonsPressed[pButton];
    }

    public boolean isButtonReleased(int pButton) {
        return mButtonsReleased[pButton];
    }

    public float getMouseX() {
        return mMouseX;
    }

    public float getMouseY() {
        return mMouseY;
    }

    private Input() {
    }

}
