//
// Created by benno on 19.08.2020.
//

#include "HitModel.h"
#include "../DebugRender.h"

#include <list>
#include <set>

HitModel::HitModel() : pMeshes(nullptr), MeshCount(0) {
    mTransform.identity();
}

HitModel::HitModel(const char *HitModelFile) : HitModel() {
    bool ret = load(HitModelFile);
    if (!ret)
        throw std::exception();
}


HitModel::~HitModel() {
    delete[] pMeshes;
    deleteNodes(&mRootNode);
}

void HitModel::deleteNodes(Node *pNode) {
    if (!pNode)
        return;
    for (unsigned int i = 0; i < pNode->mChildCount; ++i)
        deleteNodes(&(pNode->mChildren[i]));
    if (pNode->mChildCount > 0)
        delete[] pNode->mChildren;
    if (pNode->mMeshCount > 0)
        delete[] pNode->mMeshes;
}

bool HitModel::load(const char *ModelFile) {
    const aiScene *pScene = aiImportFile(ModelFile,
                                         aiProcessPreset_TargetRealtime_Fast | aiProcess_TransformUVCoords);

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
    loadNodes(pScene);

    return true;
}

void HitModel::loadMeshes(const aiScene *pScene) {
    MeshCount = pScene->mNumMeshes;
    pMeshes = new Mesh[MeshCount];

    // Load Meshes
    for (unsigned int meshIndex = 0; meshIndex < pScene->mNumMeshes; meshIndex++) {
        auto *pAiMesh = pScene->mMeshes[meshIndex];
        pMeshes[meshIndex] = Mesh();
        auto *pMesh = pMeshes + meshIndex;

        // Load Vertices
        for (unsigned int vertexIndex = 0; vertexIndex < pAiMesh->mNumVertices; vertexIndex++) {
            aiVector3D *pVertex = pAiMesh->mVertices + vertexIndex;
            pMesh->mVertices.push_back(Vector(pVertex->x, pVertex->y, pVertex->z));

        }
        // LoadIndeces
        for (unsigned int faceIndex = 0; faceIndex < pAiMesh->mNumFaces; faceIndex++) {
            aiFace *pFace = &pAiMesh->mFaces[faceIndex];
            if (pFace->mNumIndices != 3) {
                fprintf(stderr, "ERROR: Face %d has %d Indices\n", faceIndex, pFace->mNumIndices);
                throw std::exception();
            }
            pMesh->mIndices.emplace_back(pFace->mIndices[0], pFace->mIndices[1], pFace->mIndices[2]);
        }
    }
}

void HitModel::loadNodes(const aiScene *pScene) {
    deleteNodes(&mRootNode);
    copyNodesRecursive(pScene->mRootNode, &mRootNode);
}

void HitModel::copyNodesRecursive(const aiNode *paiNode, Node *pNode) {
    paiNode->mName.C_Str();
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


Matrix HitModel::convert(const aiMatrix4x4 &m) {
    return Matrix(m.a1, m.a2, m.a3, m.a4,
                  m.b1, m.b2, m.b3, m.b4,
                  m.c1, m.c2, m.c3, m.c4,
                  m.d1, m.d2, m.d3, m.d4);
}

bool HitModel::rayCast(const Vector &base, const Vector &direction, float &distance) {
    bool intersectedOne = false;

    std::list<Node *> nodes;
    nodes.push_back(&mRootNode);
    while (!nodes.empty()) {
        Node *pNode = nodes.front();
        if (pNode->pParent != NULL)
            pNode->mGlobalTransform = pNode->pParent->mGlobalTransform * pNode->mTransform;
        else
            pNode->mGlobalTransform = transform() * pNode->mTransform;

        for (unsigned int i = 0; i < pNode->mMeshCount; ++i) {
            auto *pMesh = pMeshes + pNode->mMeshes[i];
            unsigned int n = 0;
            for (auto &tri : pMesh->mIndices) {

                Vector vertex1 = pNode->mGlobalTransform.transformVec4x4(pMesh->mVertices[tri.i1]);
                Vector vertex2 = pNode->mGlobalTransform.transformVec4x4(pMesh->mVertices[tri.i2]);
                Vector vertex3 = pNode->mGlobalTransform.transformVec4x4(pMesh->mVertices[tri.i3]);
                float thisDist = 0;
                if (base.triangleIntersection(direction, vertex1, vertex2, vertex3, thisDist)) {
                    DebugRender::ref().drawLine(base,base+direction*10);
                    DebugRender::ref().drawLine(vertex1,vertex2);
                    DebugRender::ref().drawLine(vertex2,vertex3);
                    DebugRender::ref().drawLine(vertex3,vertex1);
                    intersectedOne = true;
                    if (thisDist < distance) {
                        distance = thisDist;
                    }
                }
            }
        }
        for (unsigned int i = 0; i < pNode->mChildCount; ++i)
            nodes.push_back(&(pNode->mChildren[i]));

        nodes.pop_front();
    }
    return intersectedOne;
}


