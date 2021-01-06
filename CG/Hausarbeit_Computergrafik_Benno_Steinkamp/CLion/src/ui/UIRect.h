//
// Created by benno on 13.08.2020.
//

#ifndef CLION_UIRECT_H
#define CLION_UIRECT_H

#include "BaseUIComponent.h"
#include "../helper/VertexBuffer.h"
#include "../helper/IndexBuffer.h"

class UIRect : public BaseUIComponent {
public:
    UIRect(float windowWidth, float windowHeight, float x, float y, float w, float h);

    ~UIRect() override = default;

    void draw() override;

    void color(const Color &color);

    void setTexture(Texture *pTexture, float texLeft, float texTop, float texRight, float texBottom);

    void setTexture(Texture *pTexture);

protected:
    float mX;
    float mY;
    float mWidth;
    float mHeight;

    VertexBuffer mVertexBuffer;
    IndexBuffer mIndexBuffer;

    void initVertexBuffer();

    void initIndexBuffer();

    void initTexcoord(float texLeft = 0, float texTop = 0, float texRight = 1, float texBottom = 1);
};


#endif //CLION_UIRECT_H
