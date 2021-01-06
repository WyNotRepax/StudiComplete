#include "Metrik3D.hpp"
#include "math.h"

double Metrik3D::abstand(const Punkt3D &p1, const Punkt3D &p2)
{
    return std::sqrt(std::pow(p1.x-p2.x,2)+std::pow(p1.y-p2.y,2)+std::pow(p1.z-p2.z,2));
}