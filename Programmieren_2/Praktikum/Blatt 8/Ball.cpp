#include "Ball.hpp"
#include <sstream>
#include "OperandenPassenNicht.hpp"
const double pi = 3.141592653589793238462643383279502884;

Ball::Ball(const Punkt3D &p, double r) : zentrum(p), radius(r) {}

Ball::Ball(const Ball &other) : zentrum(other.zentrum), radius(other.radius) {}

void Ball::setzeZentrum(const Punkt3D &p)
{
    zentrum = p;
}

void Ball::setzeRadius(double r)
{
    radius = r;
}

std::string Ball::toString()
{
    std::stringstream stream;
    stream << "Ball: [" << zentrum.toString() << ", " << radius << "]";
    return stream.str();
}

double Ball::inhalt()
{
    return (4.0 / 3.0) * pi * radius * radius * radius;
}

Ball *Ball::clone()
{
    return new Ball(*this);
}

Ball &Ball::assign(const GeoObjekt &other)
{
    const Ball* pK = dynamic_cast<const Ball*>(&other);
    if(!pK){
        throw OperandenPassenNicht();
    }
    zentrum = (*pK).zentrum;
    radius = (*pK).radius;
    return *this;
}