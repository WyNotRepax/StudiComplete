//
//  Model.hpp
//  ogl4
//
//  Created by Philipp Lensing on 21.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#ifndef Model_hpp
#define Model_hpp

#include <stdio.h>
#include "basemodel.h"
#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include "../helper/VertexBuffer.h"
#include "../helper/IndexBuffer.h"
#include "../texture.h"
#include "../helper/Aabb.h"
#include <string>

class Model : public BaseModel {
public:
    Model();

    Model(const char *ModelFile);

    ~Model() override;

    bool load(const char *ModelFile);

    void draw(const BaseCamera &Cam) override;

    const AABB &boundingBox() const override { return mBoundingBox; }

protected: // protected types
    struct Mesh {
        Mesh() {}

        VertexBuffer VB;
        IndexBuffer IB;
        int MaterialIdx;
    };

    struct Material {
        Material() : pDiffTex(nullptr), mDiffColor(1, 1, 1), mSpecColor(0.3f, 0.3f, 0.3f), mAmbColor(0, 0, 0),
                     mSpecExp(10) {}

        Color mDiffColor;
        Color mSpecColor;
        Color mAmbColor;
        float mSpecExp;
        const Texture *pDiffTex;
    };

    struct Node {
        Node() : pParent(nullptr), mChildren(nullptr), mChildCount(0), mMeshCount(0), mMeshes(nullptr) {}

        ~Node();

        Matrix mTransform;
        Matrix mGlobalTransform;
        int *mMeshes;
        unsigned int mMeshCount;
        Node *pParent;
        Node *mChildren;
        unsigned int mChildCount;
    };

protected: // protected methods
    void loadMeshes(const aiScene *pScene);

    void loadMaterials(const aiScene *pScene);

    void calcBoundingBox(const aiScene *pScene, AABB &Box);

    void calcNodeBoundingBox(const aiNode *pNode, const aiScene *pScene, AABB &Box);

    void calcNodeBoundingBoxRecursive(const aiNode *pNode, const aiScene *pScene, AABB &Box);

    Matrix getNodeGlobalTransform(const aiNode *pNode);

    void loadNodes(const aiScene *pScene);

    void copyNodesRecursive(const aiNode *paiNode, Node *pNode);

    Matrix convert(const aiMatrix4x4 &m);

    void applyMaterial(unsigned int index);


protected: // protected member variables
    Mesh *pMeshes;
    unsigned int mMeshCount;
    Material *mMaterials;
    unsigned int mMaterialCount;

    AABB mBoundingBox;

    std::string mFilepath; // stores pathname and filename
    std::string mPath; // stores path without filename
    Node mRootNode;

};

#endif /* Model_hpp */
