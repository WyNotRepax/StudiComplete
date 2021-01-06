//
//  BaseModel.cpp
//  ogl4
//
//  Created by Philipp Lensing on 19.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "BaseModel.h"

BaseModel::BaseModel() : pShader(nullptr), mDeleteShader(false), mShadowCaster(true), doDelete(false) {
    mTransform.identity();
}

BaseModel::~BaseModel() {
    if (mDeleteShader)
        delete pShader;
}

void BaseModel::shader(BaseShader *shader, bool deleteOnDestruction) {
    pShader = shader;
    mDeleteShader = deleteOnDestruction;
}

void BaseModel::draw(const BaseCamera &Cam) {
    if (!pShader) {
        std::cout << "BaseModel::draw() no shader found" << std::endl;
        return;
    }

    pShader->modelTransform(transform());
    pShader->activate(Cam);

}

