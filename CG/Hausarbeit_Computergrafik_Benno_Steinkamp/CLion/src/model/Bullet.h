//
// Created by benno on 23.08.2020.
//

#ifndef CLION_BULLET_H
#define CLION_BULLET_H


#include "Model.h"
#include "../Tank.h"
#include <list>

class Tank;

class Bullet : public Model {
public:
    Bullet();
    void speed(float speed);
    float speed();
    void addTarget(Tank *pTank);
    void update(float deltaTime);

    void draw(const BaseCamera &Cam) override;

protected:
    float mSpeed;
    std::list<Tank*> mTargetList;
    float mLifeTime;
    float mTimeAlive;
};


#endif //CLION_BULLET_H
