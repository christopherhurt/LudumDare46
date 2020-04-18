package main;

import java.util.ArrayList;
import java.util.List;
import util.Entity;
import util.EntityManager;
import util.IEntityData;

class HealthManager extends Entity {

    private static final int INITIAL_LIVES = 3;

    private static final float HEART_SIZE = 0.04f;
    private static final float HEART_SPACING = 0.02f;
    private static final float HEART_Y = 1.0f - HEART_SIZE / 2.0f - HEART_SPACING;

    private static final float GAME_OVER_OVERLAY_Y = 0.75f;
    private static final float GAME_OVER_OVERLAY_WIDTH = 1.0f;
    private static final float GAME_OVER_OVERLAY_HEIGHT = 0.25f;

    private final List<Entity> mHearts = new ArrayList<>(INITIAL_LIVES);

    private Entity mGameOverOverlay = null;

    HealthManager() {
        super(buildHealthManager());

        for (int i = 0; i < INITIAL_LIVES; i++) {
            float heartX = -1.0f + HEART_SIZE / 2.0f + (i + 1) * HEART_SPACING + i * HEART_SIZE;
            mHearts.add(constructHeart(heartX));
        }
    }

    private static IEntityData buildHealthManager() {
        return Entity.newDataBuilder(0.0f, 0.0f, 0.0f, 0.0f)
                    .withColor(0.0f, 0.0f, 0.0f)
                    .build();
    }

    private static Entity constructHeart(float pX) {
        IEntityData heartData = Entity.newDataBuilder(pX, HEART_Y, HEART_SIZE, HEART_SIZE)
                                    .withColor(1.0f, 0.0f, 0.0f)
                                    .build();
        return new Entity(heartData) {
            @Override
            protected void update() {
                // Do nothing
            }
        };
    }

    @Override
    public void update() {
        // Re-add overlays to scene so they're always visible above everything else
        mHearts.forEach(EntityManager.getInstance()::removeEntity);
        mHearts.forEach(EntityManager.getInstance()::addEntity);

        if (mGameOverOverlay != null) {
            EntityManager.getInstance().removeEntity(mGameOverOverlay);
            EntityManager.getInstance().addEntity(mGameOverOverlay);
        }
    }

    void inflictDamage() {
        if (!isGameOver()) {
            Entity lostHeart = mHearts.remove(mHearts.size() - 1);
            EntityManager.getInstance().removeEntity(lostHeart);

            if (isGameOver()) {
                gameOver();
            }
        }
    }

    boolean isGameOver() {
        return mHearts.isEmpty();
    }

    private void gameOver() {
        if (isGameOver()) {
            IEntityData gameOverOverlayData =
                    Entity.newDataBuilder(0.0f, GAME_OVER_OVERLAY_Y, GAME_OVER_OVERLAY_WIDTH, GAME_OVER_OVERLAY_HEIGHT)
                            .withColor(0.5f, 0.5f, 0.5f)
                            .build();
            mGameOverOverlay = new Entity(gameOverOverlayData) {
                @Override
                protected void update() {
                    // Do nothing
                }
            };
            EntityManager.getInstance().addEntity(mGameOverOverlay);
        }
    }

}
