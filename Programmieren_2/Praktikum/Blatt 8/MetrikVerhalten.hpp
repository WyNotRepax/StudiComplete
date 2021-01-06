#ifndef METRIKVERHALTEN_HPP
#define METRIKVERHALTEN_HPP

#include "Punkt.hpp"

class MetrikVerhalten
{
public:
    virtual double abstand(Punkt const &, Punkt const &) = 0;
};


#endif