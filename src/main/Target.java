package main;

import java.util.stream.Collectors;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.Texture;
import util.TextureAtlas;
import util.Time;

class Target extends Entity {

    private static final Texture TEXTURE =
            new Texture(new TextureAtlas("res/target_sprites.png"), 0.0, 0.75, 0.25, 0.25);

    private static final double SIZE = 0.1;
    private static final double LIFESPAN = 0.5;
    private static final double INITIAL_OPACITY = 0.5;
    private static final int POLY_SPACE = 10000;
    private static final double COLLISION_OFFSET = 0.025;

    private double mLife = LIFESPAN;

    Target(double pX, double pY) {
        super(buildTarget(pX, pY));
    }

    private static IEntityData buildTarget(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, SIZE, SIZE)
                    .withTexture(TEXTURE)
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
                            && collidesWith(pEntity))
                    .collect(Collectors.toList()).forEach(pEntity -> ((Jaguar)pEntity).kill());
            EntityManager.getInstance().removeEntity(this);
        }
    }

    private boolean collidesWith(Entity pJaguar) {
        // Get this target point in jaguar space
        double theta = -Math.toRadians(pJaguar.mTheta.get());
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        double xDiff = mX.get() - pJaguar.mX.get();
        double yDiff = mY.get() - pJaguar.mY.get();
        double pAdjX = cos * xDiff - sin * yDiff + pJaguar.mX.get();
        double pAdjY = sin * xDiff + cos * yDiff + pJaguar.mY.get();

        double adjWidth = 0.5 * (pJaguar.mWidth.get() + SIZE) / 2.0 - COLLISION_OFFSET;
        double adjHeight = 0.7 * (pJaguar.mHeight.get() + SIZE) / 2.0 - COLLISION_OFFSET;
        boolean insideX = pAdjX >= pJaguar.mX.get() - adjWidth
                && pAdjX <= pJaguar.mX.get() + adjWidth;
        boolean insideY = pAdjY >= pJaguar.mY.get() - adjHeight
                && pAdjY <= pJaguar.mY.get() + adjHeight;

        return insideX && insideY;
    }

}
