//
// Created by benno on 13.08.2020.
//

#ifndef CLION_BASEUICOMPONENT_H
#define CLION_BASEUICOMPONENT_H


#include "../shader/UIShader.h"

class BaseUIComponent {
public:
    BaseUIComponent(float windowWidth, float windowHeight);
    virtual ~BaseUIComponent();
    virtual void draw();
    void hide();
    void show();
protected:
    UIShader *pShader;
    bool mVisible;
};


#endif //CLION_BASEUICOMPONENT_H
