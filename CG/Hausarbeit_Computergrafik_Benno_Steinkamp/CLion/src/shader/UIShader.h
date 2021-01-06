//
// Created by benno on 12.08.2020.
//

#ifndef CLION_UISHADER_H
#define CLION_UISHADER_H


#include "BaseShader.h"
#include "../Texture.h"
#include "../math/Matrix.h"

class UIShader : BaseShader {
public:
    UIShader(float windowWidth, float windowHeight);

    void activate();

    void color(const Color &color);

    void texture(Texture *texture);

protected:
    Texture *pTexture;
    bool mTextureChanged;
    GLint mTextureLoc;

    Color mColor;
    bool mColorChanged;
    GLint mColorLoc;

    Matrix mTransform;
    GLint mTransformLoc;
};


#endif //CLION_UISHADER_H
