#ifndef KREIS_HPP
#define KREIS_HPP

#include "GeoObjekt.hpp"
#include "Punkt2D.hpp"

class Kreis : public GeoObjekt
{
public:
    Kreis(const Punkt2D &p, double r);
    Kreis(const Kreis &other);
    void setzeZentrum(const Punkt2D &p);
    void setzeRadius(double r);
    std::string toString();
    double inhalt();
    Kreis *clone();
    Kreis &assign(const GeoObjekt &other);

private:
    double radius;
    Punkt2D zentrum;
};
#endif