#ifndef PUNKT3D_HPP
#define PUNKT3D_HPP

#include "Punkt.hpp"
#include <string>
class Punkt3D : Punkt
{
public:
    Punkt3D(const double x, const double y, const double z);
    double x;
    double y;
    double z;
    std::string toString();
};

#endif