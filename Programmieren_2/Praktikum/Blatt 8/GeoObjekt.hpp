#ifndef GEOOBJEKT_HPP
#define GEOOBJEKT_HPP

#include "MetrikVerhalten.hpp"
#include <string>

class GeoObjekt
{
public:
    virtual ~GeoObjekt(){}
    virtual double inhalt() = 0;
    virtual std::string toString() = 0;
    virtual GeoObjekt *clone() = 0;
    virtual GeoObjekt &assign(const GeoObjekt &other) = 0;
    GeoObjekt &operator=(const GeoObjekt &other);

private:
    MetrikVerhalten *metrik;
};

#endif