package main;

import util.Entity;
import util.IEntityData;
import util.Time;

class Hunter extends Entity {

    private static final double SIZE = 0.1;
    private static final double BLOOD_SPRAY_INTERVAL = 0.5;

    private final HealthManager mHealthManager;

    private boolean mIsDead = false;
    private double mBloodSprayTimer = BLOOD_SPRAY_INTERVAL;

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
        } else {
            mBloodSprayTimer -= Time.getInstance().getDelta();

            if (mBloodSprayTimer <= 0.0) {
                BloodParticles.newParticleSystem(mX.get(), mY.get());
                mBloodSprayTimer = BLOOD_SPRAY_INTERVAL;
            }
        }
    }

    void inflictDamage() {
        if (!isDead()) {
            mHealthManager.inflictDamage();
            BloodParticles.newParticleSystem(this);

            if (isDead()) {
                // Hide the hunter
                setTexture(null);
                mWidth.set(0.0);
                mHeight.set(0.0);
            }
        }
    }

    boolean isDead() {
        return mHealthManager.isGameOver();
    }

}
