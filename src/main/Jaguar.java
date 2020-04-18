package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.MathUtils;
import util.Time;

class Jaguar extends Entity {

    static final float WIDTH = 0.25f;
    static final float HEIGHT = 0.125f;

    private static final float SPEED = 0.25f;
    private static final float DAMAGE_RADIUS = 0.1f;

    private final Entity mHunter;
    private final HealthManager mHealthManager;

    Jaguar(float pX, float pY, Entity pHunter, HealthManager pHealthManager) {
        super(buildJaguar(pX, pY));
        mHunter = pHunter;
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildJaguar(float pX, float pY) {
        return Entity.newDataBuilder(pX, pY, WIDTH, HEIGHT)
                    .withColor(1.0f, 1.0f, 0.0f)
                    .withTag(Tag.JAGUAR.getTag())
                    .build();
    }

    @Override
    public void update() {
        float dirSize = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        float dirX = mHunter.mX.get() - mX.get();
        float dirY = mHunter.mY.get() - mY.get();

        // TODO: update theta

        float velX = dirX / dirSize * SPEED;
        float velY = dirY / dirSize * SPEED;
        mX.set(mX.get() + velX * Time.getInstance().getDelta());
        mY.set(mY.get() + velY * Time.getInstance().getDelta());

        float newDistance = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        if (newDistance <= DAMAGE_RADIUS && !mHealthManager.isGameOver()) {
            mHealthManager.inflictDamage();
            EntityManager.getInstance().removeEntity(this);
        }
    }

}
