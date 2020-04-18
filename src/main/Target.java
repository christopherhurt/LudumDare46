package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;
import util.MathUtils;

class Target extends Entity {

    private static final float SIZE = 0.1f;
    private static final float LIFESPAN = 0.5f;
    private static final float EXPLOSION_RADIUS = 0.075f;

    private float mLife = LIFESPAN;

    Target(float pX, float pY) {
        super(buildTarget(pX, pY));
    }

    private static IEntityData buildTarget(float pX, float pY) {
        return Entity.newDataBuilder(pX, pY, SIZE, SIZE)
                    .withColor(1.0f, 0.0f, 1.0f)
                    .build();
    }

    @Override
    public void update() {
        mLife -= Time.getInstance().getDelta();

        if (mLife <= 0.0f) {
            // Kill any nearby jaguars
            EntityManager.getInstance().getEntities().removeIf(pEntity ->
                    pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent()
                            && MathUtils.getDistance(mX.get(), mY.get(), pEntity.mX.get(), pEntity.mY.get()) <= EXPLOSION_RADIUS);
            EntityManager.getInstance().removeEntity(this);
        }
    }

}
