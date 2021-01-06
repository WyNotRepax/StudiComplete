#ifndef FIFO_H
#define FIFO_H

template <typename T>
class Fifo
{
public:
  Fifo();
  Fifo(const Fifo<T> &other);
  ~Fifo();
  Fifo &operator<<(const T &value);
  Fifo &push(const T &value);
  Fifo &operator>>(T &value);
  Fifo &pop(T &value);
  T pop();
  operator int() const;
  unsigned int size();
  void info();

private:
  struct FifoElement
  {
    FifoElement(T value);
    FifoElement(const Fifo<T>::FifoElement &other);
    ~FifoElement();
    void push(Fifo<T>::FifoElement *element);
    void info();

    T value;
    Fifo<T>::FifoElement *next;
  };
  unsigned int length;
  Fifo<T>::FifoElement *top;
};

#include "Fifo.hpp"
#include "FifoElement.hpp"
#endif