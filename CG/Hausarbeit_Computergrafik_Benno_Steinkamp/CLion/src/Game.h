//
//  Game.hpp
//  ogl4
//
//  Created by Philipp Lensing on 16.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#ifndef Application_hpp
#define Application_hpp

#ifdef WIN32

#include <GL/glew.h>
#include <glfw/glfw3.h>

#else
#define GLFW_INCLUDE_GLCOREARB
#define GLFW_INCLUDE_GLEXT
#include <glfw/glfw3.h>
#endif

#include <list>
#include <random>
#include "helper/Camera.h"
#include "model/BaseModel.h"
#include "Tank.h"
#include "ui/BaseUIComponent.h"
#include "ui/Font.h"
#include "model/HitModel.h"
#include "shader/helper/ShadowMapGenerator.h"
#include "ui/UITextBox.h"

class Game {
public:
    typedef std::list<BaseModel *> ModelList;
    typedef std::list<BaseUIComponent *> UIComponentList;

    Game(GLFWwindow *pWin);

    void start();

    void update(float deltaTime);

    void draw();

    void end();


protected:
    enum STATE {
        INITIAL, PLAYING, PAUSED, WON, LOST
    };
    STATE state;

    GLFWwindow *pWindow;

    int width;
    int height;

    Camera cam;

    Tank *pPlayer;
    Tank *pEnemy;
    Vector enemyWaypoint;

    std::default_random_engine rng;
    std::uniform_real_distribution<float> rngX;
    std::uniform_real_distribution<float> rngZ;

    Model *pBackground;

    ShadowMapGenerator ShadowGenerator;

    ModelList modelList;
    std::list<Bullet *> bulletList;
    AABB playBox;

    Font *pFont;
    UIComponentList uiComponentList;
    UITextBox *playerHealthDisplay;
    UITextBox *enemyHealthDisplay;
    UITextBox *initialDisplay;
    UITextBox *lostDisplay;
    UITextBox *wonDisplay;

    void setupTanks();

    void initUI();

    void updateInitial();

    void updatePlaying();

    void updatePaused();

    void updateWon();

    void updateLost();

    void updatePlayer(float deltaTime);

    void updateEnemy(float deltaTime);

    void updateCam();

    void updateBullets(float deltaTime);

    void updateModels();

    void updateUI();
};

#endif /* Application_hpp */
