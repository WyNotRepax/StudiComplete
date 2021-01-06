#version 330 core
uniform vec3 Color;
uniform sampler2D Texture;

in vec2 Texcoord;

out vec4 FragColor;

void main()
{
    FragColor = vec4(Color,1)*texture(Texture,Texcoord);
}