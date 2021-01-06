#include "Ringliste.h"

Ringliste::Ringliste(unsigned int capacity) : capacity(capacity),empty(true) {
}

std::string Ringliste::toString() {
	return "";
}

Ringliste Ringliste::operator<<(int i)
{
	return *this;
}

bool Ringliste::operator==(const Ringliste & other)
{
	return false;
}

void Ringliste::operator+=(int i)
{
}

void Ringliste::operator<<(const Ringliste & other)
{
}

