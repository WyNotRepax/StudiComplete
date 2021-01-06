#ifndef Ringliste_H
#define Ringliste_H

#include <string>
class Ringliste
{
private:
  unsigned int capacity;
  int read;
  int write;
  bool empty;


public:
	Ringliste(unsigned int capacity = 10);
  std::string toString();
  Ringliste operator<<(int i);
  bool operator==(const Ringliste &other);
  void operator+=(int i);
  void operator<<(const Ringliste &other);
};

#endif