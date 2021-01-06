#include "Fifo.h"
#include <iostream>

template <typename T>
Fifo<T>::FifoElement::FifoElement(T value) : value(value), next(nullptr) {}

template <typename T>
Fifo<T>::FifoElement::FifoElement(const Fifo<T>::FifoElement &other) : Fifo<T>::FifoElement(other.value)
{
    if (other.next != nullptr)
    {
        next = new Fifo<T>::FifoElement(*other.next);
    }
}

template <typename T>
Fifo<T>::FifoElement::~FifoElement()
{
}

template <typename T>
void Fifo<T>::FifoElement::push(Fifo<T>::FifoElement *element)
{
    if (next == nullptr)
    {
        next = element;
    }
    else
    {
        (*next).push(element);
    }
}

template <typename T>
void Fifo<T>::FifoElement::info()
{
    std::cout << value << std::endl;
    if (next != nullptr)
    {
        (*next).info();
    }
}
