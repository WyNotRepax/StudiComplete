//
//  TriangleBoxModel.cpp
//  CGXcode
//
//  Created by Philipp Lensing on 10.10.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#include "TriangleBoxModel.h"
#define SIDES 6

TriangleBoxModel::TriangleBoxModel(float Width, float Height, float Depth)
{
	// Setup Vertex Buffer
	VB.begin();

	// Vorne
	VB.addVertex(-Width / 2, -Height / 2, Depth / 2); // Links Unten Vorne
	VB.addVertex(Width / 2, -Height / 2, Depth / 2); // Rechts Unten Vorne
	VB.addVertex(Width / 2, Height / 2, Depth / 2); // Rechts Oben Vorne
	VB.addVertex(-Width / 2, Height / 2, Depth / 2); // Links Oben Vorne

	// Hinten
	VB.addVertex(Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(-Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(-Width / 2, Height / 2, -Depth / 2);
	VB.addVertex(Width / 2, Height / 2, -Depth / 2);

	//Oben
	VB.addVertex(-Width / 2, Height / 2, Depth / 2);
	VB.addVertex(Width / 2, Height / 2, Depth / 2);
	VB.addVertex(Width / 2, Height / 2, -Depth / 2);
	VB.addVertex(-Width / 2, Height / 2, -Depth / 2);

	//Unten
	VB.addVertex(-Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(Width / 2, -Height / 2, Depth / 2);
	VB.addVertex(-Width / 2, -Height / 2, Depth / 2);


	//Rechts
	VB.addVertex(Width / 2, -Height / 2, Depth / 2);
	VB.addVertex(Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(Width / 2, Height / 2, -Depth / 2);
	VB.addVertex(Width / 2,Height / 2, Depth / 2);


	//Links
	VB.addVertex(-Width / 2, -Height / 2, -Depth / 2);
	VB.addVertex(-Width / 2, -Height / 2, Depth / 2);
	VB.addVertex(-Width / 2, Height / 2, Depth / 2);
	VB.addVertex(-Width / 2, Height / 2, -Depth / 2);

	// Vorne
	VB.addNormal(0, 0, 1);
	VB.addNormal(0, 0, 1);
	VB.addNormal(0, 0, 1);
	VB.addNormal(0, 0, 1);

	// Hinten
	VB.addNormal(0, 0, -1);
	VB.addNormal(0, 0, -1);
	VB.addNormal(0, 0, -1);
	VB.addNormal(0, 0, -1);

	// Oben
	VB.addNormal(0, 1, 0);
	VB.addNormal(0, 1, 0);
	VB.addNormal(0, 1, 0);
	VB.addNormal(0, 1, 0);

	// Unten
	VB.addNormal(0, -1, 0);
	VB.addNormal(0, -1, 0);
	VB.addNormal(0, -1, 0);
	VB.addNormal(0, -1, 0);

	//Rechts
	VB.addNormal(1, 0, 0);
	VB.addNormal(1, 0, 0);
	VB.addNormal(1, 0, 0);
	VB.addNormal(1, 0, 0);


	//Links
	VB.addNormal(-1, 0, 0);
	VB.addNormal(-1, 0, 0);
	VB.addNormal(-1, 0, 0);
	VB.addNormal(-1, 0, 0);




	for (int i = 0; i < SIDES; i++) {
		VB.addTexcoord0(0, 2);
		VB.addTexcoord0(1, 2);
		VB.addTexcoord0(1, 0);
		VB.addTexcoord0(0, 0);
	}




	VB.end();
	// Setup Index Buffer
	IB.begin();
	for (int i = 0; i < SIDES; i++) {
		int offset = 4 * i;
		//Triangle 1
		IB.addIndex(offset + 0);
		IB.addIndex(offset + 2);
		IB.addIndex(offset + 1);
		//Triangle 2
		IB.addIndex(offset + 2);
		IB.addIndex(offset + 0);
		IB.addIndex(offset + 3);
	}

	IB.end();
}

void TriangleBoxModel::draw(const BaseCamera& Cam)
{
	BaseModel::draw(Cam);

	VB.activate();
	IB.activate();

	glDrawElements(GL_TRIANGLES, IB.indexCount(), IB.indexFormat(), 0);

	IB.deactivate();
	VB.deactivate();
}
