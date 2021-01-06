#version 330 core
layout (location = 0) in vec4 Vertex;
layout (location = 1) in vec2 VertexTexcoord;

uniform mat4 Transform;

out vec2 Texcoord;
void main()
{
    gl_Position = Transform * Vertex;
    Texcoord = VertexTexcoord;
}
