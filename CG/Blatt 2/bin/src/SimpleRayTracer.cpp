//
//  SimpleRayTracer.cpp
//  SimpleRayTracer
//
//  Created by Philipp Lensing on 03.09.14.
//  Copyright (c) 2014 Philipp Lensing. All rights reserved.
//

#include "SimpleRayTracer.h"
#include "rgbimage.h"
#include <stdio.h>
#include <math.h>
#include <algorithm> // For max


Camera::Camera(float zvalue, float planedist, float width, float height, unsigned int widthInPixel, unsigned int heightInPixel) :
	position(0.0f, 0.0f, zvalue),
	planedist(planedist),
	width(width),
	height(height),
	widthInPixel(widthInPixel),
	heightInPixel(heightInPixel) {}

Vector Camera::screenToWorld(unsigned int x, unsigned int y) const {
	Vector topLeft = position + Vector(-width / 2, height / 2, planedist);
	Vector pixelOffset(x * (width / widthInPixel), y * -(height / heightInPixel), 0);
	return topLeft + pixelOffset;
}


Vector Camera::generateRay(unsigned int x, unsigned int y) const
{
	Vector screenPos = screenToWorld(x, y);
	//printf("screenPos(%f,%f,%f)", screenPos.X, screenPos.Y, screenPos.Z);
	Vector ray = screenToWorld(x, y) - position;
	return ray.normalize();
}

Vector Camera::Position() const
{
	return Vector(position);
}

SimpleRayTracer::SimpleRayTracer(unsigned int MaxDepth) :maxDepth(MaxDepth) {}



void SimpleRayTracer::traceScene(const Scene& SceneModel, RGBImage& Image)
{
	int n = SceneModel.getLightCount();
	printf("Tracing Scene with %d Lights\n", n);
	for (int i = 0; i < n; i++) {
		PointLight l = SceneModel.getLight(i);
		printf("Light %d @(%f,%f,%f)\n", i, l.Position.X, l.Position.Y, l.Position.Z);
	}

	Camera camera(-8, 1, 1, 0.75, Image.width(), Image.height());
	unsigned int triN = SceneModel.getTriangleCount();
	for (int x = 0; x < Image.width(); x++) {
		printf("x:%d\n", x);
		for (int y = 0; y < Image.height(); y++) {
			//printf("x:%dy:%d\n", x,y);
			Vector ray = camera.generateRay(x, y);
			Color c = trace(SceneModel, camera.Position(), ray, maxDepth);
			Image.setPixelColor(x, y, c);
		}
	}
}

Color SimpleRayTracer::localIllumination(const Vector& SurfacePoint, const Vector& Eye, const Vector& Normal, const PointLight& Light, const Material& Material)
{
	return diffuseComponent(SurfacePoint, Normal, Light, Material) + specularComponent(SurfacePoint, Eye, Normal, Light, Material) + Material.getAmbientCoeff(SurfacePoint);
}

Color SimpleRayTracer::diffuseComponent(const Vector& SurfacePoint, const Vector& Normal, const PointLight& Light, const Material& Material) {
	Vector L = Light.Position - SurfacePoint;
	L.normalize();
	return Light.Intensity * Material.getDiffuseCoeff(SurfacePoint) * std::max(0.0f, Normal.dot(L));
}


Color SimpleRayTracer::specularComponent(const Vector& SurfacePoint, const Vector& Eye, const Vector& Normal, const PointLight& Light, const Material& Material)
{
	Vector R = (SurfacePoint - Light.Position).reflection(Normal);
	Vector E = Eye - SurfacePoint;
	R.normalize();
	E.normalize();
	return Light.Intensity * Material.getSpecularCoeff(SurfacePoint) * std::pow(std::max(0.0f, E.dot(R)), Material.getSpecularExp(SurfacePoint));
}

Color SimpleRayTracer::trace(const Scene& SceneModel, const Vector& o, const Vector& d, int depth)
{
	Color pixelColor = Color(0, 0, 0);
	//printf("Ray:o(%f,%f,%f),d(%f,%f,%f)\n", o.X, o.Y, o.Z, d.X, d.Y, d.Z);
	unsigned int triN = SceneModel.getTriangleCount();
	float minDist = INFINITY;
	Triangle minTri;
	unsigned int minTriI = -1;
	for (unsigned int i = 0; i < triN; i++) {
		Triangle tri = SceneModel.getTriangle(i);
		float s;
		if (o.triangleIntersection(d, tri.A, tri.B, tri.C, s)) {
			if (s != 0 && s < minDist) {
				minDist = s;
				minTri = tri;
				minTriI = i;
			}
		}
	}
	if (minTriI >= 0) {
		Vector p = o + d * minDist;
		unsigned int lightN = SceneModel.getLightCount();

		for (unsigned int lightI = 0; lightI < lightN; lightI++) {
			bool canSeeLight = true;
			PointLight light = SceneModel.getLight(lightI);
			Vector lightDirection = light.Position - p;
			float lightDist = lightDirection.length();
			lightDirection.normalize();
			for (unsigned int triI = 0; triI < triN; triI++) {
				Triangle tri = SceneModel.getTriangle(triI);
				float s;
				if (triI != minTriI && p.triangleIntersection(lightDirection, tri.A, tri.B, tri.C, s)) {
					if (s != 0 && s < lightDist) {
						//printf("point on triangle %d cant see light %d because triangle %d is in the way\n", minTriI, lightI, triI);
						canSeeLight = false;
						break;
					}
				}
			}
			if (canSeeLight) {
				pixelColor += localIllumination(p, o, minTri.calcNormal(p), light, *minTri.pMtrl);
			}
		}

		if (depth > 0) {
			Vector r = d.reflection(minTri.calcNormal(p));
			r.normalize();
			pixelColor += trace(SceneModel, p, r, depth - 1) * minTri.pMtrl->getReflectivity(p);
		}
	}
	return pixelColor;
}


