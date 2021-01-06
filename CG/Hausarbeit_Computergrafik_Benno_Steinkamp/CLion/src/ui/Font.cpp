//
// Created by benno on 13.08.2020.
//

#include <cstdio>
#include "Font.h"
#include <string>
#include <fstream>
#include <algorithm>
#include <sstream>
#include <assert.h>

Font::Font(const char *filename) {
    std::string fname = filename;
    mPath = fname.substr(0, fname.find_last_of('/') + 1);
    std::ifstream inputstream;
    inputstream.open(filename);
    if (!inputstream.is_open()) {
        printf("Could not Open %s\n", filename);
        return;
    }

    std::string line;
    while (std::getline(inputstream, line)) {
        if (line.find("info") == 0) {
            processInfoLine(line);
        } else if (line.find("common") == 0) {
            processCommonLine(line);
        } else if (line.find("page") == 0) {
            processPageLine(line);
        } else if (line.find("chars") == 0) {
            processCharsLine(line);
        } else if (line.find("char") == 0) {
            processCharLine(line);
        } else if (line.find("kernings") == 0) {
            processKerningsLine(line);
        } else if (line.find("kerning") == 0) {
            processKerningLine(line);
        }
    }

    inputstream.close();

}

Font::~Font() {
    for (auto &pair : mFontChars) {
        delete pair.second;
    }
    mFontChars.clear();

    for (auto &pair : mFontPages) {
        delete pair.second;
    }
    mFontPages.clear();
    mFontKernings.clear();
}

void Font::processInfoLine(const std::string &line) {
    face = getStringToken(line, "face=");
    size = getUIntToken(line, "size=");
    bold = getBoolToken(line, "bold=");
    italic = getBoolToken(line, "italic=");
    charset = getStringToken(line, "charset=");
    unicode = getBoolToken(line, "unicode=");
    stretchH = getUIntToken(line, "stretchH=");
    smooth = getBoolToken(line, "smooth=");
    aa = getBoolToken(line, "aa=");
    setPadding(line);
    setSpacing(line);
    outline = getBoolToken(line, "outline=");
}

void Font::processCommonLine(const std::string &line) {
    lineHeight = getUIntToken(line, "lineHeight=");
    base = getUIntToken(line, "base=");
    scaleW = getUIntToken(line, "scaleW=");
    scaleH = getUIntToken(line, "scaleH=");

    // Clear fontPages before possibly overriding pageCount
    pageCount = getUIntToken(line, "pageCount=");

    packed = getBoolToken(line, "packed=");
    alphaChnl = getUIntToken(line, "alphaChnl=");
    redChnl = getUIntToken(line, "redChnl=");
    greenChnl = getUIntToken(line, "greenChnl=");
    blueChnl = getUIntToken(line, "blueChnl=");
}

void Font::processPageLine(const std::string &line) {
    auto *pFontPage = new FontPage;
    pFontPage->id = getUIntToken(line, "id=");
    pFontPage->file = getStringToken(line, "file=");
    pFontPage->pTexture = new Texture();
    pFontPage->pTexture->load((mPath + pFontPage->file).c_str());

    mFontPages[pFontPage->id] = pFontPage;
}

void Font::processCharsLine(const std::string &line) {
    charCount = getUIntToken(line, "count=");
}

void Font::processCharLine(const std::string &line) {
    auto *pFontChar = new FontChar;
    pFontChar->id = getUIntToken(line, "id=");
    pFontChar->x = getUIntToken(line, "x=");
    pFontChar->y = getUIntToken(line, "y=");
    pFontChar->width = getUIntToken(line, "width=");
    pFontChar->height = getUIntToken(line, "height=");
    pFontChar->xOffset = getUIntToken(line, "xoffset=");
    pFontChar->xAdvance = getUIntToken(line, "xadvance=");
    pFontChar->page = getUIntToken(line, "page=");
    pFontChar->chnl = getUIntToken(line, "chnl=");

    mFontChars[pFontChar->id] = pFontChar;
}

void Font::processKerningsLine(const std::string &line) {
    kerningCount = getUIntToken(line, "count=");
}

void Font::processKerningLine(const std::string &line) {
    unsigned int first = getUIntToken(line, "first=");
    unsigned int second = getUIntToken(line, "second=");
    int amount = getIntToken(line, "amount=");

    mFontKernings[FontKerningID(first, second)] = amount;
}

std::string Font::getStringToken(const std::string &line, const std::string &token) {
    int tokenpos = line.find(token);
    if (tokenpos == -1)
        return std::string();

    std::string::const_iterator it = line.begin();
    it += tokenpos;
    it += token.length();
    if (*it != '"')
        return std::string();
    it++;
    std::stringstream result;

    while (*it != '"') {
        result << *it++;
    }
    return result.str();
}

unsigned int Font::getUIntToken(const std::string &line, const std::string &token) {
    int tokenpos = line.find(token);
    if (tokenpos == -1)
        return -1;

    std::string::const_iterator it = line.begin();
    it += tokenpos;
    it += token.length();
    std::stringstream result;
    while (*it >= '0' && *it <= '9' && it != line.end()) {
        result << *it++;

    }

    return std::stoi(result.str());
}

int Font::getIntToken(const std::string &line, const std::string &token) {
    int tokenpos = line.find(token);
    if (tokenpos == -1)
        return -1;

    std::string::const_iterator it = line.begin();
    it += tokenpos;
    it += token.length();
    std::stringstream result;
    while (((*it >= '0' && *it <= '9') || *it == '-') && it != line.end()) {
        result << *it++;
    }

    return std::stoi(result.str());
}

bool Font::getBoolToken(const std::string &line, const std::string &token) {
    int tokenpos = line.find(token);
    if (tokenpos == -1)
        return false;
    char boolChar = line.at(tokenpos + token.length());
    return boolChar == '1';
}

void Font::setPadding(const std::string &line) {
    std::string token = "padding=";
    int tokenpos = line.find(token);
    if (tokenpos == -1)
        return;

    std::string::const_iterator it = line.begin();
    it += tokenpos;
    it += token.length();
    std::stringstream result;
    int n = 0;
    while (n < 4) {
        while (*it >= '0' && *it <= '9') {
            result << *it++;
        }
        padding[n] = std::stoi(result.str());
        result.clear();
        it++;
        n++;
    }
}

void Font::setSpacing(const std::string &line) {
    std::string token = "spacing=";
    int tokenpos = line.find(token);
    if (tokenpos == -1) {
        fprintf(stderr, "Could not find spacing!");
        return;
    }

    std::string::const_iterator it = line.begin();
    it += tokenpos;
    it += token.length();
    int n = 0;
    while (n < 2) {
        std::stringstream result;
        while (*it >= '0' && *it <= '9') {
            result << *it++;
        }
        spacing[n] = std::stoi(result.str());
        it++;
        n++;
    }
}


unsigned int Font::getPageCount() {
    return pageCount;
}

Font::FontChar *Font::getFontChar(unsigned int id) {
    try {
        return mFontChars.at(id);
    } catch (std::out_of_range &e) {
        return nullptr;
    }
}

Font::FontPage *Font::getFontPage(unsigned int id) {
    try {
        return mFontPages.at(id);
    } catch (std::out_of_range &e) {
        return nullptr;
    }
}

int Font::getKerningAmount(unsigned int firstID, unsigned int secondID) {
    return getKerningAmount(FontKerningID(firstID, secondID));
}

int Font::getKerningAmount(Font::FontKerningID id) {
    try {
        return mFontKernings.at(id);
    } catch (std::out_of_range &e) {
        return 0;
    }
}


const Font::FontPageMap &Font::getFontPages() {
    return mFontPages;
}

const Font::FontCharMap &Font::getFontChars() {
    return mFontChars;
}

int Font::getLineHeight() {
    return lineHeight;
}











