//
//  Application.cpp
//  ogl4
//
//  Created by Philipp Lensing on 16.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "Application.h"
#ifdef WIN32
#include <GL/glew.h>
#include <glfw/glfw3.h>
#else
#define GLFW_INCLUDE_GLCOREARB
#define GLFW_INCLUDE_GLEXT
#include <glfw/glfw3.h>
#endif
#include "lineplanemodel.h"
#include "triangleplanemodel.h"
#include "trianglespheremodel.h"
#include "lineboxmodel.h"
#include "triangleboxmodel.h"
#include "model.h"
#include "scene.h"
#define _USE_MATH_DEFINES
#include "math.h"


#ifdef WIN32
#define ASSET_DIRECTORY "../../assets/"
#else
#define ASSET_DIRECTORY "../assets/"
#endif


Application::Application(GLFWwindow* pWin) : pWindow(pWin), Cam(pWin)
{
	BaseModel* pModel;
	ConstantShader* pConstShader;
	PhongShader* pPhongShader;

	// create LineGrid model with constant color shader
	pModel = new LinePlaneModel(10, 10, 10, 10);
	pConstShader = new ConstantShader();
	pConstShader->color(Color(1, 1, 1));
	pModel->shader(pConstShader, true);
	Models.push_back(pModel);

	// Exercise 1
	/*
	pPhongShader = new PhongShader();

	pTankTop = new Model(ASSET_DIRECTORY "tank_top.dae", false);
	pTankTop->shader(pPhongShader);
	Models.push_back(pTankTop);

	pTankBot = new Model(ASSET_DIRECTORY "tank_bottom.dae", false);
	pTankBot->shader(pPhongShader);
	Models.push_back(pTankBot);
	*/
	// Exercise 2

	pPhongShader = new PhongShader();
	pTank = new Tank();
	pTank->shader(pPhongShader, true);
	pTank->loadModels(ASSET_DIRECTORY "player2.dae", ASSET_DIRECTORY "tank_top.dae");
	Models.push_back(pTank);


	// Exercise 3
	/*
	Scene* pScene = new Scene();
	pScene->shader(new PhongShader(), true);
	pScene->addSceneFile(ASSET_DIRECTORY "scene.osh");
	Models.push_back(pScene);
	*/



}
void Application::start()
{
	glEnable(GL_DEPTH_TEST); // enable depth-testing
	glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
	glEnable(GL_CULL_FACE);
	glCullFace(GL_BACK);
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
}


void Application::update(float deltatime)
{
	// Exercise 1

	if (pTankTop != nullptr && pTankBot != nullptr) {
		static float angle = 0;
		angle += (2 * M_PI / 6) * deltatime;
		//printf("Deltatime: %f\nFramerate:%f\nAngle:%f\n", deltatime, 1 / deltatime, angle);

		Matrix faceZ;
		faceZ.rotationY(-M_PI_2);

		Matrix translation;
		translation.translation(4, 0, 0);

		Matrix rotation;
		rotation.rotationY(-angle);

		Matrix counterRotation;
		counterRotation.rotationY(angle);

		pTankTop->transform(rotation * translation * counterRotation * faceZ);
		pTankBot->transform(rotation * translation * faceZ);
	}

	// Exercise 2
	if (pTank != nullptr) {
		float leftRight = 0;
		float forwardBackward = 0;

		int leftKey = glfwGetKey(pWindow, GLFW_KEY_LEFT);
		int rightKey = glfwGetKey(pWindow, GLFW_KEY_RIGHT);
		int forwardKey = glfwGetKey(pWindow, GLFW_KEY_UP);
		int backwardKey = glfwGetKey(pWindow, GLFW_KEY_DOWN);

		leftRight += leftKey;
		leftRight -= rightKey;
		forwardBackward += forwardKey;
		forwardBackward -= backwardKey;

		pTank->steer(forwardBackward, leftRight);


		int width;
		int height;
		glfwGetWindowSize(pWindow, &width, &height);

		double xpos;
		double ypos;
		glfwGetCursorPos(pWindow, &xpos, &ypos);

		float normalX = (2.0 * xpos / (double)width) - 1;
		float normalY = -((2.0 * ypos / (double)height) - 1);

		Vector origin;
		Vector direction = calc3DRay(normalX, normalY, origin);

		float faktor = -origin.Y / direction.Y;
		Vector resultPos = origin + (direction * faktor);

		pTank->aim(resultPos);

		pTank->update(deltatime);

	}
	Cam.update();
}

Vector Application::calc3DRay(float x, float y, Vector& Pos)
{
	Matrix invViewMatrix = Cam.getViewMatrix();
	invViewMatrix.invert();


	Matrix invProjectionMatrix = Cam.getProjectionMatrix();
	invProjectionMatrix.invert();

	Vector direc = invProjectionMatrix.transformVec4x4(Vector(x, y, 0));

	Pos = invViewMatrix.translation();
	return invViewMatrix.transformVec3x3(direc);
}

void Application::draw()
{
	// 1. clear screen
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// 2. setup shaders and draw models
	for (ModelList::iterator it = Models.begin(); it != Models.end(); ++it)
	{
		(*it)->draw(Cam);
	}

	// 3. check once per frame for opengl errors
	GLenum Error = glGetError();
	assert(Error == 0);
}
void Application::end()
{
	for (ModelList::iterator it = Models.begin(); it != Models.end(); ++it)
		delete* it;

	Models.clear();
}