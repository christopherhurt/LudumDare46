#version 330 core

layout (location = 0) in vec2 iPos;
layout (location = 1) in vec2 iTexCoords;

out vec2 iPassTexCoords;

uniform mat3 uTransformation;

void main() {
    iPassTexCoords = iTexCoords;
    gl_Position = vec4(uTransformation * vec3(iPos, 1.0), 1.0);
}
