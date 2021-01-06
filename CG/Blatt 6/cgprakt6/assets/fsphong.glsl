#version 400

in vec3 Position;
in vec3 Normal;
in vec2 Texcoord;
in vec3 Tangent;
in vec3 BiTangent;

out vec4 FragColor;

uniform vec3 EyePos;
uniform vec3 DiffuseColor;
uniform vec3 SpecularColor;
uniform vec3 AmbientColor;
uniform float SpecularExp;
uniform sampler2D DiffuseTexture;
uniform sampler2D NormalTexture;

const int MAX_LIGHTS=14;

uniform sampler2D ShadowMapTexture[MAX_LIGHTS];
uniform mat4 ShadowMapMat[MAX_LIGHTS];

struct Light
{
    int Type;
    vec3 Color;
    vec3 Position;
    vec3 Direction;
    vec3 Attenuation;
    vec3 SpotRadius;
    int ShadowIndex;
};

uniform Lights
{
    int LightCount;
    Light lights[MAX_LIGHTS];
};


float sat(in float a)
{
    return clamp(a, 0.0, 1.0);
}

void main()
{

    mat3 mtrx = mat3(Tangent, BiTangent, Normal);

    vec4 DiffTex = texture(DiffuseTexture, Texcoord);
    if (DiffTex.a < 0.3){
        discard;
    }
    vec3 NormTex = (2 * vec3(texture(NormalTexture, Texcoord))) - vec3(1, 1, 1);
    vec3 N = normalize(mtrx * NormTex);



    vec3 E = normalize(EyePos-Position);


    vec3 Color = vec3(0, 0, 0);

    for (int i=0; i<LightCount;i++){


        vec3 L;
        float I;
        if (lights[i].Type != 1){
            L = normalize(lights[i].Position-Position);
            float dist = distance(lights[i].Position, Position);
            if (lights[i].Type == 0){
                // Point
                I = 1.0/(lights[i].Attenuation.x + lights[i].Attenuation.y * dist + lights[i].Attenuation.z * dist * dist);
            } else {
                // Spot
                float ang = dot(normalize(lights[i].Direction), L);
                I = (1/(dist*dist)) * (1-sat((ang-lights[i].SpotRadius.x)/(lights[i].SpotRadius.y-lights[i].SpotRadius.x)));
            }
        } else {
            // Directional
            L = -lights[i].Direction;
            I = 1;
        }

        vec3 H = normalize(E+L);
        vec3 DiffuseComponent = lights[i].Color * DiffuseColor * sat(dot(N, L));
        vec3 SpecularComponent = lights[i].Color * SpecularColor * pow(sat(dot(N, H)), SpecularExp);

        if (lights[i].Type == 0 || lights[i].ShadowIndex == -1){
            Color += ((DiffuseComponent + AmbientColor)*DiffTex.rgb + SpecularComponent) * I;
        } else {
            vec4 PosSM = ShadowMapMat[lights[i].ShadowIndex] * vec4(Position.xyz, 1);
            PosSM.xyz /= PosSM.w;
            PosSM.xy = PosSM.xy * 0.5 + 0.5;
            float DepthSM = texture(ShadowMapTexture[lights[i].ShadowIndex],PosSM.xy).x;
            if(DepthSM > PosSM.z){
                Color += ((DiffuseComponent + AmbientColor)*DiffTex.rgb + SpecularComponent) * I;
            }
        }


    }
    FragColor = vec4(Color, 1.0f);

}


