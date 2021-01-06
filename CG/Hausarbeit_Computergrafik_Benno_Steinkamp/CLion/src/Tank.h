//
//  Tank.hpp
//  CGXcode
//
//  Created by Philipp Lensing on 16.11.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#ifndef Tank_hpp
#define Tank_hpp

#include <stdio.h>
#include <list>
#include "model/Model.h"
#include "model/HitModel.h"
#include "helper/Lights.h"
#include "model/Bullet.h"

class Bullet;

class Tank : public BaseModel {
public:
    Tank();

    Tank(float maxSpeed, float acceleration, float rotationSpeed, float fireRate);

    virtual ~Tank();

    bool loadModels(const char *BodyModelFile, const char *HeadModelFile, const char *BodyHitModelFile,
                    const char *HeadHitModelFile);

    void steer(float ForwardBackward, float LeftRight);

    void aim(const Vector &Target);

    void update(float deltaTime);

    virtual void draw(const BaseCamera &cam);

    virtual void shader(BaseShader *shader, bool deleteOnDestruction = false);

    bool rayCast(const Vector &base, const Vector &direction, float &distance);

    void constrain(const AABB &box);

    void hit();

    int getHP();

    void resetHp();

    const AABB &boundingBox() const override;

    const AABB &bodyBoundingBox();

    bool canFire();

    Bullet *fire();


protected:
    Model *pBodyModel;
    Model *pHeadModel;
    HitModel *pBodyHitModel;
    HitModel *pHeadHitModel;

    float mLeftRight;
    float mForwardBackward;
    float mSpeed;
    float mAcceleration;
    float mRotationSpeed;
    float currSpeed;
    float mFireRate;

    float mTimeSinceShot;
    int mHP;

    Vector mTarget;

    AABB mBoundingBox;
    AABB mBodyBoundingBox;

    BaseShader *pBulletShader;
};

#endif /* Tank_hpp */
