#ifndef RECHTECK_HPP
#define RECHTECK_HPP

#include "GeoObjekt.hpp"
#include "Punkt2D.hpp"

class Rechteck : public GeoObjekt
{
public:
    Rechteck(const Punkt2D &p1, const Punkt2D &p2);
    Rechteck(const Rechteck &other);
    void setzePunktLu(const Punkt2D &p);
    void setzePunktRo(const Punkt2D &p);
    std::string toString();
    double inhalt();
    GeoObjekt *clone();
    Rechteck &assign(const GeoObjekt &other);

private:
    Punkt2D lu;
    Punkt2D ro;
};

#endif