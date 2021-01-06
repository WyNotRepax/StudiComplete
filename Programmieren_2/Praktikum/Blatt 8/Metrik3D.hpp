#ifndef METRIK3D_HPP
#define METRIK3D_HPP

#include "MetrikVerhalten.hpp"
#include "Punkt3D.hpp"

class Metrik3D : MetrikVerhalten
{
public:
    double abstand(Punkt3D const &p1, Punkt3D const &p2);
};

#endif