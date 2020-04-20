package main;

import java.util.Comparator;
import java.util.Optional;
import org.lwjgl.opengl.GL11;
import util.Animation;
import util.Entity;
import util.EntityManager;
import util.IEntityData;
import util.MathUtils;
import util.Texture;
import util.TextureAtlas;
import util.Time;

class Hunter extends Entity {

    private static final Texture AIM_TEXTURE =
            new Texture(new TextureAtlas("res/hunterAim.png", GL11.GL_LINEAR), 0.0, 0.0, 1.0, 1.0);

    private static final double FIRE_RATE = 0.5;
    private static final Animation MOVING_ANIMATION = new Animation(1.0,
            new Texture(new TextureAtlas("res/hunterIdle.png", GL11.GL_LINEAR), 0.0, 0.0, 1.0, 1.0));
    private static final Animation AIM_ANIMATION = new Animation(1.0,
            AIM_TEXTURE);
    private static final Animation SHOOTING_ANIMATION = new Animation(FIRE_RATE,
            AIM_TEXTURE,
            new Texture(new TextureAtlas("res/hunterFire.png", GL11.GL_LINEAR), 0.0, 0.0, 1.0, 1.0));

    private static final double SIZE = 0.4;
    private static final double BLOOD_SPRAY_INTERVAL = 0.5;
    private static final double MIN_STATE_CHANGE_INTERVAL = 0.5;
    private static final double MAX_STATE_CHANGE_INTERVAL = 1.0;

    private static final double INSET = 0.1;
    private static final double SPEED = 0.25;

    private final HealthManager mHealthManager;

    private boolean mState; // False if shooting, true if moving
    private double mStateChangeTimer;
    private double mBloodSprayTimer = BLOOD_SPRAY_INTERVAL;
    private double mFireTimer;
    private Jaguar mCurrentTarget = null;

    Hunter(HealthManager pHealthManager) {
        super(buildHunter());
        mHealthManager = pHealthManager;
        setState(false);
    }

    private static IEntityData buildHunter() {
        return Entity.newDataBuilder(0.0, 0.0, SIZE, SIZE)
                    .withColor(0.0, 0.0, 0.0)
                    .build();
    }

    @Override
    public void update() {
        if (!isDead()) {
            mStateChangeTimer -= Time.getInstance().getDelta();
            if (mStateChangeTimer <= 0.0) {
                changeState();
            }

            if (mState) {
                // Moving
                Optional<Entity> closestJaguar = EntityManager.getInstance().getEntities().stream().filter(pEntity ->
                        pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent())
                        .min(Comparator.comparingDouble(pJaguar ->
                                MathUtils.getDistance(pJaguar.mX.get(), pJaguar.mY.get(), mX.get(), mY.get())));

                double velocityX;
                double velocityY;
                if (closestJaguar.isPresent()) {
                    velocityX = mX.get() - closestJaguar.get().mX.get();
                    velocityY = mY.get() - closestJaguar.get().mY.get();
                    double length = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
                    velocityX = velocityX / length * SPEED;
                    velocityY = velocityY / length * SPEED;
                } else {
                    velocityX = 0.0;
                    velocityY = 0.0;
                }

                double newX = mX.get() + velocityX * Time.getInstance().getDelta();
                double newY = mY.get() + velocityY * Time.getInstance().getDelta();

                // Don't let the hunter run offscreen
                double rightBound = 1.0 - mWidth.get() * 0.28 / 2.0 - INSET;
                mX.set(Math.max(-rightBound, Math.min(rightBound, newX)));
                double topBound = 1.0 - mHeight.get() * 0.28 / 2.0 - INSET;
                mY.set(Math.max(-topBound, Math.min(topBound, newY)));
            } else {
                // Shooting
                if (mCurrentTarget != null) {
                    if (mCurrentTarget.isDead()) {
                        changeState();
                    } else {
                        mTheta.set(Math.toDegrees(Math.atan2(mCurrentTarget.mY.get() - mY.get(), mCurrentTarget.mX.get() - mX.get())));

                        mFireTimer -= Time.getInstance().getDelta();

                        if (mFireTimer <= 0.0) {
                            if (mCurrentTarget.shoot()) {
                                // Start moving if jaguar was killed
                                changeState();
                            } else {
                                mFireTimer = FIRE_RATE;
                            }
                        }
                    }
                } else {
                    // Look for a target
                    Entity targetJaguar = EntityManager.getInstance().getEntities().stream().filter(pEntity ->
                            pEntity.getTag().filter(pTag -> pTag.equals(Tag.JAGUAR.getTag())).isPresent())
                            .min(Comparator.comparingDouble(pJaguar -> MathUtils.getDistance(pJaguar.mX.get(), pJaguar.mY.get(), mX.get(), mY.get())))
                            .orElse(null);
                    if (targetJaguar != null) {
                        mCurrentTarget = (Jaguar)targetJaguar;
                        setAnimation(SHOOTING_ANIMATION);
                    }
                }
            }
        } else {
            mBloodSprayTimer -= Time.getInstance().getDelta();

            if (mBloodSprayTimer <= 0.0) {
                BloodParticles.newParticleSystem(mX.get(), mY.get());
                mBloodSprayTimer = BLOOD_SPRAY_INTERVAL;
            }
        }
    }

    private void changeState() {
        setState(!mState);
    }

    private void setState(boolean pState) {
        if (pState) {
            // Moving
            setAnimation(MOVING_ANIMATION);
            mCurrentTarget = null;
        } else {
            // Shooting
            setAnimation(AIM_ANIMATION);
            mFireTimer = FIRE_RATE / 2.0;
        }

        mStateChangeTimer = generateStateChangeTimer();
        mState = pState;
    }

    private static double generateStateChangeTimer() {
        return MIN_STATE_CHANGE_INTERVAL
                + (MAX_STATE_CHANGE_INTERVAL - MIN_STATE_CHANGE_INTERVAL) * Math.random();
    }

    void inflictDamage() {
        if (!isDead()) {
            mHealthManager.inflictDamage();
            BloodParticles.newParticleSystem(this);

            if (isDead()) {
                // Hide the hunter
                setAnimation(null);
                mWidth.set(0.0);
                mHeight.set(0.0);
            }
        }
    }

    boolean isDead() {
        return mHealthManager.isGameOver();
    }

}
