//
//  Model.cpp
//  ogl4
//
//  Created by Philipp Lensing on 21.09.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "Model.h"
#include "phongshader.h"
#include <list>

Model::Model() : pMeshes(NULL), MeshCount(0), pMaterials(NULL), MaterialCount(0)
{

}
Model::Model(const char* ModelFile, bool FitSize) : pMeshes(NULL), MeshCount(0), pMaterials(NULL), MaterialCount(0)
{
	bool ret = load(ModelFile,FitSize);
	if (!ret)
		throw std::exception();
}
Model::~Model()
{
	delete[] pMeshes;
	delete[] pMaterials;
	deleteNodes(&RootNode);
}

void Model::deleteNodes(Node* pNode)
{
	if (!pNode)
		return;
	for (unsigned int i = 0; i < pNode->ChildCount; ++i)
		deleteNodes(&(pNode->Children[i]));
	if (pNode->ChildCount > 0)
		delete[] pNode->Children;
	if (pNode->MeshCount > 0)
		delete[] pNode->Meshes;
}

bool Model::load(const char* ModelFile, bool FitSize)
{
	const aiScene* pScene = aiImportFile(ModelFile, aiProcessPreset_TargetRealtime_Fast | aiProcess_TransformUVCoords);

	if (pScene == NULL || pScene->mNumMeshes <= 0)
		return false;

	Filepath = ModelFile;
	Path = Filepath;
	size_t pos = Filepath.rfind('/');
	if (pos == std::string::npos)
		pos = Filepath.rfind('\\');
	if (pos != std::string::npos)
		Path.resize(pos + 1);

	loadMeshes(pScene, FitSize);
	loadMaterials(pScene);
	loadNodes(pScene);

	return true;
}

void Model::loadMeshes(const aiScene* pScene, bool FitSize)
{
	printf("Loading %d Meshes\n", pScene->mNumMeshes);
	MeshCount = pScene->mNumMeshes;
	pMeshes = new Mesh[MeshCount];

	float scale = 1;
	Vector offset(0, 0, 0);
	if (FitSize) {
		printf("Scaling model to a 5x5x5 Bounding Box\n");
		AABB boundingBox;
		calcBoundingBox(pScene, boundingBox);
		Vector boundingBoxSize = boundingBox.size();
		printf("Original Bounding Box is (%f,%f,%f)<->(%f,%f,%f) %fx%fx%f\n", boundingBox.Min.X, boundingBox.Min.Y, boundingBox.Min.Z, boundingBox.Max.X, boundingBox.Max.Y, boundingBox.Max.Z, boundingBoxSize.X, boundingBoxSize.Y, boundingBoxSize.Z);
		offset = (boundingBox.Min + boundingBox.Max) * 0.5 * -1;
		scale = 5 / fmaxf(fmaxf(boundingBoxSize.X, boundingBoxSize.Y), boundingBoxSize.Z);
	}
	printf("Offset is (%f,%f,%f) and scale is %f\n", offset.X, offset.Y, offset.Z, scale);

	// Load Meshes
	for (unsigned int meshIndex = 0; meshIndex < pScene->mNumMeshes; meshIndex++) {
		aiMesh* pAiMesh = pScene->mMeshes[meshIndex];
		Mesh* pMesh = pMeshes + meshIndex;
		printf("%d:%s\n", meshIndex, pAiMesh->mName.C_Str());

		//Load Vertices
		printf("Loading %d Vertices and Normals\n", pAiMesh->mNumVertices);
		pMesh->VB.begin();
		printf("Mesh %d has %d UV Channels\n", meshIndex, pAiMesh->GetNumUVChannels());

		for (unsigned int vertexIndex = 0; vertexIndex < pAiMesh->mNumVertices; vertexIndex++) {
			aiVector3D* pVertex = pAiMesh->mVertices + vertexIndex;
			aiVector3D* pNormal = pAiMesh->mNormals + vertexIndex;
			aiVector3D* pTextCoord0 = pAiMesh->mTextureCoords[0] + vertexIndex;
			aiVector3D* pTextCoord1 = pAiMesh->mTextureCoords[1] + vertexIndex;
			aiVector3D* pTextCoord2 = pAiMesh->mTextureCoords[2] + vertexIndex;
			aiVector3D* pTextCoord3 = pAiMesh->mTextureCoords[3] + vertexIndex;

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
		printf("Loading %d Faces\n", pAiMesh->mNumFaces);
		pMesh->IB.begin();
		for (unsigned int faceIndex = 0; faceIndex < pAiMesh->mNumFaces; faceIndex++) {
			aiFace* pFace = &pAiMesh->mFaces[faceIndex];
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
void Model::loadMaterials(const aiScene* pScene)
{
	printf("Loading %d Materials\n", pScene->mNumMaterials);

	MaterialCount = pScene->mNumMaterials;
	pMaterials = new Material[MaterialCount];
	for (unsigned int materialIndex = 0; materialIndex < pScene->mNumMaterials; materialIndex++) {
		aiMaterial* pAiMaterial = pScene->mMaterials[materialIndex];
		Material* pMaterial = pMaterials + materialIndex;

		printf("Getting Properties for Material %d\n", materialIndex);
		aiColor3D aiResultColor(0, 0, 0);

		if (pAiMaterial->Get(AI_MATKEY_COLOR_AMBIENT, aiResultColor) != aiReturn_SUCCESS) {
			printf("Could not get Ambient Color\n");
		}
		pMaterial->AmbColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

		if (pAiMaterial->Get(AI_MATKEY_COLOR_DIFFUSE, aiResultColor) != aiReturn_SUCCESS) {
			printf("Could not get Diffuse Color\n");
		}
		pMaterial->DiffColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

		if (pAiMaterial->Get(AI_MATKEY_COLOR_SPECULAR, aiResultColor) != aiReturn_SUCCESS) {
			printf("Could not get Specular Color\n");
		}
		pMaterial->SpecColor = Color(aiResultColor.r, aiResultColor.g, aiResultColor.b);

		float specExp(1);
		if (pAiMaterial->Get(AI_MATKEY_SHININESS, specExp) != aiReturn_SUCCESS) {
			printf("Could not get Specular Exponent\n");
		}
		pMaterial->SpecExp = specExp;

		aiString textureAiString;
		std::string texturePath = Path;
		if (aiGetMaterialString(pAiMaterial, AI_MATKEY_TEXTURE_DIFFUSE(0), &textureAiString) != aiReturn_SUCCESS) {
			printf("Could not get Texture\n");
		}
		else {
			texturePath.append(textureAiString.C_Str());
		}
		pMaterial->DiffTex = Texture::LoadShared(texturePath.c_str());

		printf("Loaded Material %d:\n	AmbColor: %f,%f,%f\n	DiffColor: %f,%f,%f\n	SpecColor: %f,%f,%f\n	SpecExponent: %f\n	Texture: %s\n",
			materialIndex,
			pMaterial->AmbColor.R,
			pMaterial->AmbColor.G,
			pMaterial->AmbColor.B,
			pMaterial->DiffColor.R,
			pMaterial->DiffColor.G,
			pMaterial->DiffColor.B,
			pMaterial->SpecColor.R,
			pMaterial->SpecColor.G,
			pMaterial->SpecColor.B,
			pMaterial->SpecExp,
			texturePath.c_str()
		);
	}
}
void Model::calcBoundingBox(const aiScene* pScene, AABB& Box)
{
	Box.Min = Vector(INFINITY, INFINITY, INFINITY);
	Box.Max = Vector(-INFINITY, -INFINITY, -INFINITY);
	for (unsigned int meshIndex = 0; meshIndex < pScene->mNumMeshes; meshIndex++) {
		aiMesh* pMesh = pScene->mMeshes[meshIndex];
		for (unsigned int vertexIndex = 0; vertexIndex < pMesh->mNumVertices; vertexIndex++) {
			aiVector3D* pVector = &pMesh->mVertices[vertexIndex];
			if (pVector->x < Box.Min.X) {
				Box.Min.X = pVector->x;
			}
			if (pVector->y < Box.Min.Y) {
				Box.Min.Y = pVector->y;
			}
			if (pVector->z < Box.Min.Z) {
				Box.Min.Z = pVector->z;
			}
			if (pVector->x > Box.Max.X) {
				Box.Max.X = pVector->x;
			}
			if (pVector->y > Box.Max.Y) {
				Box.Max.Y = pVector->y;
			}
			if (pVector->z > Box.Max.Z) {
				Box.Max.Z = pVector->z;
			}
		}
	}
}

void Model::loadNodes(const aiScene* pScene)
{
	deleteNodes(&RootNode);
	copyNodesRecursive(pScene->mRootNode, &RootNode);
}

void Model::copyNodesRecursive(const aiNode* paiNode, Node* pNode)
{
	pNode->Name = paiNode->mName.C_Str();
	pNode->Trans = convert(paiNode->mTransformation);

	if (paiNode->mNumMeshes > 0)
	{
		pNode->MeshCount = paiNode->mNumMeshes;
		pNode->Meshes = new int[pNode->MeshCount];
		for (unsigned int i = 0; i < pNode->MeshCount; ++i)
			pNode->Meshes[i] = (int)paiNode->mMeshes[i];
	}

	if (paiNode->mNumChildren <= 0)
		return;

	pNode->ChildCount = paiNode->mNumChildren;
	pNode->Children = new Node[pNode->ChildCount];
	for (unsigned int i = 0; i < paiNode->mNumChildren; ++i)
	{
		copyNodesRecursive(paiNode->mChildren[i], &(pNode->Children[i]));
		pNode->Children[i].Parent = pNode;
	}
}

void Model::applyMaterial(unsigned int index)
{
	if (index >= MaterialCount)
		return;

	PhongShader* pPhong = dynamic_cast<PhongShader*>(shader());
	if (!pPhong) {
		std::cout << "Model::applyMaterial(): WARNING Invalid shader-type. Please apply PhongShader for rendering models.\n";
		return;
	}

	Material* pMat = &pMaterials[index];
	pPhong->ambientColor(pMat->AmbColor);
	pPhong->diffuseColor(pMat->DiffColor);
	pPhong->specularExp(pMat->SpecExp);
	pPhong->specularColor(pMat->SpecColor);
	pPhong->diffuseTexture(pMat->DiffTex);
}

void Model::draw(const BaseCamera& Cam)
{
	if (!pShader) {
		std::cout << "BaseModel::draw() no shader found" << std::endl;
		return;
	}
	pShader->modelTransform(transform());

	std::list<Node*> DrawNodes;
	DrawNodes.push_back(&RootNode);

	while (!DrawNodes.empty())
	{
		Node* pNode = DrawNodes.front();
		Matrix GlobalTransform;

		if (pNode->Parent != NULL)
			pNode->GlobalTrans = pNode->Parent->GlobalTrans * pNode->Trans;
		else
			pNode->GlobalTrans = transform() * pNode->Trans;

		pShader->modelTransform(pNode->GlobalTrans);

		for (unsigned int i = 0; i < pNode->MeshCount; ++i)
		{
			Mesh& mesh = pMeshes[pNode->Meshes[i]];
			mesh.VB.activate();
			mesh.IB.activate();
			applyMaterial(mesh.MaterialIdx);
			pShader->activate(Cam);
			glDrawElements(GL_TRIANGLES, mesh.IB.indexCount(), mesh.IB.indexFormat(), 0);
			mesh.IB.deactivate();
			mesh.VB.deactivate();
		}
		for (unsigned int i = 0; i < pNode->ChildCount; ++i)
			DrawNodes.push_back(&(pNode->Children[i]));

		DrawNodes.pop_front();
	}
}

Matrix Model::convert(const aiMatrix4x4& m)
{
	return Matrix(m.a1, m.a2, m.a3, m.a4,
		m.b1, m.b2, m.b3, m.b4,
		m.c1, m.c2, m.c3, m.c4,
		m.d1, m.d2, m.d3, m.d4);
}



