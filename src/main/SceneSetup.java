package main;

import util.Audio;
import util.AudioFactory;
import util.EntityManager;

public final class SceneSetup {

    private static final Audio MUSIC = AudioFactory.createMusic("res/ludum46.ogg", 0.025f);

    public static void setup(EntityManager pEntityManager) {
        MUSIC.play();

        HealthManager healthManager = new HealthManager();
        pEntityManager.addEntity(healthManager);

        pEntityManager.addEntity(new ResetController(healthManager));

        pEntityManager.addEntity(new TargetManager(healthManager));

        ScreenWhipe screenWhipe = new ScreenWhipe();
        pEntityManager.addEntity(screenWhipe);

        Hunter hunter = new Hunter(healthManager);
        pEntityManager.addEntity(new JaguarManager(hunter, screenWhipe));
        pEntityManager.addEntity(hunter);
    }

}
