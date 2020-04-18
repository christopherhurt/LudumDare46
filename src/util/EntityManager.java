package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntityManager {

    private static class InstanceHolder {
        static final EntityManager INSTANCE = new EntityManager();
    }

    static EntityManager getInstance() {
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

    public List<Entity> getEntitiesUnmodifiable() {
        return Collections.unmodifiableList(mEntities);
    }

    void updateAll() {
        mEntities.forEach(Entity::update);
    }

    void renderAll() {
        mEntities.forEach(Entity::prepareAndRender);
    }

}
