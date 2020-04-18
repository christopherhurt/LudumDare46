#version 330 core

in vec2 iPassTexCoords;

out vec4 oFragColor;

uniform int uHasTexture;
uniform sampler2D uTexture;
uniform vec4 uTexCoordInfo;

uniform vec3 uColor;

void main() {
    if (uHasTexture > 0) {
        vec2 textureCoord = iPassTexCoords * uTexCoordInfo.zw + uTexCoordInfo.xy;
        oFragColor = texture(uTexture, textureCoord);
    } else {
        oFragColor = vec4(uColor, 1.0);
    }
}
