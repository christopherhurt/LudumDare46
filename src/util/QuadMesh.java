package util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

final class QuadMesh {

    private static final int[] QUAD_INDICES = new int[] {
            0, 1, 2,
            2, 3, 0,
    };
    private static final float[] QUAD_POS = new float[] {
            -0.5f, -0.5f,
            -0.5f,  0.5f,
             0.5f,  0.5f,
             0.5f, -0.5f,
    };

    static final int INDEX_COUNT = QUAD_INDICES.length;

    static void bind() {
        GL30.glBindVertexArray(GL30.glGenVertexArrays());

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.glGenBuffers());
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, QUAD_INDICES, GL15.GL_STATIC_DRAW);

        createVbo(QUAD_POS, 0, 2);
    }

    private static void createVbo(float[] pValues, int pAttribute, int pSize) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(pValues.length);
        buffer.put(pValues);
        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, GL15.glGenBuffers());
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(pAttribute, pSize, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(pAttribute);
    }

    private QuadMesh() {
    }

}
