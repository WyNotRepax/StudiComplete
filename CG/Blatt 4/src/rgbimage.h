#ifndef __SimpleRayTracer__rgbimage__
#define __SimpleRayTracer__rgbimage__

#include <iostream>

// https://de.wikipedia.org/wiki/Windows_Bitmap#Dateikopf

typedef unsigned short int WORD;
typedef unsigned long DWORD;
typedef long LONG;



class Color;

class RGBImage
{
public:

    typedef WORD bfType_t;
    typedef DWORD bfSize_t;
    typedef DWORD bfReserved_t;
    typedef DWORD bfOffBits_t;

    typedef DWORD biSize_t;
    typedef LONG biWidth_t;
    typedef LONG biHeight_t;
    typedef WORD biPlanes_t;
    typedef WORD biBitCount_t;
    typedef DWORD biCompression_t;
        const biCompression_t BI_RGB = 0;
        const biCompression_t BI_RLE8 = 1;
        const biCompression_t BI_RLE4 = 2;
        const biCompression_t BI_BITFIELDS = 3;
    typedef DWORD biSizeImage_t;
    typedef LONG biXPelsPerMeter_t;
    typedef LONG biYPelsPerMeter_t;
    typedef DWORD biClrUsed_t;
    typedef DWORD biClrImportant_t;

    const bfType_t bfType = 'MB';
    const bfReserved_t bfReserved = 0;
    const bfOffBits_t bfOffBits = 0x36;

    const biSize_t biSize = 40;
    const biPlanes_t biPlanes = 1;
    const biBitCount_t biBitCount = 24;
    const biCompression_t biCompression = BI_RGB;
    const biXPelsPerMeter_t biXPelsPerMeter = 0;
    const biYPelsPerMeter_t biYPelsPerMeter = 0;
    const biClrUsed_t biClrUsed = 0;
    const biClrImportant_t biClrImportant = 0;

    RGBImage( unsigned int Width, unsigned Height);
    ~RGBImage();
    void setPixelColor( unsigned int x, unsigned int y, const Color& c);
    const Color& getPixelColor( unsigned int x, unsigned int y) const;
    bool saveToDisk( const char* Filename);
    unsigned int width() const;
    unsigned int height() const;
    
    static unsigned char convertColorChannel( float f);
protected:
    Color* m_Image;
    unsigned int m_Height;
    unsigned int m_Width;
};

#endif /* defined(__SimpleRayTracer__rgbimage__) */
