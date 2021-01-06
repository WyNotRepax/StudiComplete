#ifndef BALL_HPP
#define BALL_HPP

#include "GeoObjekt.hpp"
#include "Punkt3D.hpp"
#include <string>

class Ball : public GeoObjekt
{
public:
    Ball(const Punkt3D &p, double r);
    Ball(const Ball &other);
    void setzeZentrum(const Punkt3D &p);
    void setzeRadius(double r);
    std::string toString();
    double inhalt();
    Ball *clone();
    Ball &assign(const GeoObjekt &other);

private:
    double radius;
    Punkt3D zentrum;
};

#endif