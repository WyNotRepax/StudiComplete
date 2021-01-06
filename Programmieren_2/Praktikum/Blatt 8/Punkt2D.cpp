#include "Punkt2D.hpp"
#include <sstream>

Punkt2D::Punkt2D(double x, double y):x(x),y(y){}

std::string Punkt2D::toString(){
    std::stringstream stream;
    stream << "(" << x << "," << y << ")";
    return stream.str();
}
