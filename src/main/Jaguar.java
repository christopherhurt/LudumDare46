package main;

import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.MathUtils;
import util.Texture;
import util.TextureAtlas;
import util.Time;

class Jaguar extends Entity {

    static final double WIDTH = 0.25;
    static final double HEIGHT = 0.125;

    private static final Texture TEXTURE;
    static {
        TextureAtlas atlas = new TextureAtlas("res/jaguar.png");
        double textureHeight = HEIGHT / WIDTH;
        TEXTURE = new Texture(atlas, 0.0, 0.5 - textureHeight / 2.0, 1.0, textureHeight);
    }

    private static final double SPEED = 0.25;
    private static final double DAMAGE_RADIUS = 0.1;

    private final Hunter mHunter;

    Jaguar(double pX, double pY, Hunter pHunter) {
        super(buildJaguar(pX, pY));
        mHunter = pHunter;
    }

    private static IEntityData buildJaguar(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, WIDTH, HEIGHT)
                    .withTexture(TEXTURE)
                    .withTag(Tag.JAGUAR.getTag())
                    .build();
    }

    @Override
    public void update() {
        double dirSize = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        double dirX = mHunter.mX.get() - mX.get();
        double dirY = mHunter.mY.get() - mY.get();

        mTheta.set(Math.toDegrees(Math.atan2(dirY, dirX)));

        double velX = dirX / dirSize * SPEED;
        double velY = dirY / dirSize * SPEED;
        mX.set(mX.get() + velX * Time.getInstance().getDelta());
        mY.set(mY.get() + velY * Time.getInstance().getDelta());

        double newDistance = MathUtils.getDistance(mX.get(), mY.get(), mHunter.mX.get(), mHunter.mY.get());
        if (newDistance <= DAMAGE_RADIUS && !mHunter.isDead()) {
            mHunter.inflictDamage();
            kill();
        }
    }

    void kill() {
        EntityManager.getInstance().removeEntity(this);
        BloodParticles.newParticleSystem(mX.get(), mY.get());
    }

}
