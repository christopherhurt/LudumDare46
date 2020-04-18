package main;

import util.Entity;
import util.IEntityData;

class Hunter extends Entity {

    private static final float SIZE = 0.1f;

    private final HealthManager mHealthManager;

    private boolean mIsDead = false;

    Hunter(HealthManager pHealthManager) {
        super(buildHunter());
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildHunter() {
        return Entity.newDataBuilder(0.0f, 0.0f, SIZE, SIZE)
                    .withColor(0.0f, 0.0f, 0.0f)
                    .build();
    }

    @Override
    public void update() {
        if (mHealthManager.isGameOver()) {
            die();
        } else {
            // TODO: move around, shoot jaguars
        }
    }

    private void die() {
        if (!mIsDead) {
            setColor(1.0f, 1.0f, 1.0f);
            mIsDead = true;
        }
    }

}
