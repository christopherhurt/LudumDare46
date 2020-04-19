package main;

import java.util.stream.Collectors;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Time;
import util.MathUtils;

class Target extends Entity {

    private static final double SIZE = 0.1;
    private static final double LIFESPAN = 0.5;
    private static final double EXPLOSION_RADIUS = 0.075;
    private static final double INITIAL_OPACITY = 0.5;

    private double mLife = LIFESPAN;

    Target(double pX, double pY) {
        super(buildTarget(pX, pY));
    }

    private static IEntityData buildTarget(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, SIZE, SIZE)
                    .withColor(1.0, 0.0, 1.0)
                    .withOpacity(INITIAL_OPACITY)
                    .build();
    }

    @Override
    public void update() {
        mLife -= Time.getInstance().getDelta();

        setOpacity(INITIAL_OPACITY + (1.0 - INITIAL_OPACITY) * (1.0 - mLife / LIFESPAN));

        if (mLife <= 0.0) {
            // Kill any nearby jaguars
            EntityManager.getInstance().getEntities().stream().filter(pEntity ->
                    pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent()
                            && MathUtils.getDistance(mX.get(), mY.get(), pEntity.mX.get(), pEntity.mY.get()) <= EXPLOSION_RADIUS)
                    .collect(Collectors.toList()).forEach(pEntity -> ((Jaguar)pEntity).kill());
            EntityManager.getInstance().removeEntity(this);
        }
    }

}
