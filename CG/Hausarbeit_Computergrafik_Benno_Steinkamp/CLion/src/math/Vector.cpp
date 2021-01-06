#include "Vector.h"
#include <assert.h>
#include <math.h>

#define EPSILON 1e-6

Vector::Vector(float x, float y, float z) : X(x), Y(y), Z(z) {}

Vector::Vector() {}

float Vector::dot(const Vector &v) const {
    return v.X * this->X + v.Y * this->Y + v.Z * this->Z;
}

Vector Vector::cross(const Vector &v) const {
    return Vector(this->Y * v.Z - this->Z * v.Y, this->Z * v.X - this->X * v.Z, this->X * v.Y - this->Y * v.X);
}


Vector Vector::operator+(const Vector &v) const {
    return Vector(this->X + v.X, this->Y + v.Y, this->Z + v.Z);
}

Vector Vector::operator-(const Vector &v) const {
    return *this + -v;
}

Vector Vector::operator*(float c) const {
    return Vector(this->X * c, this->Y * c, this->Z * c);
}

Vector Vector::operator-() const {
    return Vector(-this->X, -this->Y, -this->Z);
}

Vector &Vector::operator+=(const Vector &v) {
    this->X += v.X;
    this->Y += v.Y;
    this->Z += v.Z;
    return *this;
}


Vector &Vector::normalize() {
    float l = this->length();
    if (l > 0) {
        *this = *this * (1 / l);
    }
    return *this;
}

float Vector::length() const {
    return sqrtf(this->lengthSquared());
}

float Vector::lengthSquared() const {
    return this->X * this->X + this->Y * this->Y + this->Z * this->Z;
}

Vector Vector::reflection(const Vector &normal) const {
    return *this - normal * (2 * this->dot(normal));
}


bool Vector::triangleIntersection(const Vector &d, const Vector &a, const Vector &b, const Vector &c, float &s) const {
    Vector nPlane = ((b - a).cross(c - a)).normalize();
    float dPlane = nPlane.dot(a);

    s = (dPlane - nPlane.dot(*this)) / (nPlane.dot(d));

    Vector p = (*this) + d * s;

    Vector ab = b - a;
    Vector ac = c - a;
    Vector ap = p - a;

    float cn = 1 / (ac.dot(ac) * ab.dot(ab) - ac.dot(ab) * ab.dot(ac));

    float c1 = cn * ab.dot(ab);
    float c2 = cn * ab.dot(ac);
    float c3 = cn * ac.dot(ac);
    float c4 = cn * ac.dot(ab);

    float v = c1 * ap.dot(ac) - c2 * ap.dot(ab);
    float u = c3 * ap.dot(ab) - c4 * ap.dot(ac);

    bool ret = (
            s >= 0 - EPSILON &&
            0 - EPSILON <= u &&
            u <= 1 + EPSILON &&
            0 - EPSILON <= v &&
            v <= 1 + EPSILON &&
            0 - EPSILON <= (u + v) &&
            (u + v) <= 1 + EPSILON);
    return ret;
}

float Vector::angle(const Vector &other) {
    return acos((this->dot(other) / (this->length() * other.length())));
}
