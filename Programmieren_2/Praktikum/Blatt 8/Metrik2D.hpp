#ifndef METRIK2D_HPP
#define METRIK2D_HPP

#include "MetrikVerhalten.hpp"
#include "Punkt2D.hpp"

class Metrik2D : MetrikVerhalten
{
public:
    Metrik2D(){}
    double abstand(Punkt2D const &p1, Punkt2D const &p2);
};

#endif