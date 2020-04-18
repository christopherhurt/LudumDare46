package main;

import util.Entity;
import util.IEntityData;

class Hunter extends Entity {

    private static final double SIZE = 0.1;

    private final HealthManager mHealthManager;

    private boolean mIsDead = false;

    Hunter(HealthManager pHealthManager) {
        super(buildHunter());
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildHunter() {
        return Entity.newDataBuilder(0.0, 0.0, SIZE, SIZE)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    @Override
    public void update() {
        if (!isDead()) {
            // TODO: move around, shoot jaguars
        }
    }

    void inflictDamage() {
        if (!isDead()) {
            mHealthManager.inflictDamage();

            if (isDead()) {
                setColor(1.0, 1.0, 1.0);
            }
        }
    }

    boolean isDead() {
        return mHealthManager.isGameOver();
    }

}
