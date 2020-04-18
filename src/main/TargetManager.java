package main;

import org.lwjgl.glfw.GLFW;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Input;
import util.Time;

class TargetManager extends Entity {

    private static final float COOLDOWN = 1.0f;

    private final HealthManager mHealthManager;

    private float mCooldownTimer = 0.0f;

    TargetManager(HealthManager pHealthManager) {
        super(buildTargetManager());
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildTargetManager() {
        return Entity.newDataBuilder(0.0f, 0.0f, 0.0f, 0.0f)
                    .withColor(0.0f, 0.0f, 0.0f)
                    .build();
    }

    @Override
    public void update() {
        if (!mHealthManager.isGameOver()) {
            mCooldownTimer -= Time.getInstance().getDelta();

            if (Input.getInstance().isButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT) && mCooldownTimer <= 0.0f) {
                // Drop a target
                EntityManager.getInstance().addEntity(new Target(Input.getInstance().getMouseX(), Input.getInstance().getMouseY()));
                mCooldownTimer = COOLDOWN;
            }
        }
    }

}
