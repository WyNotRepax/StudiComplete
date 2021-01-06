#include "Fifo.h"
#include "new"
#include <iostream>

template <typename T>
Fifo<T>::Fifo() : top(nullptr), length(0)
{
}

template <typename T>
Fifo<T>::Fifo(const Fifo<T> &other) : Fifo()
{
    length = other.length;
    if (other.top != nullptr)
    {
        top = new FifoElement(*other.top);
    }
}

template <typename T>
Fifo<T>::~Fifo()
{
    while (length > 0)
    {
        T temp;
        *this >> temp;
    }
}

template <typename T>
Fifo<T> &Fifo<T>::operator<<(const T &value)
{
    try
    {
        Fifo<T>::FifoElement *newElem = new Fifo<T>::FifoElement(value);
        if (top == nullptr)
        {
            top = newElem;
        }
        else
        {
            (*top).push(newElem);
        }
    }
    catch (std::bad_alloc &ba)
    {
        throw "Memory allocation failed!";
    }
    length++;
    return *this;
}

template <typename T>
Fifo<T> &Fifo<T>::push(const T &value)
{
    return *this << value;
}


template <typename T>
Fifo<T> &Fifo<T>::operator>>(T &value)
{
    if (top == nullptr)
    {
        throw "Fifo Unterlauf";
    }
    value = (*top).value;
    Fifo<T>::FifoElement *toDelete = top;
    top = (*top).next;
    delete toDelete;
    length--;
    return *this;
}

template <typename T>
Fifo<T> &Fifo<T>::pop(T &value)
{
    return *this >> value;
}

template <typename T>
T Fifo<T>::pop()
{
    T ret;
    *this >> ret;
    return ret;
}

template <typename T>
Fifo<T>::operator int() const
{
    return length;
}

template <typename T>
unsigned int Fifo<T>::size()
{
    return length;
}

template <typename T>
void Fifo<T>::info()
{
    std::cout << "Inhalt des Fifos:\n";
    if (top != nullptr)
    {
        (*top).info();
    }
}
