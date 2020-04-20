package main;

import util.Entity;
import util.EntityManager;
import util.ParticleSystem;
import util.ParticleTextureAtlas;

final class BloodParticles {

    private static final ParticleTextureAtlas[] BLOOD_PARTICLE_ATLASES = {
            new ParticleTextureAtlas("res/blood.png", 8, 7),
            new ParticleTextureAtlas("res/blood2.png", 8, 7),
    };

    static void newParticleSystem(Entity pEntity) {
        EntityManager.getInstance().addParticleSystem(new ParticleSystem(BLOOD_PARTICLE_ATLASES, pEntity));
    }

    static void newParticleSystem(double pX, double pY) {
        EntityManager.getInstance().addParticleSystem(new ParticleSystem(BLOOD_PARTICLE_ATLASES, pX, pY));
    }

}
