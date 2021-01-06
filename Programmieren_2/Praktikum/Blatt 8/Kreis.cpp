#include "Kreis.hpp"
#include <sstream>
#include "OperandenPassenNicht.hpp"

const double pi = 3.141592653589793238462643383279502884;

Kreis::Kreis(const Punkt2D &p, double r) : radius(r), zentrum(p) {}

Kreis::Kreis(const Kreis &other) : Kreis(other.zentrum, other.radius) {}

void Kreis::setzeZentrum(const Punkt2D &p)
{
    zentrum = p;
}

void Kreis::setzeRadius(const double r)
{
    radius = r;
}

std::string Kreis::toString()
{
    std::stringstream stream;
    stream << "Kreis: [" << zentrum.toString() << ", " << radius << "]";
    return stream.str();
}

double Kreis::inhalt()
{
    return pi * radius * radius;
}

Kreis *Kreis::clone()
{
    return new Kreis(*this);
}

Kreis &Kreis::assign(const GeoObjekt &other){
    const Kreis *pKreis = dynamic_cast<const Kreis *>(&other);
    if(!pKreis){
        throw OperandenPassenNicht();
    }
    zentrum = (*pKreis).zentrum;
    radius = (*pKreis).radius;
}