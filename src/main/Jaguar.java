package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.MathUtils;
import util.Time;

class Jaguar extends Entity {

    static final double WIDTH = 0.25;
    static final double HEIGHT = 0.125;

    private static final double SPEED = 0.25;
    private static final double DAMAGE_RADIUS = 0.1;

    private final Entity mHunter;
    private final HealthManager mHealthManager;

    Jaguar(double pX, double pY, Entity pHunter, HealthManager pHealthManager) {
        super(buildJaguar(pX, pY));
        mHunter = pHunter;
        mHealthManager = pHealthManager;
    }

    private static IEntityData buildJaguar(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, WIDTH, HEIGHT)
                    .withColor(1.0, 1.0, 0.0)
                    .withTag(Tag.JAGUAR.getTag())
                    .build();
    }

    @Override
    public void update() {
        double dirSize = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        double dirX = mHunter.mX.get() - mX.get();
        double dirY = mHunter.mY.get() - mY.get();

        // TODO: update theta

        double velX = dirX / dirSize * SPEED;
        double velY = dirY / dirSize * SPEED;
        mX.set(mX.get() + velX * Time.getInstance().getDelta());
        mY.set(mY.get() + velY * Time.getInstance().getDelta());

        double newDistance = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        if (newDistance <= DAMAGE_RADIUS && !mHealthManager.isGameOver()) {
            mHealthManager.inflictDamage();
            EntityManager.getInstance().removeEntity(this);
        }
    }

}
