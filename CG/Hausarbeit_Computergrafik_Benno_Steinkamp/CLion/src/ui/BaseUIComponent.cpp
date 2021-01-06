//
// Created by benno on 13.08.2020.
//

#include "BaseUIComponent.h"

void BaseUIComponent::draw() {
    if(mVisible)
        pShader->activate();
}


BaseUIComponent::~BaseUIComponent() {
    delete pShader;
}

BaseUIComponent::BaseUIComponent(float windowWidth, float windowHeight) :
        pShader(new UIShader(windowWidth, windowHeight)) {}

void BaseUIComponent::hide() {
    mVisible = false;
}

void BaseUIComponent::show() {
    mVisible = true;
}


