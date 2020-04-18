package main;

import org.lwjgl.glfw.GLFW;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Input;

class ResetController extends Entity {

    private final HealthManager mHealthManager;

    ResetController(HealthManager pHealthManager) {
        super(buildResetController());
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildResetController() {
        return Entity.newDataBuilder(0.0, 0.0, 0.0, 0.0)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    @Override
    public void update() {
        if (mHealthManager.isGameOver() && Input.getInstance().isKeyPressed(GLFW.GLFW_KEY_ENTER)) {
            EntityManager.getInstance().reset();
        }
    }

}
