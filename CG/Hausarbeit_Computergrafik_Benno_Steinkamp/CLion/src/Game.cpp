
#include "Game.h"

#include <sstream>
#include <random>
#include <math.h>
#define _USE_MATH_DEFINES

#include "DIRECTORIES.h"
#include "shader/helper/ShaderLightMapper.h"
#include "ui/UIRect.h"
#include "shader/PhongShader.h"
#include "ui/Font.h"
#include "ui/UITextBox.h"
#include "DebugRender.h"
#include "model/SkyBox.h"

Game::Game(GLFWwindow *pWin) : pWindow(pWin), cam(pWin), ShadowGenerator(2048, 2048), width(640), height(480),
                               playBox(Vector(-30, 0, -20), Vector(30, 10, 20)), rng(std::random_device()()),
                               state(INITIAL) {

    rngX = std::uniform_real_distribution<float>(playBox.Min.X, playBox.Max.X);
    rngZ = std::uniform_real_distribution<float>(playBox.Min.Z, playBox.Max.Z);

    pPlayer = new Tank();
    pPlayer->loadModels(MODEL_DIRECTORY "player_body.dae", MODEL_DIRECTORY "player_head.dae",
                        MODEL_DIRECTORY "player_body_hitmodel.dae", MODEL_DIRECTORY "player_head_hitmodel.dae");
    modelList.push_back(pPlayer);
    pPlayer->shader(new PhongShader(), true);
    pEnemy = new Tank();
    pEnemy->loadModels(MODEL_DIRECTORY "enemy_body.dae", MODEL_DIRECTORY "enemy_head.dae",
                       MODEL_DIRECTORY "player_body_hitmodel.dae", MODEL_DIRECTORY "player_head_hitmodel.dae");
    pEnemy->shader(new PhongShader(), true);
    modelList.push_back(pEnemy);


    pBackground = new Model(MODEL_DIRECTORY "level.dae");
    pBackground->shader(new PhongShader(), true);
    pBackground->shadowCaster(false);
    modelList.push_back(pBackground);

    auto *dl = new DirectionalLight();
    dl->direction(Vector(-1, -1, 1));
    dl->color(Color(0.5, 0.5, 0.5));
    dl->castShadows(true);
    ShaderLightMapper::instance().addLight(dl);

    initUI();


    auto *skyBox = new SkyBox(TEXTURE_DIRECTORY"Yokohama2");
    skyBox->transform(Matrix().scale(500));
    modelList.push_back(skyBox);
}

void Game::start() {
    glEnable(GL_DEPTH_TEST); // enable depth-testing
    glDepthFunc(GL_LESS); // depth-testing interprets a smaller value as "closer"
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    DebugRender::ref().init();
}


void Game::update(float deltaTime) {
    switch (state) {
        case INITIAL:
            updateInitial();
            break;
        case PLAYING:
            updatePlayer(deltaTime);
            updateEnemy(deltaTime);
            updateBullets(deltaTime);
            updatePlaying();
            break;
        case PAUSED:
            updatePaused();
            break;
        case WON:
            updateWon();
            break;
        case LOST:
            updateLost();
            break;
    }
    updateModels();
    updateCam();
    updateUI();
}


void Game::draw() {
    ShadowGenerator.generate(modelList);

    // 1. clear screen
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);


    ShaderLightMapper::instance().activate();

    // 2. setup shaders and draw models
    for (auto &pModel : modelList) {
        pModel->draw(cam);
    }

    ShaderLightMapper::instance().deactivate();
    glDisable(GL_DEPTH_TEST); // enable depth-testing
    for (auto &pUIComponent : uiComponentList) {
        pUIComponent->draw();
    }
#ifdef DEBUG
    DebugRender::ref().render(cam);
#endif
    // 3. check once per frame for opengl errors

    GLenum Error = glGetError();
    if (Error != GL_NO_ERROR) {
        fprintf(stderr, "Error Code: %x\n", Error);
        exit(1);
    }
}

void Game::end() {
    for (auto &pModel : modelList)
        delete pModel;
    modelList.clear();

    for (auto &pUiComponent: uiComponentList)
        delete pUiComponent;
    uiComponentList.clear();
}

void Game::updatePlayer(float deltaTime) {
    float leftRight = 0;
    float forwardBackward = 0;

    int leftKey = glfwGetKey(pWindow, GLFW_KEY_LEFT);
    int rightKey = glfwGetKey(pWindow, GLFW_KEY_RIGHT);
    int forwardKey = glfwGetKey(pWindow, GLFW_KEY_UP);
    int backwardKey = glfwGetKey(pWindow, GLFW_KEY_DOWN);
    int fireKey = glfwGetMouseButton(pWindow, GLFW_MOUSE_BUTTON_LEFT);

    leftRight += leftKey;
    leftRight -= rightKey;
    forwardBackward += forwardKey;
    forwardBackward -= backwardKey;

    pPlayer->steer(forwardBackward, leftRight);


    int width;
    int height;
    glfwGetWindowSize(pWindow, &width, &height);

    double xPos;
    double yPos;
    glfwGetCursorPos(pWindow, &xPos, &yPos);

    float normalX = (2.0 * xPos / (double) width) - 1;
    float normalY = -((2.0 * yPos / (double) height) - 1);

    Vector origin;
    Vector direction = cam.calc3DRay(normalX, normalY, origin);

    float factor = -origin.Y / direction.Y;
    Vector resultPos = origin + (direction * factor);

    pPlayer->aim(resultPos);


    pPlayer->update(deltaTime);
    pPlayer->constrain(playBox);


    if (fireKey && pPlayer->canFire()) {
        auto *pBullet = pPlayer->fire();
        pBullet->addTarget(pPlayer);
        pBullet->addTarget(pEnemy);
        modelList.push_back(pBullet);
        bulletList.push_back(pBullet);
    }
}

void Game::updateCam() {
    cam.setPosition(Vector(0, 10, -25));
    cam.setTarget((pPlayer->transform().translation() + pEnemy->transform().translation()) * 0.5);
    cam.update();
}

void Game::updateModels() {
    auto modelIt = modelList.begin();
    while (modelIt != modelList.end()) {
        if ((*modelIt)->doDelete) {
            delete *modelIt;
            modelIt = modelList.erase(modelIt);
        } else {
            modelIt++;
        }
    }
}

void Game::updateBullets(float deltaTime) {
    auto bulletIt = bulletList.begin();
    while (bulletIt != bulletList.end()) {
        (*bulletIt)->update(deltaTime);
        if ((*bulletIt)->doDelete) {
            bulletIt = bulletList.erase(bulletIt);
        } else {
            bulletIt++;
        }
    }
}

void Game::updateUI() {

    std::stringstream stream;
    stream << "Player HP: " << pPlayer->getHP();
    playerHealthDisplay->setText(stream.str().c_str());
    stream.str("");
    stream << "Enemy HP: " << pEnemy->getHP();
    enemyHealthDisplay->setText(stream.str().c_str());
}

void Game::updateEnemy(float deltaTime) {
    pEnemy->aim(pPlayer->transform().translation());
    AABB enemyBox = pEnemy->boundingBox().transform(pEnemy->transform());
    Vector enemyTranslation = pEnemy->transform().translation();
    //DebugRender::ref().drawLine(enemyTranslation,enemyWaypoint);
    if (
            enemyWaypoint.X > enemyBox.Min.X &&
            enemyWaypoint.X < enemyBox.Max.X &&
            enemyWaypoint.Z > enemyBox.Min.Z &&
            enemyWaypoint.Z < enemyBox.Max.Z) {
        enemyWaypoint.X = rngX(rng);
        enemyWaypoint.Z = rngZ(rng);
    }
    float leftRight = -pEnemy->transform().forward().dot(enemyWaypoint - pEnemy->transform().translation());
    leftRight = leftRight >= 1 ? 1 : (leftRight <= -1 ? -1 : 0.0f); //
    pEnemy->steer(1, leftRight);
    pEnemy->transform().right();
    if (pEnemy->canFire()) {
        auto *pBullet = pEnemy->fire();
        pBullet->addTarget(pPlayer);
        pBullet->addTarget(pEnemy);
        modelList.push_back(pBullet);
        bulletList.push_back(pBullet);
    }
    pEnemy->update(deltaTime);
    pEnemy->constrain(playBox);
}

void Game::updateInitial() {
    int goButton = glfwGetKey(pWindow, GLFW_KEY_SPACE);
    if (goButton) {
        playerHealthDisplay->show();
        enemyHealthDisplay->show();
        initialDisplay->hide();
        setupTanks();
        state = PLAYING;
    }
}

void Game::updatePlaying() {
    if (pPlayer->getHP() == 0) {
        state = LOST;
        lostDisplay->show();
    } else if (pEnemy->getHP() == 0) {
        state = WON;
        wonDisplay->show();
    } else {
        int pauseButtonPressed = glfwGetKey(pWindow, GLFW_KEY_P);
        if (pauseButtonPressed) {
            state = PAUSED;
            playerHealthDisplay->hide();
            enemyHealthDisplay->hide();
        }
    }
}

void Game::updatePaused() {
    int unpauseButtonPressed = glfwGetKey(pWindow, GLFW_KEY_P);
    if (unpauseButtonPressed) {
        state = PLAYING;
        playerHealthDisplay->show();
        enemyHealthDisplay->show();
    }
}

void Game::updateWon() {
    int restartButtonPressed = glfwGetKey(pWindow, GLFW_KEY_SPACE);
    if (restartButtonPressed) {
        state = INITIAL;
        wonDisplay->hide();
        playerHealthDisplay->hide();
        enemyHealthDisplay->hide();
        initialDisplay->show();
        for (auto *pBullet:bulletList)
            pBullet->doDelete = true;
        bulletList.clear();
    }
}

void Game::updateLost() {
    int restartButtonPressed = glfwGetKey(pWindow, GLFW_KEY_SPACE);
    if (restartButtonPressed) {
        state = INITIAL;
        lostDisplay->hide();
        playerHealthDisplay->hide();
        enemyHealthDisplay->hide();
        initialDisplay->show();
        for (auto *pBullet:bulletList)
            pBullet->doDelete = true;
        bulletList.clear();
    }
}

void Game::setupTanks() {
    pPlayer->transform( Matrix().translation(20, 0, 0) * Matrix().rotationY(M_PI));
    pPlayer->resetHp();

    enemyWaypoint = Vector(-20, 0, 0);
    pEnemy->transform(Matrix().translation(enemyWaypoint));
    pEnemy->resetHp();
}

void Game::initUI() {
    pFont = new Font(FONT_DIRECTORY"Roboto_Mono/RobotoMono-Medium.fnt");

    playerHealthDisplay = new UITextBox(width, height, 0, 0, 200, pFont->getLineHeight(), pFont);
    playerHealthDisplay->color(Color(0, 0, 0));
    playerHealthDisplay->hide();
    uiComponentList.push_back(playerHealthDisplay);

    enemyHealthDisplay = new UITextBox(width, height, 0, pFont->getLineHeight(), 200, pFont->getLineHeight(), pFont);
    enemyHealthDisplay->color(Color(0, 0, 0));
    enemyHealthDisplay->hide();
    uiComponentList.push_back(enemyHealthDisplay);

    initialDisplay = new UITextBox(width, height, 0, height / 2 - (pFont->getLineHeight() * 2), width,
                                   pFont->getLineHeight() * 4, pFont);
    initialDisplay->setText(
            "Welcome to TankGame!\nPress Space to Start the Game.\nAim using the Mouse.\nPress Left Mouse Button to Shoot.");
    initialDisplay->drawBackground = true;
    initialDisplay->color(Color(0, 0, 0));
    uiComponentList.push_back(initialDisplay);

    wonDisplay = new UITextBox(width, height, 0, height / 2 - (pFont->getLineHeight() / 2), width,
                               pFont->getLineHeight(), pFont);
    wonDisplay->setText("You Won!");
    wonDisplay->color(Color(0, 0, 0));
    wonDisplay->hide();
    uiComponentList.push_back(wonDisplay);

    lostDisplay = new UITextBox(width, height, 0, height / 2 - pFont->getLineHeight() / 2, width,
                                pFont->getLineHeight(), pFont);
    lostDisplay->setText("You Lost!");
    lostDisplay->color(Color(0, 0, 0));
    lostDisplay->hide();
    uiComponentList.push_back(lostDisplay);
}


