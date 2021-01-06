//
//  BaseModel.hpp
//  ogl4
//
//  Created by Philipp Lensing on 19.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#ifndef BaseModel_hpp
#define BaseModel_hpp

#include <stdio.h>
#include "../helper/Camera.h"
#include "../math/Matrix.h"
#include "../shader/BaseShader.h"
#include "../helper/Aabb.h"

class BaseModel {
public:
    BaseModel();

    virtual ~BaseModel();

    virtual void draw(const BaseCamera &Cam);

    const Matrix &transform() const { return mTransform; }

    void transform(const Matrix &m) { mTransform = m; }

    virtual void shader(BaseShader *shader, bool deleteOnDestruction = false);

    virtual BaseShader *shader() const { return pShader; }

    virtual const AABB &boundingBox() const { return AABB::unitBox(); }

    bool shadowCaster() const { return mShadowCaster; }

    void shadowCaster(bool sc) { mShadowCaster = sc; }

    bool doDelete;
protected:
    Matrix mTransform;
    BaseShader *pShader;
    bool mDeleteShader;
    bool mShadowCaster;
};


#endif /* BaseModel_hpp */
