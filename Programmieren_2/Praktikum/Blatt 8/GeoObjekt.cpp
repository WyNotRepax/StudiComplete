#include "GeoObjekt.hpp"

GeoObjekt &GeoObjekt::operator=(const GeoObjekt &other)
{
    if(this == &other){
        return *this;
    }
    return assign(other);
}