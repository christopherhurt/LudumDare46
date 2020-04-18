package main;

import util.Entity;
import util.IEntityData;

class Jaguar extends Entity {

    private static final float WIDTH = 0.25f;
    private static final float HEIGHT = 0.125f;

    private final Entity mHunter;

    Jaguar(float pX, float pY, Entity pHunter) {
        super(buildJaguar(pX, pY));
        mHunter = pHunter;
    }

    private static IEntityData buildJaguar(float pX, float pY) {
        return Entity.newDataBuilder(pX, pY, WIDTH, HEIGHT)
                    .withColor(1.0f, 1.0f, 0.0f)
                    .withTag(Tag.JAGUAR.getTag())
                    .build();
    }

    @Override
    public void update() {
        // TODO: chase hunter, check distance
    }

}
