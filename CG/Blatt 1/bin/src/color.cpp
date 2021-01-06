#include "color.h"
#include <assert.h>

Color::Color():Color(0,0,0){}

Color::Color(float r, float g, float b) :R(r), G(g), B(b) {}

Color Color::operator*(const Color& c) const
{
    return Color(this->R * c.R, this->G * c.G , this->B * c.B);
}

Color Color::operator*(const float Factor) const
{
	return Color(this->R * Factor, this->G * Factor, this->B * Factor);
}

Color Color::operator+(const Color& c) const
{
	return Color(this->R + c.R, this->G + c.G, this->B + c.B);
}

Color& Color::operator+=(const Color& c)
{
	*this = *this + c;
	return *this;
}