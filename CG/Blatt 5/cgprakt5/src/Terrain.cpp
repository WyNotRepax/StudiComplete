#include "Terrain.h"
#include "rgbimage.h"
#include "Terrainshader.h"

// Maps a value from the range [valueMin,valueMax] onto the range [outMin,outMax] in a linear fashion
// if value == valueMin then LINMAP returns outMin
// if value == valueMax then LINMAP returns outMax
#define LINMAP(value,valueMin,valueMax,outMin,outMax) ((value-valueMin) / (valueMax-valueMin) * (outMax - outMin) + outMin)

Terrain::Terrain(const char* HeightMap, const char* DetailMap1, const char* DetailMap2) : Size(10, 1, 10)
{
	if (HeightMap && DetailMap1 && DetailMap2)
	{
		bool loaded = load(HeightMap, DetailMap1, DetailMap2);
		if (!loaded)
			throw std::exception();
	}
}

Terrain::~Terrain()
{

}

bool Terrain::load(const char* HeightMap, const char* DetailMap1, const char* DetailMap2)
{
	if (!HeightTex.load(HeightMap))
		return false;
	if (!DetailTex[0].load(DetailMap1))
		return false;
	if (!DetailTex[1].load(DetailMap2))
		return false;

	const RGBImage* pHeightImg = HeightTex.getRGBImage();
	const unsigned int imgWidth = pHeightImg->width();
	const unsigned int imgHeight = pHeightImg->height();

	RGBImage mixImg(imgWidth, imgHeight);
	mixImg.SobelFilter(mixImg, *pHeightImg,30);

	if (!MixTex.create(mixImg))
		return false;

	VB.begin();
	IB.begin();
	printf("Creating Terrain Model %dx%d\n", imgWidth, imgHeight);
	for (unsigned int pixelY = 0; pixelY < imgHeight; pixelY++) {
		for (unsigned int pixelX = 0; pixelX < imgWidth; pixelX++) {
			//printf("x:%d,y%d\n", pixelX, pixelY);
			float worldX = LINMAP((float)pixelX, 0, (float)(imgWidth - 1), -width() / 2, width() / 2);
			float worldZ = LINMAP((float)pixelY, 0, (float)(imgHeight - 1), -depth() / 2, depth() / 2);
			Color pixelColor = pHeightImg->getPixelColor(pixelX, pixelY);
			float grayScale = (pixelColor.R + pixelColor.G + pixelColor.B) / 3.0f;
			float worldY = height()* grayScale;

			//printf("(%d,%d) -> (%f,%f,%f)\n", pixelX, pixelY, worldX,worldY,worldZ);
			VB.addTexcoord0((float)pixelX / (imgWidth - 1), (float)pixelY / (imgWidth - 1));
			VB.addVertex(worldX, worldY, worldZ);
			if (pixelX < imgWidth - 1 && pixelY < imgHeight - 1) {
				IB.addIndex(pixelX + pixelY * imgWidth);
				IB.addIndex((pixelX + 1) + (pixelY + 1) * imgWidth);
				IB.addIndex((pixelX + 1) + pixelY * imgWidth);

				IB.addIndex(pixelX + pixelY * imgWidth);
				IB.addIndex(pixelX + (pixelY + 1) * imgWidth);
				IB.addIndex((pixelX + 1) + (pixelY + 1) * imgWidth);
			}
		}
	}
	printf("Calculating Normals\n");
	Vector* normalSums = new Vector[VB.vertexCount()];
	for (unsigned int i = 0; i < VB.vertexCount(); i++) {
		normalSums[i] = Vector(0, 0, 0);
	}
	for (unsigned int i = 0; i < IB.indexCount();) {
		unsigned int a = IB.indices().at(i++);
		unsigned int b = IB.indices().at(i++);
		unsigned int c = IB.indices().at(i++);

		Vector ab = VB.vertices().at(b) - VB.vertices().at(a);
		Vector ac = VB.vertices().at(c) - VB.vertices().at(a);

		Vector normal = ab.cross(ac);
		normal.normalize();

		normalSums[a] += normal;
		normalSums[b] += normal;
		normalSums[c] += normal;
	}
	// Renormalize
	for (unsigned int i = 0; i < VB.normals().size(); i++) {
		VB.addNormal(normalSums[i].normalize());
	}
	delete[] normalSums;
	VB.end();
	IB.end();

	return true;
}

void Terrain::shader(BaseShader* shader, bool deleteOnDestruction)
{
	BaseModel::shader(shader, deleteOnDestruction);

}

void Terrain::draw(const BaseCamera& Cam)
{
	applyShaderParameter();
	BaseModel::draw(Cam);
	VB.activate();
	IB.activate();

	glDrawElements(GL_TRIANGLES, IB.indexCount(), IB.indexFormat(), 0);

	IB.deactivate();
	VB.deactivate();
}

void Terrain::applyShaderParameter()
{
	TerrainShader* Shader = dynamic_cast<TerrainShader*>(BaseModel::shader());
	if (!Shader)
		return;

	Shader->mixTex(&MixTex);
	for (int i = 0; i < 2; i++)
		Shader->detailTex(i, &DetailTex[i]);
	Shader->scaling(Size);

	// TODO: add additional parameters if needed..
}
