#ifndef QUADER_HPP
#define QUADER_HPP

#include "GeoObjekt.hpp"
#include "Punkt3D.hpp"
#include <string>

class Quader : public GeoObjekt
{
public:
    Quader(const Punkt3D &luv, const Punkt3D &roh);
    Quader(const Quader &other);
    void setzePunktLuv(const Punkt3D &p);
    void setzePunktRoh(const Punkt3D &p);
    std::string toString();
    double inhalt();
    Quader *clone();
    Quader &assign(const GeoObjekt &other);

private:
    Punkt3D luv;
    Punkt3D roh;
};

#endif