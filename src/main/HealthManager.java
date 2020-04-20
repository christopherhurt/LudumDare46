package main;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Texture;
import util.TextureAtlas;

class HealthManager extends Entity {

    private static final Texture HEART_TEXTURE = new Texture(new TextureAtlas("res/heart.png", GL11.GL_LINEAR),
            0.0, 0.0, 1.0, 1.0);
    private static final Texture BACKDROP = new Texture(new TextureAtlas("res/backdrop.png"),
            0.0, 0.0, 1.0, 1.0);
    private static final Texture BACKDROP_TREE_LINE = new Texture(new TextureAtlas("res/backdrop_edges.png"),
            0.0, 0.0, 1.0, 1.0);
    private static final Texture GAME_OVER_TEXTURE = new Texture(new TextureAtlas("res/game_over_sprites.png"),
            0.0, 0.0, 1.0, 1.0);

    private static final int INITIAL_LIVES = 3;

    private static final double HEART_SIZE = 0.06;
    private static final double HEART_SPACING = 0.02;
    private static final double HEART_Y = 1.0 - HEART_SIZE / 2.0 - HEART_SPACING;

    private static final double GAME_OVER_OVERLAY_Y = 0.75;
    private static final double GAME_OVER_OVERLAY_SIZE = 0.5;

    private final List<Entity> mHearts = new ArrayList<>(INITIAL_LIVES);
    private final Entity mTreeLine;

    private Entity mGameOverOverlay = null;

    HealthManager() {
        super(buildHealthManager());

        for (int i = 0; i < INITIAL_LIVES; i++) {
            double heartX = -1.0 + HEART_SIZE / 2.0 + (i + 1) * HEART_SPACING + i * HEART_SIZE;
            mHearts.add(constructHeart(heartX));
        }

        mTreeLine = new Entity(Entity.newDataBuilder(0.0, 0.0, 2.0, 2.0)
                                    .withTexture(BACKDROP_TREE_LINE)
                                    .build()) {
            @Override
            protected void update() {
                // ????????
            }
        };
    }

    private static IEntityData buildHealthManager() {
        return Entity.newDataBuilder(0.0, 0.0, 2.0, 2.0)
                    .withTexture(BACKDROP)
                    .build();
    }

    private static Entity constructHeart(double pX) {
        IEntityData heartData = Entity.newDataBuilder(pX, HEART_Y, HEART_SIZE, HEART_SIZE)
                                    .withTexture(HEART_TEXTURE)
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
        EntityManager.getInstance().removeEntity(mTreeLine);
        EntityManager.getInstance().addEntity(mTreeLine);

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
                    Entity.newDataBuilder(0.0, GAME_OVER_OVERLAY_Y, GAME_OVER_OVERLAY_SIZE, GAME_OVER_OVERLAY_SIZE)
                            .withTexture(GAME_OVER_TEXTURE)
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
