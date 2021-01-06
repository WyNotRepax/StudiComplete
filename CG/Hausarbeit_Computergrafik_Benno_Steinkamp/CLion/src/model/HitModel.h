//
// Created by benno on 19.08.2020.
//

#ifndef CLION_HITMODEL_H
#define CLION_HITMODEL_H

#include "../helper/Camera.h"
#include "../helper/Color.h"
#include <vector>
#include <set>
#include <string>
#include <assimp/cimport.h>
#include <assimp/scene.h>
#include <assimp/postprocess.h>

class HitModel {
public:

    HitModel();

    HitModel(const char *ModelFile);

    bool load(const char *ModelFile);

    virtual ~HitModel();

    bool rayCast(const Vector &base, const Vector &direction, float &distance);

    const Matrix &transform() const { return mTransform; }

    void transform(const Matrix &m) { mTransform = m; }

protected: // protected types

    struct Tri {
        Tri(unsigned int i1, unsigned int i2, unsigned int i3) : i1(i1), i2(i2), i3(i3) {};
        unsigned int i1;
        unsigned int i2;
        unsigned int i3;
    };

    struct Mesh {
        Mesh() = default;

        std::vector<Vector> mVertices;
        std::vector<Tri> mIndices;
    };


    struct Node {
        Node() : pParent(nullptr), mChildren(nullptr), mChildCount(0), mMeshCount(0), mMeshes(nullptr) {}

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

    void loadNodes(const aiScene *pScene);

    void copyNodesRecursive(const aiNode *paiNode, Node *pNode);

    Matrix convert(const aiMatrix4x4 &m);

    void deleteNodes(Node *pNode);


protected: // protected member variables
    Mesh *pMeshes;
    unsigned int MeshCount;

    std::string mFilepath; // stores pathname and filename
    std::string mPath; // stores path without filename
    Node mRootNode;
    Matrix mTransform;
};

#endif //CLION_HITMODEL_H
