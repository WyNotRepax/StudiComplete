//
// Created by benno on 15.08.2020.
//

#include "UITextBox.h"


void UITextBox::draw() {
    if (mVisible) {
        if (drawBackground)
            UIRect::draw();
        for (auto &pair : VBs) {
            if (pair.second->vertexCount() == 0) {
                continue;
            }
            int id = pair.first;
            auto *pVB = pair.second;
            auto *pIB = IBs.at(id);
            static bool once = true;
            textShader->texture(pFont->getFontPage(id)->pTexture);
            textShader->activate();
            pVB->activate();
            pIB->activate();
            glDrawElements(GL_TRIANGLES, pIB->indexCount(), pIB->indexFormat(), 0);
            pIB->deactivate();
            pVB->deactivate();
        }
    }
}

UITextBox::UITextBox(float windowWidth, float windowHeight, float x, float y, float w, float h, Font *pFont) :
        UIRect(windowWidth, windowHeight, x, y, w, h),
        pFont(pFont),
        drawBackground(true),
        textShader(new UIShader(windowWidth, windowHeight)) {
    for (auto &pair : pFont->getFontPages()) {
        VBs[pair.first] = new VertexBuffer();
        IBs[pair.first] = new IndexBuffer();
    }

}

void UITextBox::setTextColor(Color c) {
    textShader->color(c);
}

UITextBox::~UITextBox() {
    for (auto &pair: VBs) {
        delete pair.second;
    }
    VBs.clear();
    for (auto &pair: IBs) {
        delete pair.second;
    }
    IBs.clear();
}

void UITextBox::setText(const char *text) {
    const char *currChar = text;
    int xOffset = 0;
    int yOffset = 0;
    for (auto &pair : VBs) {
        pair.second->begin();
    }
    for (auto &pair : IBs) {
        pair.second->begin();
    }
    while (*currChar) {
        if (drawChar(currChar, xOffset, yOffset)) {
            currChar++;
        } else {
            break;
        }
    }
    for (auto &pair : VBs) {
        pair.second->end();
    }
    for (auto &pair : IBs) {
        pair.second->end();
    }
}

bool UITextBox::drawChar(const char *c, int &offsetX, int &offsetY) {
    if (*c == '\n') {
        offsetX = 0;
        offsetY += pFont->getLineHeight();
        return offsetY + pFont->getLineHeight() <= mHeight;
    }
    auto *pFontChar = pFont->getFontChar(*c);
    if (!pFontChar) {
        fprintf(stderr, "Invalid character %c!\n", *c);
        return false;
    }
    auto *pFontPage = pFont->getFontPage(pFontChar->page);

    if (offsetX + pFontChar->width > mWidth) {
        offsetX = 0;
        offsetY += pFont->getLineHeight();
        if (offsetY + pFont->getLineHeight() > mHeight) {
            return false;
        }
    }

    auto *pVB = VBs.at(pFontChar->page);
    auto *pIB = IBs.at(pFontChar->page);


    float left = ((float) pFontChar->x / (float) pFontPage->pTexture->width());
    float top = ((float) pFontChar->y / (float) pFontPage->pTexture->height());
    float right = left + ((float) pFontChar->width / (float) pFontPage->pTexture->width());
    float bottom = top + ((float) pFontChar->height / (float) pFontPage->pTexture->height());

    unsigned int count = pVB->vertexCount();

    pVB->addTexcoord0(left, top);
    pVB->addTexcoord0(right, top);
    pVB->addTexcoord0(left, bottom);
    pVB->addTexcoord0(right, bottom);

    pVB->addVertex(mX + (float) offsetX, mY + (float) offsetY, 0);
    pVB->addVertex(mX + (float) offsetX + (float) pFontChar->width, mY + (float) offsetY, 0);
    pVB->addVertex(mX + (float) offsetX, mY + (float) offsetY + (float) pFontChar->height, 0);
    pVB->addVertex(mX + (float) offsetX + (float) pFontChar->width, mY + (float) offsetY + (float) pFontChar->height,
                   0);


    pIB->addIndex(count + 2);
    pIB->addIndex(count + 1);
    pIB->addIndex(count + 0);

    pIB->addIndex(count + 2);
    pIB->addIndex(count + 3);
    pIB->addIndex(count + 1);


    offsetX += pFontChar->width + pFont->getKerningAmount(*c, *(c + 1));


    return true;
}


