#version 330 core

in vec2 iPassTexCoords;

out vec4 oFragColor;

uniform int uHasTexture;
uniform sampler2D uTexture;
uniform vec4 uTexCoordInfo;

uniform vec3 uColor;
uniform float uOpacity;

void main() {
    vec3 outColor;

    if (uHasTexture > 0) {
        vec2 textureCoord = iPassTexCoords * uTexCoordInfo.zw + uTexCoordInfo.xy;
        outColor = texture(uTexture, textureCoord).rgb;
    } else {
        outColor = uColor;
    }

    oFragColor = vec4(outColor, uOpacity);
}
