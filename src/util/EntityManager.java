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

    private boolean mResetOnUpdateFinish = false;

    private EntityManager() {
    }

    public void addEntity(Entity pEntity) {
        mEntities.add(pEntity);
    }

    public void removeEntity(Entity pEntity) {
        mEntities.remove(pEntity);
    }

    public List<Entity> getEntities() {
        return mEntities;
    }

    public void reset() {
        mResetOnUpdateFinish = true;
    }

    void updateAll() {
        new ArrayList<>(mEntities).forEach(Entity::update);

        if (mResetOnUpdateFinish) {
            mEntities.clear();
            SceneSetup.setup(this);
            mResetOnUpdateFinish = false;
        }
    }

    void renderAll() {
        mEntities.forEach(Entity::prepareAndRender);
    }

}
