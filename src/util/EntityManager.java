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

    void reset() {
        mEntities.clear();
        SceneSetup.setup(this);
    }

    void updateAll() {
        new ArrayList<>(mEntities).forEach(Entity::update);
    }

    void renderAll() {
        mEntities.forEach(Entity::prepareAndRender);
    }

}
