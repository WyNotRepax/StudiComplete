#include "Ringliste.h"
#include <sstream>
Ringliste::Ringliste(unsigned int capacity) : capacity(capacity){
	content = std::vector<int>(capacity);
	read = content.begin();
	write = content.begin();
}

std::string Ringliste::toString() {
	std::vector<int>::iterator it = read;

	std::string contentString = "";
	int n = 0;

	while (read != write) {
		contentString += (*it);
		n++;
		if (it == content.end()) {
			it = content.begin();
		}
		else {
			it++;
		}
	}

	std::stringstream ret;

	ret << n;
	ret << "/";
	ret << capacity;
	ret << " | ";
	ret << contentString;

	return ret.str();
}

Ringliste Ringliste::operator<<(int i)
{
	*write = i;
	write++;
	
	if (write == content.end()) {
		write = content.begin();
	}
	else {
		write++;
	}

	if (write == read) {
		if (read == content.end()) {
			read = content.begin();
		}
		else {
			read++;
		}
	}

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

