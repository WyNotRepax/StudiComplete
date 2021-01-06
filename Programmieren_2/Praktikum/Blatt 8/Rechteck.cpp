#include "Rechteck.hpp"
#include <sstream>
#include "math.h"
#include "OperandenPassenNicht.hpp"

Rechteck::Rechteck(const Punkt2D &lu, const Punkt2D &ro) : lu(lu), ro(ro) {}

Rechteck::Rechteck(const Rechteck &other) : Rechteck(other.lu, other.ro) {}

void Rechteck::setzePunktLu(const Punkt2D &p)
{
    lu = p;
}

void Rechteck::setzePunktRo(const Punkt2D &p)
{
    ro = p;
}

std::string Rechteck::toString()
{
    std::stringstream stream;
    stream << "Rechteck: [" << lu.toString() << ", " << ro.toString() << "]";
    return stream.str();
}

double Rechteck::inhalt()
{
    return std::abs((lu.x - ro.x) * (lu.y - ro.y));
}

GeoObjekt *Rechteck::clone()
{
    return new Rechteck(*this);
}

Rechteck &Rechteck::assign(const GeoObjekt &other)
{
    const Rechteck *pRechteck = dynamic_cast<const Rechteck *>(&other);
    if(!pRechteck){
        throw OperandenPassenNicht();
    }
    lu = (*pRechteck).lu;
    ro = (*pRechteck).ro;
}