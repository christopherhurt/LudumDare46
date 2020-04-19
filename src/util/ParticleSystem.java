package util;

import java.util.HashSet;
import java.util.Set;

public class ParticleSystem {

    private static final double LIFESPAN = 2.5;
    private static final int NUM_PARTICLES = 200;
    private static final double MAX_RADIAL_OFFSET = 0.03;
    private static final double MAX_ANGULAR_OFFSET = Math.toRadians(20.0);
    private static final double MIN_INITIAL_SPEED = 0.015;
    private static final double MAX_INITIAL_SPEED = 0.08;

    private final ParticleTextureAtlas mAtlas;
    private final Entity mTarget;
    private final Set<Particle> mParticles = new HashSet<>(NUM_PARTICLES);

    private double mX;
    private double mY;
    private double mProgress = 0.0;

    public ParticleSystem(ParticleTextureAtlas pAtlas, Entity pTarget) {
        this(pAtlas, pTarget.mX.get(), pTarget.mY.get(), pTarget);
    }

    public ParticleSystem(ParticleTextureAtlas pAtlas, double pX, double pY) {
        this(pAtlas, pX, pY, null);
    }

    private ParticleSystem(ParticleTextureAtlas pAtlas, double pX, double pY, Entity pTarget) {
        mAtlas = pAtlas;
        mTarget = pTarget;
        mX = pX;
        mY = pY;

        initializeParticles();
    }

    private void initializeParticles() {
        for (int i = 0; i < NUM_PARTICLES ; i++) {
            // Get randomized offset from center
            double offsetTheta = Math.random() * Math.PI * 2.0;
            double offsetDistance = Math.random() * MAX_RADIAL_OFFSET;
            double initialSpeed = MIN_INITIAL_SPEED
                    + (MAX_INITIAL_SPEED - MIN_INITIAL_SPEED) * offsetDistance / MAX_RADIAL_OFFSET;
            double offsetX = Math.cos(offsetTheta) * offsetDistance;
            double offsetY = Math.sin(offsetTheta) * offsetDistance;

            // Adjust the offset vector by a random angle to generate trajectory
            double trajectoryTheta = Math.random() * 2 * MAX_ANGULAR_OFFSET - MAX_ANGULAR_OFFSET;
            double cos = Math.cos(trajectoryTheta);
            double sin = Math.sin(trajectoryTheta);
            double trajectoryX = offsetX * cos - offsetY * sin;
            double trajectoryY = offsetX * sin + offsetY * cos;

            mParticles.add(new Particle(offsetX, offsetY, trajectoryX, trajectoryY, initialSpeed));
        }
    }

    void update() {
        mProgress += Time.getInstance().getDelta() / LIFESPAN;

        if (mTarget != null) {
            mX = mTarget.mX.get();
            mY = mTarget.mY.get();
        }

        if (mProgress < 1.0) {
            mParticles.forEach(Particle::update);
        } else {
            EntityManager.getInstance().removeParticleSystem(this);
        }
    }

    void prepareAndRender() {
        mAtlas.loadAndBind();
        ParticleShader.getInstance().loadStageProgress((float)mProgress);
        mParticles.forEach(pParticle -> pParticle.prepareAndRender(mX, mY));
    }

}
