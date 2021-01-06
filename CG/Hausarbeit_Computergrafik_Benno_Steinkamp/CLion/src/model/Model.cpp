//
//  Model.cpp
//  ogl4
//
//  Created by Philipp Lensing on 21.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "Model.h"
#include "../shader/phongshader.h"
#include "../DebugRender.h"
#include <list>

Model::Model() : BaseModel(), pMeshes(NULL), mMeshCount(0), mMaterials(NULL), mMaterialCount(0) {

}

Model::Model(const char *ModelFile) : Model() {
    bool ret = load(ModelFile);
    if (!ret)
        throw std::exception();
}

Model::~Model() {
    delete[] pMeshes;
    delete[] mMaterials;
}

bool Model::load(const char *ModelFile) {
    const aiScene *pScene = aiImportFile(ModelFile, aiProcessPreset_TargetRealtime_Fast | aiProcess_TransformUVCoords);

    if (pScene == NULL || pScene->mNumMeshes <= 0)
        return false;

    mFilepath = ModelFile;
    mPath = mFilepath;
    size_t pos = mFilepath.rfind('/');
    if (pos == std::string::npos)
        pos = mFilepath.rfind('\\');
    if (pos != std::string::npos)
        mPath.resize(pos + 1);

    loadMeshes(pScene);
    loadMaterials(pScene);
    loadNodes(pScene);

    return true;
}

void Model::loadMeshes(const aiScene *pScene) {
    mMeshCount = pScene->mNumMeshes;
    pMeshes = new Mesh[mMeshCount];

    float scale = 1;
    Vector offset(0, 0, 0);
    calcBoundingBox(pScene, mBoundingBox);

    // Load Meshes
    for (unsigned int meshIndex = 0; meshIndex < pScene->mNumMeshes; meshIndex++) {
        aiMesh *pAiMesh = pScene->mMeshes[meshIndex];
        Mesh *pMesh = pMeshes + meshIndex;

        //Load Vertices
        pMesh->VB.begin();

        for (unsigned int vertexIndex = 0; vertexIndex < pAiMesh->mNumVertices; vertexIndex++) {
            aiVector3D *pVertex = pAiMesh->mVertices + vertexIndex;
            aiVector3D *pNormal = pAiMesh->mNormals + vertexIndex;
            aiVector3D *pTextCoord0 = pAiMesh->mTextureCoords[0] + vertexIndex;
            aiVector3D *pTextCoord1 = pAiMesh->mTextureCoords[1] + vertexIndex;
            aiVector3D *pTextCoord2 = pAiMesh->mTextureCoords[2] + vertexIndex;
            aiVector3D *pTextCoord3 = pAiMesh->mTextureCoords[3] + vertexIndex;

            Vector scaledVertex = (Vector(pVertex->x, pVertex->y, pVertex->z) + offset) * scale;

            pMesh->VB.addNormal(pNormal->x, pNormal->y, pNormal->z);
            if (pAiMesh->mTextureCoords[0] != nullptr) {
                pMesh->VB.addTexcoord0(pTextCoord0->x, 1 - pTextCoord0->y, pTextCoord0->z);
            }
            if (pAiMesh->mTextureCoords[1] != nullptr) {
                pMesh->VB.addTexcoord1(pTextCoord1->x, 1 - pTextCoord1->y, pTextCoord1->z);
            }
            if (pAiMesh->mTextureCoords[2] != nullptr) {
                pMesh->VB.addTexcoord2(pTextCoord2->x, 1 - pTextCoord2->y, pTextCoord2->z);
            }
            if (pAiMesh->mTextureCoords[3] != nullptr) {
                pMesh->VB.addTexcoord3(pTextCoord3->x, 1 - pTextCoord3->y, pTextCoord3->z);
            }
            pMesh->VB.addVertex(scaledVertex);

        }
        pMesh->VB.end();

        //Load Indices
        //printf("Loading %d Faces\n", pAiMesh->mNumFaces);
        pMesh->IB.begin();
        for (unsigned int faceIndex = 0; faceIndex < pAiMesh->mNumFaces; faceIndex++) {
            aiFace *pFace = &pAiMesh->mFaces[faceIndex];
            if (pFace->mNumIndices != 3) {
                printf("WARNING: Face %d has %d Indices\n", faceIndex, pFace->mNumIndices);
            }
            for (unsigned int indexIndex = 0; indexIndex < pFace->mNumIndices; indexIndex++) {
                pMesh->IB.addIndex(pFace->mIndices[indexIndex]);
            }
        }
        pMesh->IB.end();

        pMesh->MaterialIdx = pAiMesh->mMaterialIndex;
    }

}

void Model::loadMaterials(const aiScene *pScene) {
    //printf("Loading %d Materials\n", pScene->mNumMaterials);

    mMaterialCount = pScene->mNumMaterials;
    mMaterials = new Material[mMaterialCount];
    for (unsigned int materialIndex = 0; materialIndex < pScene->mNumMaterials; materialIndex++) {
        aiMaterial *pAiMaterial = pScene->mMaterials[materialIndex];
        Material *pMaterial = mMaterials + materialIndex;

        //printf("Getting Properties for Material %d\n", materialIndex);
        aiColor3D aiResultColor(0, 0, 0);

        if (pAiMaterial->Get(AI_MATKEY_COLOR_AMBIENT, aiResultColor) != aiReturn_SUCCESS) {
            //printf("Could not get Ambient Color\n");
        }
        pMaterial->mAmbColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

        if (pAiMaterial->Get(AI_MATKEY_COLOR_DIFFUSE, aiResultColor) != aiReturn_SUCCESS) {
            //printf("Could not get Diffuse Color\n");
        }
        pMaterial->mDiffColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

        if (pAiMaterial->Get(AI_MATKEY_COLOR_SPECULAR, aiResultColor) != aiReturn_SUCCESS) {
            //printf("Could not get Specular Color\n");
        }
        pMaterial->mSpecColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

        float specExp(1);
        if (pAiMaterial->Get(AI_MATKEY_SHININESS, specExp) != aiReturn_SUCCESS) {
            //printf("Could not get Specular Exponent\n");
        }
        pMaterial->mSpecExp = specExp;

        aiString textureAiString;
        std::string texturePath = mPath;
        if (aiGetMaterialString(pAiMaterial, AI_MATKEY_TEXTURE_DIFFUSE(0), &textureAiString) == aiReturn_SUCCESS) {
            printf("textureAiString(%s)\n",textureAiString.C_Str());
            pMaterial->pDiffTex = Texture::LoadShared(texturePath.c_str());
        }
    }
}

void Model::calcBoundingBox(const aiScene *pScene, AABB &Box) {
    Box.Max = Vector(-INFINITY, -INFINITY, -INFINITY);
    Box.Min = Vector(INFINITY, INFINITY, INFINITY);
    calcNodeBoundingBoxRecursive(pScene->mRootNode, pScene, Box);
}

void Model::loadNodes(const aiScene *pScene) {
    copyNodesRecursive(pScene->mRootNode, &mRootNode);
}

void Model::copyNodesRecursive(const aiNode *paiNode, Node *pNode) {
    pNode->mTransform = convert(paiNode->mTransformation);

    if (paiNode->mNumMeshes > 0) {
        pNode->mMeshCount = paiNode->mNumMeshes;
        pNode->mMeshes = new int[pNode->mMeshCount];
        for (unsigned int i = 0; i < pNode->mMeshCount; ++i)
            pNode->mMeshes[i] = (int) paiNode->mMeshes[i];
    }

    if (paiNode->mNumChildren <= 0)
        return;

    pNode->mChildCount = paiNode->mNumChildren;
    pNode->mChildren = new Node[pNode->mChildCount];
    for (unsigned int i = 0; i < paiNode->mNumChildren; ++i) {
        copyNodesRecursive(paiNode->mChildren[i], &(pNode->mChildren[i]));
        pNode->mChildren[i].pParent = pNode;
    }
}

void Model::applyMaterial(unsigned int index) {
    if (index >= mMaterialCount)
        return;

    PhongShader *pPhong = dynamic_cast<PhongShader *>(shader());
    if (!pPhong) {
        //std::cout << "Model::applyMaterial(): WARNING Invalid shader-type. Please apply PhongShader for rendering models.\n";
        return;
    }

    Material *pMat = &mMaterials[index];
    pPhong->ambientColor(pMat->mAmbColor);
    pPhong->diffuseColor(pMat->mDiffColor);
    pPhong->specularExp(pMat->mSpecExp);
    pPhong->specularColor(pMat->mSpecColor);
    pPhong->diffuseTexture(pMat->pDiffTex);
}

void Model::draw(const BaseCamera &Cam) {
    if (!pShader) {
        std::cout << "Model::draw() no shader found" << std::endl;
        return;
    }
    pShader->modelTransform(transform());

    std::list<Node *> DrawNodes;
    DrawNodes.push_back(&mRootNode);

    while (!DrawNodes.empty()) {
        Node *pNode = DrawNodes.front();

        if (pNode->pParent != nullptr)
            pNode->mGlobalTransform = pNode->pParent->mGlobalTransform * pNode->mTransform;
        else
            pNode->mGlobalTransform = transform() * pNode->mTransform;

        pShader->modelTransform(pNode->mGlobalTransform);

        for (unsigned int i = 0; i < pNode->mMeshCount; ++i) {
            Mesh &mesh = pMeshes[pNode->mMeshes[i]];
            mesh.VB.activate();
            mesh.IB.activate();
            applyMaterial(mesh.MaterialIdx);
            pShader->activate(Cam);
            glDrawElements(GL_TRIANGLES, mesh.IB.indexCount(), mesh.IB.indexFormat(), 0);
            mesh.IB.deactivate();
            mesh.VB.deactivate();
        }
        for (unsigned int i = 0; i < pNode->mChildCount; ++i)
            DrawNodes.push_back(&(pNode->mChildren[i]));

        DrawNodes.pop_front();
    }
}

Matrix Model::convert(const aiMatrix4x4 &m) {
    return Matrix(m.a1, m.a2, m.a3, m.a4,
                  m.b1, m.b2, m.b3, m.b4,
                  m.c1, m.c2, m.c3, m.c4,
                  m.d1, m.d2, m.d3, m.d4);
}

Matrix Model::getNodeGlobalTransform(const aiNode *pNode) {
    Matrix ret;
    ret.identity();
    auto *currNode = pNode;
    while (currNode) {
        ret = convert(currNode->mTransformation) * ret;
        currNode = currNode->mParent;
    }
    return ret;
}

void Model::calcNodeBoundingBox(const aiNode *pNode, const aiScene *pScene, AABB &Box) {
    Matrix nodeTransform = getNodeGlobalTransform(pNode);
    AABB nodeBox;
    nodeBox.Max = Vector(-INFINITY, -INFINITY, -INFINITY);
    nodeBox.Min = Vector(INFINITY, INFINITY, INFINITY);

    for (unsigned int meshIndex = 0; meshIndex < pNode->mNumMeshes; meshIndex++) {
        auto *pMesh = pScene->mMeshes[pNode->mMeshes[meshIndex]];
        for (unsigned int vertexIndex = 0; vertexIndex < pMesh->mNumVertices; vertexIndex++) {
            auto *pVertex = &pMesh->mVertices[vertexIndex];
            if (pVertex->x > nodeBox.Max.X) {
                nodeBox.Max.X = pVertex->x;
            }
            if (pVertex->y > nodeBox.Max.Y) {
                nodeBox.Max.Y = pVertex->y;
            }
            if (pVertex->z > nodeBox.Max.Z) {
                nodeBox.Max.Z = pVertex->z;
            }

            if (pVertex->x < nodeBox.Min.X) {
                nodeBox.Min.X = pVertex->x;
            }
            if (pVertex->y < nodeBox.Min.Y) {
                nodeBox.Min.Y = pVertex->y;
            }
            if (pVertex->z < nodeBox.Min.Z) {
                nodeBox.Min.Z = pVertex->z;
            }
        }
    }
    Box.merge(nodeBox.transform(nodeTransform));
}

void Model::calcNodeBoundingBoxRecursive(const aiNode *pNode, const aiScene *pScene, AABB &Box) {
    calcNodeBoundingBox(pNode, pScene, Box);
    for (unsigned int i = 0; i < pNode->mNumChildren; i++) {
        calcNodeBoundingBoxRecursive(pNode->mChildren[i], pScene, Box);
    }
}

Model::Node::~Node() {
    delete[] mMeshes;
    delete[] mChildren;
}
