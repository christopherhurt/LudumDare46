#version 330 core

layout (location = 0) in vec2 iPos;

out vec2 iPassPos;

uniform mat3 uTransformation;

void main() {
    iPassPos = iPos;
    gl_Position = vec4(uTransformation * vec3(iPos, 1.0), 1.0);
}
