package util;

import org.lwjgl.opengl.GL11;

class Particle {

    private static final ParticleShader SHADER = ParticleShader.getInstance();

    private static final double SIZE = 0.02;
    private static final double MOVING_TIME = 2;

    private static final float[] TRANSFORMATION_HOLDER = new float[9];

    private final double mTrajX;
    private final double mTrajY;
    private final double mInitialSpeed;

    private double mOffsetX;
    private double mOffsetY;
    private double mSpeed;

    Particle(double pOffsetX, double pOffsetY, double pTrajX, double pTrajY, double pInitialSpeed) {
        mOffsetX = pOffsetX;
        mOffsetY = pOffsetY;
        mInitialSpeed = pInitialSpeed;

        double trajLength = Math.sqrt(pTrajX * pTrajX + pTrajY * pTrajY);
        mTrajX = pTrajX / trajLength;
        mTrajY = pTrajY / trajLength;

        mSpeed = mInitialSpeed;
    }

    void update() {
        mSpeed -= Time.getInstance().getDelta() / MOVING_TIME * mInitialSpeed;

        if (mSpeed > 0.0) {
            mOffsetX += mTrajX * mSpeed * Time.getInstance().getDelta();
            mOffsetY += mTrajY * mSpeed * Time.getInstance().getDelta();
        }
    }

    void prepareAndRender(double pSystemX, double pSystemY) {
        buildTransformation(pSystemX, pSystemY);
        SHADER.loadTransformation(TRANSFORMATION_HOLDER);
        GL11.glDrawElements(GL11.GL_TRIANGLES, QuadMesh.INDEX_COUNT, GL11.GL_UNSIGNED_INT, 0);
    }

    private void buildTransformation(double pSystemX, double pSystemY) {
        TRANSFORMATION_HOLDER[0] = (float)SIZE;
        TRANSFORMATION_HOLDER[1] = 0.0f;
        TRANSFORMATION_HOLDER[2] = 0.0f;
        TRANSFORMATION_HOLDER[3] = 0.0f;
        TRANSFORMATION_HOLDER[4] = (float)SIZE;
        TRANSFORMATION_HOLDER[5] = 0.0f;
        TRANSFORMATION_HOLDER[6] = (float)(pSystemX + mOffsetX);
        TRANSFORMATION_HOLDER[7] = (float)(pSystemY + mOffsetY);
        TRANSFORMATION_HOLDER[8] = 1.0f;
    }

}
