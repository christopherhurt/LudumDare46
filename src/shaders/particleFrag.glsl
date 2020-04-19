#version 330 core

in vec2 iPassTexCoords;

out vec4 oFragColor;

uniform sampler2D uTextureAtlas;
uniform int uAtlasSize;
uniform int uStageCount;
uniform float uStageProgress;

vec4 colorForStageIndex(int pStageIndex) {
    vec2 stageCoord = (iPassTexCoords + vec2(pStageIndex % uAtlasSize, pStageIndex / uAtlasSize)) / uAtlasSize;
    // Flip the y coordinate to start from top-left corner
    stageCoord.y = 1.0 - stageCoord.y;
    return texture(uTextureAtlas, stageCoord);
}

void main() {
    float stageValue = uStageProgress * (uStageCount - 1.0);
    int stageIndex1 = int(stageValue);
    int stageIndex2 = int(ceil(stageValue));
    float stageProgress = stageValue - stageIndex1;

    vec4 color1 = colorForStageIndex(stageIndex1);
    vec4 color2 = colorForStageIndex(stageIndex2);

    oFragColor = mix(color1, color2, stageProgress);
}
