package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;
import util.MathUtils;

class Target extends Entity {

    private static final double SIZE = 0.1;
    private static final double LIFESPAN = 0.5;
    private static final double EXPLOSION_RADIUS = 0.075;

    private double mLife = LIFESPAN;

    Target(double pX, double pY) {
        super(buildTarget(pX, pY));
    }

    private static IEntityData buildTarget(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, SIZE, SIZE)
                    .withColor(1.0, 0.0, 1.0)
                    .build();
    }

    @Override
    public void update() {
        mLife -= Time.getInstance().getDelta();

        if (mLife <= 0.0) {
            // Kill any nearby jaguars
            EntityManager.getInstance().getEntities().removeIf(pEntity ->
                    pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent()
                            && MathUtils.getDistance(mX.get(), mY.get(), pEntity.mX.get(), pEntity.mY.get()) <= EXPLOSION_RADIUS);
            EntityManager.getInstance().removeEntity(this);
        }
    }

}
