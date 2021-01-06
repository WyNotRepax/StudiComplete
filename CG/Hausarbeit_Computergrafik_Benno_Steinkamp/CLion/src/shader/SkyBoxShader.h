//
// Created by benno on 21.08.2020.
//

#ifndef CLION_SKYBOXSHADER_H
#define CLION_SKYBOXSHADER_H


#include "BaseShader.h"

class SkyBoxShader : public BaseShader {
public:
    SkyBoxShader();

    void texture(unsigned int textureID);

    void activate(const BaseCamera &Cam) const override;

protected:
    GLint mModelViewProjLoc;
    GLuint mTextureID;
    GLint mTextureLoc;
};


#endif //CLION_SKYBOXSHADER_H
