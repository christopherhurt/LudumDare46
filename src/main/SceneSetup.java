package main;

import util.EntityManager;

public final class SceneSetup {

    public static void setup(EntityManager pEntityManager) {
        HealthManager healthManager = new HealthManager();
        pEntityManager.addEntity(healthManager);

        pEntityManager.addEntity(new TargetManager(healthManager));

        Hunter hunter = new Hunter(healthManager);
        pEntityManager.addEntity(new JaguarManager(hunter, healthManager));
        pEntityManager.addEntity(hunter);
    }

}
