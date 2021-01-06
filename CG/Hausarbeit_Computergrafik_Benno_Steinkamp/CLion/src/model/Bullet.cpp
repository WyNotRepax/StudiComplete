//
// Created by benno on 23.08.2020.
//

#include "Bullet.h"
#include "../DIRECTORIES.h"
#include "../DebugRender.h"

Bullet::Bullet() : mSpeed(0.0f), mLifeTime(10), mTimeAlive(0) {
    shadowCaster(false);
    load(MODEL_DIRECTORY"bullet.dae");
}

void Bullet::speed(float speed) {
    mSpeed = speed;
}

float Bullet::speed() {
    return mSpeed;
}

void Bullet::addTarget(Tank *pTank) {
    if (pTank)
        mTargetList.push_back(pTank);
}

void Bullet::update(float deltaTime) {
    mTimeAlive += deltaTime;
    if (mTimeAlive > mLifeTime) {
        doDelete = true;
    }
    for (auto *pTank: mTargetList) {
        float dist = INFINITY;
        if (pTank->rayCast(transform().translation(), transform().right(), dist)) {
            if (dist < mSpeed * deltaTime) {
                pTank->hit();
                doDelete = true;
            }
        }
        DebugRender::ref().drawLine(transform().translation(), transform().translation() + transform().right() * dist);
    }
    transform(transform() * Matrix().translation(mSpeed * deltaTime, 0, 0));
}

void Bullet::draw(const BaseCamera &Cam) {
    Model::draw(Cam);
}



