#include "istKuerzerAls.h"

bool istKuerzerAls::operator()(string s1, string s2){
    return s1.size() < s2.size();
}