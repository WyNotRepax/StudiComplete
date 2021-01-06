#include <iostream>
#include "cgutilities.h"
#include "rgbimage.h"
#include "simpleraytracer.h"

#define SCENE_COMPLEXITY 20
#define MAX_DEPTH 2
#define Z_VALUE -8
#define PLANEDIST 1
#define WIDTH 1
#define HEIGHT 0.75
#define WIDTH_IN_PIXEL 640
#define HEIGHT_IN_PIXEL 480

int main(int argc, const char * argv[]) {
    Scene scene(SCENE_COMPLEXITY);
    RGBImage image(WIDTH_IN_PIXEL, HEIGHT_IN_PIXEL);
    SimpleRayTracer tracer(MAX_DEPTH);
    tracer.traceScene(scene, image);
    std::cout << std::boolalpha << image.saveToDisk("raytracing_image.bmp");
    return 0;
}
