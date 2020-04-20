package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Input;
import util.Texture;
import util.TextureAtlas;

public class ScreenWhipe extends Entity {

    private static final Texture TEXTURE = new Texture(new TextureAtlas("res/nuke_sprites.png", GL11.GL_LINEAR),
            0.0, 0.875, 0.125, 0.125);

    private static final int MAX_WHIPES = 3;
    private static final int KILL_STREAK = 15;

    private final List<Entity> mScreenWhipes = new ArrayList<>();

    private int mKillCounter = 0;

    ScreenWhipe() {
        super(buildScreenWhipe());
    }

    private static IEntityData buildScreenWhipe() {
        return Entity.newDataBuilder(0.0, 0.0, 0.0, 0.0)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    @Override
    public void update() {
        // Re-add screen whipes icons so they appear on top
        mScreenWhipes.forEach(EntityManager.getInstance()::removeEntity);
        mScreenWhipes.forEach(EntityManager.getInstance()::addEntity);

        if (Input.getInstance().isKeyPressed(GLFW.GLFW_KEY_SPACE) && !mScreenWhipes.isEmpty()) {
            // Kill all jaguars
            EntityManager.getInstance().getEntities().stream().filter(pEntity ->
                    pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent())
                    .collect(Collectors.toList()).forEach(pJaguar -> ((Jaguar)pJaguar).kill(false));
            mScreenWhipes.remove(mScreenWhipes.size() - 1);
        }
    }

    void logKill() {
        if (mScreenWhipes.size() < MAX_WHIPES) {
            mKillCounter++;

            if (mKillCounter >= KILL_STREAK) {
                mKillCounter = 0;
                addWhipe();
            }
        }
    }

    private void addWhipe() {
        int i = mScreenWhipes.size();
        double x = 1.0 - HealthManager.HEART_SIZE / 2.0 - i * HealthManager.HEART_SIZE - (i + 1) * HealthManager.HEART_SPACING;
        double y = -HealthManager.HEART_Y;
        double size = HealthManager.HEART_SIZE;
        mScreenWhipes.add(new Entity(Entity.newDataBuilder(x, y, size, size).withTexture(TEXTURE).build()) {
            @Override
            protected void update() {
                // asdkjfbnajkhsdgbjksdfhg
            }
        });
    }

}
