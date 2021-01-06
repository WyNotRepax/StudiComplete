#ifndef Ringliste_H
#define Ringliste_H

#include <string>
#include <vector>
class Ringliste
{
private:
  unsigned int capacity;
  unsigned int read;
  unsigned int write;
  bool empty;
  std::vector<int> content;


public:
  Ringliste(unsigned int capacity = 10);
  std::string toString();
  Ringliste& operator<<(const int i);
  void operator<<(const Ringliste &other);
  bool operator==(const Ringliste &other);
  void operator+=(int i);
};

#endif