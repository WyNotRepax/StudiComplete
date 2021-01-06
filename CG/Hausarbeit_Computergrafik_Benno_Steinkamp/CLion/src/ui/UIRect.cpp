//
// Created by benno on 13.08.2020.
//

#include "UIRect.h"

UIRect::UIRect(float windowWidth, float windowHeight, float x, float y, float w, float h) :
        BaseUIComponent(windowWidth, windowHeight),
        mX(x), mY(y),
        mWidth(w), mHeight(h) {

    mVertexBuffer.begin();
    initVertexBuffer();
    initTexcoord();
    mVertexBuffer.end();

    mIndexBuffer.begin();
    initIndexBuffer();
    mIndexBuffer.end();
}

void UIRect::draw() {
    if(mVisible){
        BaseUIComponent::draw();
        mVertexBuffer.activate();
        mIndexBuffer.activate();

        glDrawElements(GL_TRIANGLES, mIndexBuffer.indexCount(), mIndexBuffer.indexFormat(), 0);

        mIndexBuffer.deactivate();
        mVertexBuffer.deactivate();
    }
}

void UIRect::color(const Color &color) {
    pShader->color(color);
}

void UIRect::setTexture(Texture *pTexture) {
    setTexture(pTexture, 0, 0, pTexture->width(), pTexture->height());
}

void UIRect::setTexture(Texture *pTexture, float texLeft, float texTop, float texRight, float texBottom) {
    pShader->texture(pTexture);
    mVertexBuffer.begin();
    initVertexBuffer();
    initTexcoord(texLeft / pTexture->width(),
                 texTop / pTexture->height(),
                 texRight / pTexture->width(),
                 texBottom / pTexture->height());
    mVertexBuffer.end();
}

void UIRect::initVertexBuffer() {
    mVertexBuffer.addVertex(mX, mY, 0);
    mVertexBuffer.addVertex(mX + mWidth, mY, 0);
    mVertexBuffer.addVertex(mX, mY + mHeight, 0);
    mVertexBuffer.addVertex(mX + mWidth, mY + mHeight, 0);
}

void UIRect::initTexcoord(float texLeft, float texTop, float texRight, float texBottom) {
    mVertexBuffer.addTexcoord0(texLeft, texTop);
    mVertexBuffer.addTexcoord0(texRight, texTop);
    mVertexBuffer.addTexcoord0(texLeft, texBottom);
    mVertexBuffer.addTexcoord0(texRight, texBottom);
}

void UIRect::initIndexBuffer() {
    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(1);
    mIndexBuffer.addIndex(0);

    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(3);
    mIndexBuffer.addIndex(1);
}




