
#include "UIShader.h"
#include "../DIRECTORIES.h"

UIShader::UIShader(float windowWidth, float windowHeight) :
        mColor(1, 1, 1),
        mColorChanged(true),
        pTexture(Texture::defaultTex()),
        mTextureChanged(true) {
    load(SHADER_DIRECTORY"vsui.glsl", SHADER_DIRECTORY"fsui.glsl");
    mColorLoc = getParameterID("Color");
    mTextureLoc = getParameterID("Texture");
    mTransformLoc = getParameterID("Transform");

    mTransform = Matrix().scale(1, -1, 1) *
                 Matrix().translation(-1, -1, 0) *
                 Matrix().scale(2.0f / windowWidth, 2.0f / windowHeight, 1);

}

void UIShader::activate() {
    if (ShaderInPipe != this) {
        glUseProgram(ShaderProgram);
    }
    ShaderInPipe = this;
    if (mColorLoc == GL_INVALID_INDEX) {
        fprintf(stderr, "Cloud not set Color!");
        return;
    }
    if (mColorChanged) {
        setParameter(mColorLoc, mColor);
        mColorChanged = false;
    }
    if (!pTexture) {
        pTexture = Texture::defaultTex();
    }
    pTexture->activate();
    glUniform1i(mTextureLoc, 0);

    setParameter(mTransformLoc, mTransform);

}

void UIShader::color(const Color &color) {
    mColor = color;
    mColorChanged = true;
}

void UIShader::texture(Texture *texture) {
    pTexture = texture;
    mTextureChanged = true;
}



