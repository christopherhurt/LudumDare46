package main;

import sun.plugin.dom.exception.InvalidStateException;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;

class JaguarManager extends Entity {

    private static final float INITIAL_SPAWN_INTERVAL = 3.0f;
    private static final float SPAWN_INTERVAL_MULTIPLIER = 0.9f;
    private static final float SPAWN_INTERVAL_DECREASE_INTERVAL = 20.0f;

    private final Entity mHunter;
    private final HealthManager mHealthManager;

    private float mCurrentSpawnInterval = INITIAL_SPAWN_INTERVAL;
    private float mSpawnIntervalTimer = INITIAL_SPAWN_INTERVAL;
    private float mIntervalDecreaseTimer = SPAWN_INTERVAL_DECREASE_INTERVAL;

    JaguarManager(Entity pHunter, HealthManager pHealthManager) {
        super(buildJaguarManager());
        mHunter = pHunter;
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildJaguarManager() {
        return Entity.newDataBuilder(0.0f, 0.0f, 0.0f, 0.0f)
                    .withColor(0.0f, 0.0f, 0.0f)
                    .build();
    }

    @Override
    public void update() {
        mSpawnIntervalTimer -= Time.getInstance().getDelta();
        mIntervalDecreaseTimer -= Time.getInstance().getDelta();

        if (mIntervalDecreaseTimer <= 0.0f) {
            mCurrentSpawnInterval *= SPAWN_INTERVAL_MULTIPLIER;
            mIntervalDecreaseTimer = SPAWN_INTERVAL_DECREASE_INTERVAL;
        }
        if (mSpawnIntervalTimer <= 0.0f) {
            spawnJaguar();
            mSpawnIntervalTimer = mCurrentSpawnInterval;
        }
    }

    private void spawnJaguar() {
        float spawnX;
        float spawnY;

        int side = (int)(Math.random() * 4.0);
        if (side == 0) {
            // Left
            spawnX = -(1.0f + Jaguar.WIDTH / 2.0f);
            spawnY = randomPosition(Jaguar.HEIGHT);
        } else if (side == 1) {
            // Top
            spawnX = randomPosition(Jaguar.WIDTH);
            spawnY = 1.0f + Jaguar.HEIGHT / 2.0f;
        } else if (side == 2) {
            // Right
            spawnX = 1.0f + Jaguar.WIDTH / 2.0f;
            spawnY = randomPosition(Jaguar.HEIGHT);
        } else if (side == 3) {
            // Bottom
            spawnX = randomPosition(Jaguar.WIDTH);
            spawnY = -(1.0f + Jaguar.HEIGHT / 2.0f);
        } else {
            throw new InvalidStateException("Invalid jaguar spawn side " + side);
        }

        EntityManager.getInstance().addEntity(new Jaguar(spawnX, spawnY, mHunter, mHealthManager));
    }

    private float randomPosition(float pDimension) {
        float positionRange = 2.0f + pDimension;
        return (float)(Math.random() * positionRange - positionRange / 2.0);
    }

}
