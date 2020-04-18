package main;

import org.lwjgl.glfw.GLFW;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Input;
import util.Texture;
import util.TextureAtlas;
import util.Time;

public final class SceneSetup {

    public static void setup(EntityManager pEntityManager) {
        // TODO: setup scene here
        IEntityData data = Entity.newDataBuilder(0.5f, 0.5f, 0.5f, 0.5f)
//                                .withColor(1.0f, 0.0f, 0.0f)
                                .withTexture(new Texture(new TextureAtlas("res/test.png"), 0.5f, 0.5f, 0.5f, 0.5f))
                                .build();
        pEntityManager.addEntity(new Entity(data) {
            @Override
            protected void update() {
                if (Input.getInstance().isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    System.out.println("SPACE PRESSED");
                }
                if (Input.getInstance().isKeyReleased(GLFW.GLFW_KEY_SPACE)) {
                    System.out.println("SPACE RELEASED");
                }
                if (Input.getInstance().isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
                    System.out.println("RIGHT MOUSE DOWN");
                }
                if (Input.getInstance().isKeyDown(GLFW.GLFW_KEY_LEFT)) {
                    mX.set(mX.get() - 0.25f * Time.getInstance().getDelta());
                }
                if (Input.getInstance().isKeyDown(GLFW.GLFW_KEY_W)) {
                    mTheta.set(mTheta.get() + 90.0f * Time.getInstance().getDelta());
                }
                if (Input.getInstance().isKeyDown(GLFW.GLFW_KEY_S)) {
                    mTheta.set(mTheta.get() - 90.0f * Time.getInstance().getDelta());
                }
            }
        });
    }

}
