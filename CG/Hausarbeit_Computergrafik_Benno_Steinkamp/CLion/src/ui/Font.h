//
// Created by benno on 13.08.2020.
//

#ifndef CLION_FONT_H
#define CLION_FONT_H

#include <list>
#include <string>
#include "../Texture.h"

class Font {
public:
    struct FontChar {
        unsigned int id;
        unsigned int x;
        unsigned int y;
        unsigned int width;
        unsigned int height;
        unsigned int xOffset;
        unsigned int yOffset;
        unsigned int xAdvance;
        unsigned int page;
        unsigned int chnl;
    };
    typedef struct FontChar FontChar;

    struct FontPage {
        unsigned int id;
        std::string file;
        Texture *pTexture;
    };
    typedef struct FontPage FontPage;

    typedef std::map<unsigned int, FontChar*> FontCharMap;
    typedef std::map<unsigned int, FontPage *> FontPageMap;
    typedef std::pair<unsigned int,unsigned int> FontKerningID;
    typedef std::map<FontKerningID,int> FontKerningMap;


    Font(const char *filename);

    ~Font();

    FontChar *getFontChar(unsigned int id);
    const FontCharMap& getFontChars();
    FontPage *getFontPage(unsigned int id);
    const FontPageMap& getFontPages();

    int getKerningAmount(FontKerningID id);
    int getKerningAmount(unsigned int firstID, unsigned int secondID);
    int getLineHeight();

    unsigned int getPageCount();

protected: // protected member variables
    std::string mPath;
    FontCharMap mFontChars;
    FontPageMap mFontPages;
    FontKerningMap mFontKernings;

// From info line
    std::string face;
    unsigned int size;
    bool bold;
    bool italic;
    std::string charset;
    bool unicode;
    unsigned int stretchH;
    bool smooth;
    bool aa;
    unsigned int padding[4];
    unsigned int spacing[2];
    bool outline;
// From common line
    unsigned int lineHeight;
    unsigned int base;
    unsigned int scaleW;
    unsigned int scaleH;
    unsigned int pageCount;
    bool packed;
    unsigned int alphaChnl;
    unsigned int redChnl;
    unsigned int greenChnl;
    unsigned int blueChnl;
// From chars line
    unsigned int charCount;
// From kernings line
    unsigned int kerningCount;

protected: // protected methods
    void processInfoLine(const std::string &line);

    void processPageLine(const std::string &line);

    void processCommonLine(const std::string &line);

    void processCharsLine(const std::string &line);

    void processCharLine(const std::string &line);

    void processKerningsLine(const std::string &line);

    void processKerningLine(const std::string &line);

    static std::string getStringToken(const std::string &line, const std::string &token);

    static unsigned int getUIntToken(const std::string &line, const std::string &token);

    static int getIntToken(const std::string &line, const std::string &token);

    static bool getBoolToken(const std::string &line, const std::string &token);

    void setPadding(const std::string &line);

    void setSpacing(const std::string &line);
};


#endif //CLION_FONT_H
