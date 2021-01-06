//
// Created by benno on 15.08.2020.
//

#ifndef CLION_UITEXTBOX_H
#define CLION_UITEXTBOX_H


#include "UIRect.h"
#include "Font.h"

class UITextBox : public UIRect {
public:
    UITextBox(float windowWidth, float windowHeight, float x, float y, float w, float h, Font *pFont);

    ~UITextBox() override;

    void draw() override;

    void setTextColor(Color c);

    void setText(const char *text);

    bool drawBackground;
protected:
    UIShader *textShader;
    std::map<unsigned int, VertexBuffer *> VBs;
    std::map<unsigned int, IndexBuffer *> IBs;
    Font *pFont;

    bool drawChar(const char *c, int &offsetX, int &offsetY);
};


#endif //CLION_UITEXTBOX_H
