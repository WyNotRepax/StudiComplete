//
//  DebugRender.hpp
//  CGXcode
//
//  Created by Philipp Lensing on 08.11.16.
//  Copyright © 2016 Philipp Lensing. All rights reserved.
//

#ifndef DebugRender_hpp
#define DebugRender_hpp

#include <stdio.h>
#include <vector>
#ifdef WIN32
#include <GL/glew.h>
#include <glfw/glfw3.h>
#else
#define GLFW_INCLUDE_GLCOREARB
#define GLFW_INCLUDE_GLEXT
#include <glfw/glfw3.h>
#endif
#include "helper/camera.h"
#include "shader/ConstantShader.h"
#include "math/Vector.h"
#include "helper/Color.h"
#include "math/matrix.h"
#include "helper/Aabb.h"

#define Debug DebugRender::ref()

class DebugRender
{
public:
    static DebugRender& ref();
    void drawLine( const Vector& a, const Vector& b, const Color& c=Color(1,1,1));
    void drawMatrix( const Matrix& m);
    void render(const BaseCamera& Cam);
    void init();
    void drawBoundingBox(const AABB BoundingBox);

protected:
    struct Line
    {
        Line() {}
        Line(const Vector& a, const Vector& b, const Color _c) : c(_c) { v[0] = a; v[1] = b; }
        Vector v[2];
        Color c;
    };
    struct Vertex
    {
        Vector p;
        Color c;
    };
    
    DebugRender() : MaxVertices(0){}
    DebugRender(const DebugRender&) {}
private:
    static DebugRender* Instance;
    std::vector<Line> Lines;
    GLuint VBO;
    GLuint VAO;
    GLsync fence;
    GLuint MaxVertices;
    ConstantShader Shader;
    
};

#endif /* DebugRender_hpp */
