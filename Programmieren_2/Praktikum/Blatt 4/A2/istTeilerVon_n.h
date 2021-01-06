#ifndef istTeilerVon_n_H
#define istTeilerVon_n_H

using namespace std;
class istTeilerVon_n
{
public:
  istTeilerVon_n(int n);
  bool operator()(int i);

private:
  int n;
};

#endif