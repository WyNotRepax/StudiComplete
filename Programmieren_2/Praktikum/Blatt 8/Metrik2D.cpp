#include "Metrik2D.hpp"
#include "math.h"

double Metrik2D::abstand(const Punkt2D &p1, const Punkt2D &p2)
{
    return std::sqrt(std::pow(p1.x-p2.x,2)+std::pow(p1.y-p2.y,2));
}