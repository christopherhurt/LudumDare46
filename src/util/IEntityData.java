package util;

import java.util.Optional;

public interface IEntityData {

    float getX();
    float getY();
    float getWidth();
    float getHeight();
    Optional<Texture> getTexture();
    Optional<Float> getColorR();
    Optional<Float> getColorG();
    Optional<Float> getColorB();
    float getTheta();
    Optional<String> getTag();

}
