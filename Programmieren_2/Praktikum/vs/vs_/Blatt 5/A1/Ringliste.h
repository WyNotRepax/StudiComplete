#ifndef Ringliste_H
#define Ringliste_H

#include <string>
#include <vector>
class Ringliste
{
private:
  unsigned int capacity;
  std::vector<int> content;
  std::vector<int>::iterator read;
  std::vector<int>::iterator write;


public:
	Ringliste(unsigned int capacity = 10);
  std::string toString();
  Ringliste operator<<(int i);
  bool operator==(const Ringliste &other);
  void operator+=(int i);
  void operator<<(const Ringliste &other);
};

#endif