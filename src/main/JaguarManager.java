package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;

class JaguarManager extends Entity {

    private static final double INITIAL_SPAWN_INTERVAL = 3.0;
    private static final double SPAWN_INTERVAL_MULTIPLIER = 0.9;
    private static final double SPAWN_INTERVAL_DECREASE_INTERVAL = 20.0;
    private static final double SPAWN_OFFSET = 0.1f;

    private final Hunter mHunter;

    private double mCurrentSpawnInterval = INITIAL_SPAWN_INTERVAL;
    private double mSpawnIntervalTimer = INITIAL_SPAWN_INTERVAL;
    private double mIntervalDecreaseTimer = SPAWN_INTERVAL_DECREASE_INTERVAL;

    JaguarManager(Hunter pHunter) {
        super(buildJaguarManager());
        mHunter = pHunter;
    }

    private static IEntityData buildJaguarManager() {
        return Entity.newDataBuilder(0.0, 0.0, 0.0, 0.0)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    @Override
    public void update() {
        mSpawnIntervalTimer -= Time.getInstance().getDelta();
        mIntervalDecreaseTimer -= Time.getInstance().getDelta();

        if (mIntervalDecreaseTimer <= 0.0) {
            mCurrentSpawnInterval *= SPAWN_INTERVAL_MULTIPLIER;
            mIntervalDecreaseTimer = SPAWN_INTERVAL_DECREASE_INTERVAL;
        }
        if (mSpawnIntervalTimer <= 0.0) {
            spawnJaguar();
            mSpawnIntervalTimer = mCurrentSpawnInterval;
        }
    }

    private void spawnJaguar() {
        double spawnX;
        double spawnY;

        double positionRange = 2.0 + Jaguar.WIDTH;
        double randomBoundary = Math.random() * positionRange - positionRange / 2.0;
        double fixedBoundary = 1.0 + Jaguar.HEIGHT / 2.0 + SPAWN_OFFSET;

        int side = (int)(Math.random() * 4.0);
        if (side == 0) {
            // Left
            spawnX = -fixedBoundary;
            spawnY = randomBoundary;
        } else if (side == 1) {
            // Top
            spawnX = randomBoundary;
            spawnY = fixedBoundary;
        } else if (side == 2) {
            // Right
            spawnX = fixedBoundary;
            spawnY = randomBoundary;
        } else if (side == 3) {
            // Bottom
            spawnX = randomBoundary;
            spawnY = -fixedBoundary;
        } else {
            throw new IllegalStateException("Invalid jaguar spawn side " + side);
        }

        EntityManager.getInstance().addEntity(new Jaguar(spawnX, spawnY, mHunter));
    }

}
