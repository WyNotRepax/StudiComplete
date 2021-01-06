#include "Quader.hpp"
#include <sstream>
#include "math.h"
#include "OperandenPassenNicht.hpp"

Quader::Quader(const Punkt3D &luv, const Punkt3D &roh) : luv(luv), roh(roh) {}

Quader::Quader(const Quader &other) : luv(other.luv), roh(other.roh) {}

void Quader::setzePunktLuv(const Punkt3D &p)
{
    luv = p;
}

void Quader::setzePunktRoh(const Punkt3D &p)
{
    roh = p;
}

std::string Quader::toString()
{
    std::stringstream stream;
    stream << "Quader: [" << luv.toString() << ", " << roh.toString() << "]";
    return stream.str();
}

double Quader::inhalt()
{
    return abs((luv.x - roh.x) * (luv.y - roh.y) * (luv.z - roh.z));
}

Quader *Quader::clone()
{
    return new Quader(*this);
}

Quader &Quader::assign(const GeoObjekt &other)
{
    const Quader *pQuader = dynamic_cast<const Quader *>(&other);
    if (!pQuader)
    {
        throw OperandenPassenNicht();
    }
    luv = (*pQuader).luv;
    roh = (*pQuader).roh;
}