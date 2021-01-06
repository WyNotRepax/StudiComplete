//
// Created by benno on 21.08.2020.
//

#include "SkyBoxShader.h"
#include "../DIRECTORIES.h"

SkyBoxShader::SkyBoxShader() : BaseShader() {
    bool loaded = load(SHADER_DIRECTORY"vsskybox.glsl", SHADER_DIRECTORY"fsskybox.glsl");
    if (!loaded)
        throw std::exception();
    mModelViewProjLoc = getParameterID("ModelViewProjMat");
    mTextureLoc = getParameterID("skybox");
}

void SkyBoxShader::activate(const BaseCamera &Cam) const {
    BaseShader::activate(Cam);
    Matrix ModelViewProj = Cam.getProjectionMatrix() * Cam.getViewMatrix() * modelTransform();
    setParameter(mModelViewProjLoc, ModelViewProj);
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_CUBE_MAP, mTextureID);
    setParameter(mTextureLoc, 0);
}

void SkyBoxShader::texture(unsigned int textureID) {
    this->mTextureID = textureID;

}
