package util;

import java.util.ArrayList;
import main.SceneSetup;

import java.util.List;

public final class EntityManager {

    private static class InstanceHolder {
        static final EntityManager INSTANCE = new EntityManager();
    }

    public static EntityManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    static {
        QuadMesh.bind();
    }

    private final List<Entity> mEntities = new ArrayList<>();
    private final List<ParticleSystem> mParticleSystems = new ArrayList<>();

    private boolean mResetOnUpdateFinish = false;

    private EntityManager() {
    }

    public void addEntity(Entity pEntity) {
        mEntities.add(pEntity);
    }

    public void removeEntity(Entity pEntity) {
        mEntities.remove(pEntity);
    }

    public void addParticleSystem(ParticleSystem pParticleSystem) {
        mParticleSystems.add(pParticleSystem);
    }

    void removeParticleSystem(ParticleSystem pParticleSystem) {
        mParticleSystems.remove(pParticleSystem);
    }

    public List<Entity> getEntities() {
        return mEntities;
    }

    public void reset() {
        mResetOnUpdateFinish = true;
    }

    void updateAll() {
        new ArrayList<>(mEntities).forEach(Entity::update);
        new ArrayList<>(mParticleSystems).forEach(ParticleSystem::update);

        if (mResetOnUpdateFinish) {
            mEntities.clear();
            mParticleSystems.clear();
            AudioFactory.stopAll();
            SceneSetup.setup(this);
            mResetOnUpdateFinish = false;
        }
    }

    void renderAll() {
        EntityShader.getInstance().use();
        mEntities.forEach(Entity::prepareAndRender);

        ParticleShader.getInstance().use();
        mParticleSystems.forEach(ParticleSystem::prepareAndRender);
    }

}
