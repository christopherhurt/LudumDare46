package main;

import util.Animation;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.MathUtils;
import util.Texture;
import util.TextureAtlas;
import util.Time;

class Jaguar extends Entity {

    static final double WIDTH = 0.6;
    static final double HEIGHT = WIDTH / 4.0;

    private static final double ANIMATION_DURATION = 0.5;
    private static final TextureAtlas ATLAS = new TextureAtlas("res/jaguar_sprites.png");
    private static final Texture[] TEXTURES;
    static {
        TEXTURES = new Texture[13];
        for (int i = 0; i < TEXTURES.length; i++) {
            TEXTURES[i] = new Texture(ATLAS, 0.0, (i + 3) * 0.0625, 0.25, 0.0625);
        }
    }

    private static final double SPEED = 0.25;
    private static final double DAMAGE_RADIUS = 0.1;
    private static final int HEALTH = 3;

    private final Hunter mHunter;

    private int mHealth = HEALTH;

    Jaguar(double pX, double pY, Hunter pHunter) {
        super(buildJaguar(pX, pY));
        mHunter = pHunter;
    }

    private static IEntityData buildJaguar(double pX, double pY) {
        return Entity.newDataBuilder(pX, pY, WIDTH, HEIGHT)
                    .withAnimation(newAnimation())
                    .withTag(Tag.JAGUAR.getTag())
                    .build();
    }

    private static Animation newAnimation() {
        return new Animation(ANIMATION_DURATION, TEXTURES);
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

    boolean shoot() {
        return false; // TODO
//        boolean noHealth = isDead();
//        if (!isDead()) {
//            mHealth--;
//            noHealth = isDead();
//            if (noHealth) {
//                kill();
//            }
//        }
//        return noHealth;
    }

    void kill() {
        mHealth = 0;
        EntityManager.getInstance().removeEntity(this);
        BloodParticles.newParticleSystem(mX.get(), mY.get());
    }

    boolean isDead() {
        return mHealth <= 0;
    }

}
