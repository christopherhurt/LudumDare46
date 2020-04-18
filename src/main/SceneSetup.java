package main;

import util.EntityManager;

public final class SceneSetup {

    public static void setup(EntityManager pEntityManager) {
        pEntityManager.addEntity(new TargetManager());
    }

}
