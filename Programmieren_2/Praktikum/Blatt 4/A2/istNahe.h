#ifndef istNahe_H
#define istNahe_H
using namespace std;
class istNahe
{
  private:
    double d;
    double tolerance;

  public:
    bool operator()(double z);
    istNahe(double d, double tolerance);
};

#endif