//
//  Tank.cpp
//  CGXcode
//
//  Created by Philipp Lensing on 16.11.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "Tank.h"
#include "DebugRender.h"
#include "shader/PhongShader.h"

#define _USE_MATH_DEFINES

#include <math.h>

Tank::Tank() : Tank(10.0f, 1.0f, 1.0f, 1.0f) {
}

Tank::Tank(float maxSpeed, float acceleration, float rotationSpeed, float fireRate) :
        BaseModel(),
        mTarget(10, 0, 0),
        mSpeed(maxSpeed), mRotationSpeed(rotationSpeed),
        currSpeed(0.0f), mAcceleration(acceleration),
        pBodyModel(nullptr), pHeadModel(nullptr),
        pBodyHitModel(nullptr), pHeadHitModel(nullptr),
        mTimeSinceShot(1.0f / fireRate + 1.0f), mFireRate(1.0f / fireRate),
        pBulletShader(new PhongShader()),mForwardBackward(0) {
    resetHp();
}


Tank::~Tank() {
    delete pBodyModel;
    delete pHeadModel;
    delete pBodyHitModel;
    delete pHeadHitModel;
    if (mDeleteShader)
        delete pShader;
    delete pBulletShader;
}

bool Tank::loadModels(const char *BodyModelFile, const char *HeadModelFile, const char *BodyHitModelFile,
                      const char *HeadHitModelFile) {
    pBodyModel = new Model(BodyModelFile);
    pHeadModel = new Model(HeadModelFile);
    pBodyHitModel = new HitModel(BodyHitModelFile);
    pHeadHitModel = new HitModel(HeadHitModelFile);

    mBoundingBox = pBodyModel->boundingBox();

    if (pShader) {
        pBodyModel->shader(pShader, false);
        pHeadModel->shader(pShader, false);
    }
    return true;
}

void Tank::steer(float ForwardBackward, float LeftRight) {
    mLeftRight = LeftRight;
    mForwardBackward = ForwardBackward;
}

void Tank::aim(const Vector &Target) {
    Matrix invMatrix = transform();
    invMatrix.invert();
    mTarget = (mTarget * 0.9f + Target * 0.1f);
}

void Tank::update(float deltaTime) {
    Matrix forwardBackwardMatrix;
    if (abs(currSpeed) < mSpeed) {
        currSpeed += mForwardBackward * mAcceleration;
    }
    if (abs(currSpeed) < 1) {
        currSpeed = 0;
    }
    if (mForwardBackward == 0) {
        currSpeed *= 0.9f;
    }
    forwardBackwardMatrix.translation(Vector(1, 0, 0) * currSpeed * deltaTime);


    Matrix leftRightMatrix;
    leftRightMatrix.rotationY(2 * M_PI * mLeftRight * deltaTime * mRotationSpeed);

    transform(transform() * forwardBackwardMatrix * leftRightMatrix);

    Matrix invMatrix = transform();
    invMatrix.invert();
    Vector relTarget = invMatrix.transformVec4x4(mTarget);
    relTarget.normalize();

    Matrix aimMatrix;
    aimMatrix.identity();
    aimMatrix.m00 = aimMatrix.m22 = relTarget.X;
    aimMatrix.m02 = -relTarget.Z;
    aimMatrix.m20 = relTarget.Z;
    if (pHeadModel != nullptr) {
        pHeadModel->transform(transform() * aimMatrix);
    }
    if (pHeadHitModel) {
        pHeadHitModel->transform(transform() * aimMatrix);
    }
    if (pBodyModel != nullptr) {
        pBodyModel->transform(transform());
    }
    if (pBodyHitModel != nullptr) {
        pBodyHitModel->transform(transform());
    }
    mBodyBoundingBox = pBodyModel->boundingBox();
    mBoundingBox = pHeadModel->boundingBox().transform(aimMatrix);
    mBoundingBox.merge(mBodyBoundingBox);

    mTimeSinceShot += deltaTime;
}

void Tank::draw(const BaseCamera &cam) {
    BaseModel::draw(cam);
    pBodyModel->draw(cam);
    pHeadModel->draw(cam);
}

void Tank::shader(BaseShader *shader, bool deleteOnDestruction) {
    BaseModel::shader(shader, deleteOnDestruction);
    if (pBodyModel != nullptr) {
        pBodyModel->shader(shader, false);
    }
    if (pHeadModel != nullptr) {
        pHeadModel->shader(shader, false);
    }
}

bool Tank::rayCast(const Vector &base, const Vector &direction, float &distance) {
    bool bodyHit = pBodyHitModel->rayCast(base, direction, distance);
    bool headHit = pHeadHitModel->rayCast(base, direction, distance);
    return bodyHit || headHit;
}

const AABB &Tank::boundingBox() const {
    return mBoundingBox;
}

const AABB &Tank::bodyBoundingBox() {
    return mBodyBoundingBox;
}


Bullet *Tank::fire() {
    mTimeSinceShot = 0;
    auto *pBullet = new Bullet();
    pBullet->shader(pBulletShader, false);
    pBullet->speed(15);
    pBullet->transform(pHeadModel->transform() * Matrix().translation(2.3, 1.5, 0));
    return pBullet;
}

bool Tank::canFire() {
    return mTimeSinceShot > mFireRate;
}

void Tank::hit() {
    mHP --;
}

int Tank::getHP() {
    return mHP;
}

void Tank::constrain(const AABB &box) {
    AABB currAABB = bodyBoundingBox().transform(transform());
    Vector translate = Vector(0, 0, 0);
    if(currAABB.Min.X < box.Min.X){
        translate.X = box.Min.X - currAABB.Min.X;
    }else if(currAABB.Max.X > box.Max.X){
        translate.X = box.Max.X - currAABB.Max.X;
    }
    if(currAABB.Min.Z < box.Min.Z){
        translate.Z = box.Min.Z - currAABB.Min.Z;
    }else if(currAABB.Max.Z > box.Max.Z){
        translate.Z = box.Max.Z - currAABB.Max.Z;
    }
    transform(Matrix().translation(translate) * transform());
}

void Tank::resetHp() {
    mHP = 10;
}
