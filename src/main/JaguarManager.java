package main;

import sun.plugin.dom.exception.InvalidStateException;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;

class JaguarManager extends Entity {

    private static final double INITIAL_SPAWN_INTERVAL = 3.0;
    private static final double SPAWN_INTERVAL_MULTIPLIER = 0.9;
    private static final double SPAWN_INTERVAL_DECREASE_INTERVAL = 20.0;

    private final Entity mHunter;
    private final HealthManager mHealthManager;

    private double mCurrentSpawnInterval = INITIAL_SPAWN_INTERVAL;
    private double mSpawnIntervalTimer = INITIAL_SPAWN_INTERVAL;
    private double mIntervalDecreaseTimer = SPAWN_INTERVAL_DECREASE_INTERVAL;

    JaguarManager(Entity pHunter, HealthManager pHealthManager) {
        super(buildJaguarManager());
        mHunter = pHunter;
        mHealthManager = pHealthManager;
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

        int side = (int)(Math.random() * 4.0);
        if (side == 0) {
            // Left
            spawnX = -(1.0 + Jaguar.WIDTH / 2.0);
            spawnY = randomPosition(Jaguar.HEIGHT);
        } else if (side == 1) {
            // Top
            spawnX = randomPosition(Jaguar.WIDTH);
            spawnY = 1.0 + Jaguar.HEIGHT / 2.0;
        } else if (side == 2) {
            // Right
            spawnX = 1.0 + Jaguar.WIDTH / 2.0;
            spawnY = randomPosition(Jaguar.HEIGHT);
        } else if (side == 3) {
            // Bottom
            spawnX = randomPosition(Jaguar.WIDTH);
            spawnY = -(1.0 + Jaguar.HEIGHT / 2.0);
        } else {
            throw new InvalidStateException("Invalid jaguar spawn side " + side);
        }

        EntityManager.getInstance().addEntity(new Jaguar(spawnX, spawnY, mHunter, mHealthManager));
    }

    private double randomPosition(double pDimension) {
        double positionRange = 2.0 + pDimension;
        return Math.random() * positionRange - positionRange / 2.0;
    }

}
