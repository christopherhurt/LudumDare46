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
    float outOpacity;

    if (uHasTexture > 0) {
        vec2 textureCoord = iPassTexCoords * uTexCoordInfo.zw + uTexCoordInfo.xy;
        vec4 texColor = texture(uTexture, textureCoord);
        outColor = texColor.rgb;
        outOpacity = texColor.a * uOpacity;
    } else {
        outColor = uColor;
        outOpacity = uOpacity;
    }

    oFragColor = vec4(outColor, outOpacity);
}
