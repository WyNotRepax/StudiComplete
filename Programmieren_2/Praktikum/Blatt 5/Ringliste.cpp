#include "Ringliste.h"
#include <sstream>
#include <iostream>

Ringliste::Ringliste(unsigned int capacity) : capacity(capacity), empty(true), read(0), write(0)
{
	content = std::vector<int>(capacity);
}

std::string Ringliste::toString()
{
	std::stringstream contentString;
	unsigned int n = 0;
	if (!empty)
	{
		contentString << content[read] << " ";
		n++;
		for (unsigned int i = (read + 1) % capacity; i != write; i++)
		{
			i %= capacity;
			contentString << content[i] << " ";
			n++;
		}
	}

	std::stringstream ret;
	ret << n;
	ret << "/";
	ret << capacity;
	ret << " | ";
	ret << contentString.str() << " ";

	return ret.str();
}

Ringliste &Ringliste::operator<<(const int i)
{
	if (write == read && !empty)
	{
		read++;
		read %= capacity;
	}
	content[write] = i;
	write++;
	empty = false;
	write %= capacity;

	return *this;
}

bool Ringliste::operator==(const Ringliste &other)
{
	// Empty Comparison
	if (empty)
	{
		return other.empty;
	}
	// Compare first Element
	if (content[read] != other.content[other.read])
	{
		return false;
	}
	// Initialize Loop variables
	unsigned int i = (read + 1) % capacity;
	unsigned int iother = (other.read + 1) % other.capacity;
	while (true)
	{
		// this has reached the End
		if (i == write)
		{
			return iother == other.write;
		}
		// other has reached the End
		else if (iother == other.write)
		{
			return false;
		}
		// Check Element wise
		else if (content[i] != other.content[iother])
		{
			return false;
		}
		// Increase variables
		i = (i + 1) % capacity;
		iother = (iother + 1) % other.capacity;
	}
}

void Ringliste::operator+=(int i)
{
	// add to all Elements
	for (int &n : content)
	{
		n += i;
	}
}

void Ringliste::operator<<(const Ringliste &other)
{
	if (!other.empty)
	{
		// Push first Element
		*this << other.content[read];
		// Push rest
		for (unsigned int i = other.read + 1; i != other.write; i++)
		{
			i %= capacity;
			*this << content[i];
		}
	}
}
