package main;

import util.Entity;
import util.EntityManager;
import util.ParticleSystem;
import util.ParticleTextureAtlas;

final class BloodParticles {

    private static final ParticleTextureAtlas BLOOD_PARTICLE_ATLAS =
            new ParticleTextureAtlas("res/particleTest.png", 4, 15);

    static void newParticleSystem(Entity pEntity) {
        EntityManager.getInstance().addParticleSystem(new ParticleSystem(BLOOD_PARTICLE_ATLAS, pEntity));
    }

    static void newParticleSystem(double pX, double pY) {
        EntityManager.getInstance().addParticleSystem(new ParticleSystem(BLOOD_PARTICLE_ATLAS, pX, pY));
    }

}
