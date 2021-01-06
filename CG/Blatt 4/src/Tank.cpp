//
//  Tank.cpp
//  CGXcode
//
//  Created by Philipp Lensing on 16.11.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "Tank.h"
#define _USE_MATH_DEFINES
#include <math.h>

Tank::Tank() :Tank(10.0f, 1.0f)
{
}

Tank::Tank(float speed, float rotationSpeed) : BaseModel(), mSpeed(speed), mRotationSpeed(rotationSpeed), pChassisModel(nullptr), pCannonModel(nullptr) {

}



Tank::~Tank()
{
	delete pChassisModel;
	delete pCannonModel;
}

bool Tank::loadModels(const char* ChassisFile, const char* CannonFile)
{
	pChassisModel = new Model(ChassisFile, false);
	pCannonModel = new Model(CannonFile, false);
	if (pShader) {
		pChassisModel->shader(pShader, false);
		pCannonModel->shader(pShader, false);
	}
	return true;
}

void Tank::steer(float ForwardBackward, float LeftRight)
{
	mLeftRight = LeftRight;
	mForwardBackward = ForwardBackward;
}

void Tank::aim(const Vector& Target)
{
	mTarget = Target; 
}

void Tank::update(float dtime)
{
	Matrix forwardBackwardMatrix;
	forwardBackwardMatrix.translation(Vector(1, 0, 0) * mForwardBackward * dtime * mSpeed);


	Matrix leftRightMatrix;
	leftRightMatrix.rotationY(2 * M_PI * mLeftRight * dtime * mRotationSpeed);

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
	
	if (pCannonModel != nullptr) {
		pCannonModel->transform(transform()*aimMatrix);
	}
	if (pChassisModel != nullptr) {
		pChassisModel->transform(transform());
	}


}

void Tank::draw(const BaseCamera& Cam)
{
	BaseModel::draw(Cam);
	pChassisModel->draw(Cam);
	pCannonModel->draw(Cam);
}

void Tank::shader(BaseShader* shader, bool deleteOnDestruction)
{
	BaseModel::shader(shader, deleteOnDestruction);
	if (pChassisModel != nullptr) {
		pChassisModel->shader(shader, false);
	}
	if (pCannonModel != nullptr) {
		pCannonModel->shader(shader, false);
	}
}
