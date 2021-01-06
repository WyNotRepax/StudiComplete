//
// Created by benno on 21.08.2020.
//

#include <FreeImage.h>
#include <cstring>
#include "SkyBox.h"
#include "../shader/SkyBoxShader.h"

void SkyBox::draw(const BaseCamera &Cam) {
    pShader->modelTransform(transform());
    pShader->activate(Cam);
    glDepthMask(GL_FALSE);
    mVertexBuffer.activate();
    mIndexBuffer.activate();
    pShader->activate(Cam);
    glDrawElements(GL_TRIANGLES, mIndexBuffer.indexCount(), mIndexBuffer.indexFormat(), 0);
    mIndexBuffer.deactivate();
    mVertexBuffer.deactivate();
    glDepthMask(GL_TRUE);
}

SkyBox::SkyBox(const char *dirName) : BaseModel() {
    shadowCaster(false);
    std::vector<std::string> faces =
            {
                    "posx.jpg",
                    "negx.jpg",
                    "posy.jpg",
                    "negy.jpg",
                    "posz.jpg",
                    "negz.jpg"
            };
    if (!load(dirName, faces)) {
        throw std::exception();
    }
    auto *pSkyBoxShader = new SkyBoxShader();
    pSkyBoxShader->texture(mTextureID);
    pShader = pSkyBoxShader;
    mDeleteShader = true;
    initVB();
    initIB();
}

bool SkyBox::load(const char *dirName, std::vector<std::string> faces) {
    glGenTextures(1, &mTextureID);
    glBindTexture(GL_TEXTURE_CUBE_MAP, mTextureID);

    for (unsigned int i = 0; i < faces.size(); i++) {
        std::string Filename = dirName;
        Filename.append("/");
        Filename.append(faces.at(i));

        unsigned char *data = nullptr;
        unsigned int width;
        unsigned int height;
        load(Filename.c_str(), &data, width, height);
        if (data) {
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0,GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
            delete[]data;
        } else {
            std::cout << "Cubemap tex failed to load at path: " << dirName << "/" << faces[i] << std::endl;
            delete[]data;
        }
    }

    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

    return mTextureID;
}

bool SkyBox::load(const char *Filename, unsigned char **data, unsigned int &width, unsigned int &height) {

    FREE_IMAGE_FORMAT ImageFormat = FreeImage_GetFileType(Filename, 0);
    if (ImageFormat == FIF_UNKNOWN)
        ImageFormat = FreeImage_GetFIFFromFilename(Filename);

    if (ImageFormat == FIF_UNKNOWN) {
        std::cout << "Warning: Unkown pTexture format: " << Filename << std::endl;
        return false;
    }
    FIBITMAP *pBitmap = FreeImage_Load(ImageFormat, Filename);

    if (pBitmap == NULL) {
        std::cout << "Warning: Unable to open Skybox image " << Filename << std::endl;
        return false;
    }

    FREE_IMAGE_TYPE Type = FreeImage_GetImageType(pBitmap);
    assert(Type == FIT_BITMAP);

    width = FreeImage_GetWidth(pBitmap);
    height = FreeImage_GetHeight(pBitmap);
    unsigned int bpp = FreeImage_GetBPP(pBitmap);
    assert(bpp == 32 || bpp == 16 || bpp == 24);

    *data = new unsigned char[width * height * 4];
    unsigned char *dataPtr = *data - 1;


    RGBQUAD c;
    for (unsigned int i = 0; i < height; ++i)
        for (unsigned int j = 0; j < width; ++j) {
            FreeImage_GetPixelColor(pBitmap, j, height - i - 1, &c);
            *(++dataPtr) = c.rgbRed;
            *(++dataPtr) = c.rgbGreen;
            *(++dataPtr) = c.rgbBlue;
            if (bpp == 32)
                *(++dataPtr) = c.rgbReserved;
            else
                *(++dataPtr) = 255;
        }

    FreeImage_Unload(pBitmap);
    return true;

}

void SkyBox::initVB() {
    mVertexBuffer.begin();
    mVertexBuffer.addVertex(1, 1, 1);
    mVertexBuffer.addVertex(-1, 1, 1);
    mVertexBuffer.addVertex(1, -1, 1);
    mVertexBuffer.addVertex(-1, -1, 1);
    mVertexBuffer.addVertex(1, 1, -1);
    mVertexBuffer.addVertex(-1, 1, -1);
    mVertexBuffer.addVertex(1, -1, -1);
    mVertexBuffer.addVertex(-1, -1, -1);
    mVertexBuffer.end();
}

void SkyBox::initIB() {
    mIndexBuffer.begin();

    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(1);
    mIndexBuffer.addIndex(0);
    mIndexBuffer.addIndex(1);
    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(3);

    mIndexBuffer.addIndex(5);
    mIndexBuffer.addIndex(4);
    mIndexBuffer.addIndex(0);
    mIndexBuffer.addIndex(0);
    mIndexBuffer.addIndex(1);
    mIndexBuffer.addIndex(5);

    mIndexBuffer.addIndex(4);
    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(0);
    mIndexBuffer.addIndex(2);
    mIndexBuffer.addIndex(4);
    mIndexBuffer.addIndex(6);

    mIndexBuffer.addIndex(5);
    mIndexBuffer.addIndex(1);
    mIndexBuffer.addIndex(3);
    mIndexBuffer.addIndex(5);
    mIndexBuffer.addIndex(3);
    mIndexBuffer.addIndex(7);

    mIndexBuffer.addIndex(6);
    mIndexBuffer.addIndex(4);
    mIndexBuffer.addIndex(5);
    mIndexBuffer.addIndex(6);
    mIndexBuffer.addIndex(5);
    mIndexBuffer.addIndex(7);


    mIndexBuffer.addIndex(7);
    mIndexBuffer.addIndex(3);
    mIndexBuffer.addIndex(6);
    mIndexBuffer.addIndex(6);
    mIndexBuffer.addIndex(3);
    mIndexBuffer.addIndex(2);

    mIndexBuffer.end();
}

