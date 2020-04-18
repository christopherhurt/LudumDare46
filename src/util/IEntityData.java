package util;

import java.util.Optional;

public interface IEntityData {

    double getX();
    double getY();
    double getWidth();
    double getHeight();
    Optional<Texture> getTexture();
    Optional<Double> getColorR();
    Optional<Double> getColorG();
    Optional<Double> getColorB();
    double getTheta();
    Optional<String> getTag();

}
