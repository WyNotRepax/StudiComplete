#include "Punkt3D.hpp"
#include <sstream>

Punkt3D::Punkt3D(double x, double y, double z) : x(x), y(y), z(z){};

std::string Punkt3D::toString()
{
    std::stringstream stream;
    stream << "(" << x << "," << y << "," << z << ")";
    return stream.str();
}
