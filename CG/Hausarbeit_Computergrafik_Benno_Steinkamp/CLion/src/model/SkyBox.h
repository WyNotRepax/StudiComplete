//
// Created by benno on 21.08.2020.
//

#ifndef CLION_SKYBOX_H
#define CLION_SKYBOX_H


#include "BaseModel.h"
#include "../helper/VertexBuffer.h"
#include "../helper/IndexBuffer.h"
#include "../Texture.h"

class SkyBox : public BaseModel {
public:
    SkyBox(const char *dirName);

    void draw(const BaseCamera &Cam) override;

protected: // protected methods
    bool load(const char *dirName, std::vector<std::string> faces);

    bool load(const char *Filename, unsigned char **data, unsigned int &width, unsigned int &height);

    void initVB();

    void initIB();

protected: // protected member variables
    VertexBuffer mVertexBuffer;
    IndexBuffer mIndexBuffer;
    GLuint mTextureID;
};


#endif //CLION_SKYBOX_H
