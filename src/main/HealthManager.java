package main;

import java.util.ArrayList;
import java.util.List;
import util.Entity;
import util.EntityManager;
import util.IEntityData;

class HealthManager extends Entity {

    private static final int INITIAL_LIVES = 3;

    private static final double HEART_SIZE = 0.04;
    private static final double HEART_SPACING = 0.02;
    private static final double HEART_Y = 1.0 - HEART_SIZE / 2.0 - HEART_SPACING;

    private static final double GAME_OVER_OVERLAY_Y = 0.75;
    private static final double GAME_OVER_OVERLAY_WIDTH = 1.0;
    private static final double GAME_OVER_OVERLAY_HEIGHT = 0.25;

    private final List<Entity> mHearts = new ArrayList<>(INITIAL_LIVES);

    private Entity mGameOverOverlay = null;

    HealthManager() {
        super(buildHealthManager());

        for (int i = 0; i < INITIAL_LIVES; i++) {
            double heartX = -1.0 + HEART_SIZE / 2.0 + (i + 1) * HEART_SPACING + i * HEART_SIZE;
            mHearts.add(constructHeart(heartX));
        }
    }

    private static IEntityData buildHealthManager() {
        return Entity.newDataBuilder(0.0, 0.0, 0.0, 0.0)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    private static Entity constructHeart(double pX) {
        IEntityData heartData = Entity.newDataBuilder(pX, HEART_Y, HEART_SIZE, HEART_SIZE)
                                    .withColor(1.0, 0.0, 0.0)
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
                    Entity.newDataBuilder(0.0, GAME_OVER_OVERLAY_Y, GAME_OVER_OVERLAY_WIDTH, GAME_OVER_OVERLAY_HEIGHT)
                            .withColor(0.5, 0.5, 0.5)
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
