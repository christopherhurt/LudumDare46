#version 330 core

in vec2 iPassPos;

out vec4 oFragColor;

uniform sampler2D uTexture;
uniform vec4 uTexCoordInfo;

void main() {
    vec2 adjusted = (iPassPos + 1.0) / 2.0;
    vec2 textureCoord = adjusted * uTexCoordInfo.zw + uTexCoordInfo.xy;
    oFragColor = vec4(uTexture, textureCoord);
}
