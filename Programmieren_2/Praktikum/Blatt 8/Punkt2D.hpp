#ifndef PUNKT2D_HPP
#define PUNKT2D_HPP

#include "Punkt.hpp"
#include <string>

class Punkt2D : Punkt
{
public:
    Punkt2D(const double x, const double y);
    double x;
    double y;
    std::string toString();
};

#endif