package main;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.Util;

public class NightSky {

	int[] black = { 9, 46, 99 };
	int[] white = { 23, 32, 45 };
	
	int octaves = 10;
	
	int octaveSize = 2;
	
	float persistence = 0.60f;
	
	int width = (int)Math.pow(octaveSize, octaves);
	int height = width;
	
	WritableRaster target = Util.createRaster(width, height, false);
	
	float[][] tempMap = new float[width][height];
	
	float[][] finalMap = new float[width][height];
	
	float multiplier = 1.0f;
	
	public BufferedImage create()
	{	
	for(int o = 0; o < octaves; ++o)
	{
		float[][] octaveMap = new float[octaveSize][octaveSize];
		
		for(int x = 0; x < octaveSize; ++x)
		{
			for(int y = 0; y < octaveSize; ++y)
			{
				octaveMap[x][y] = ((float)Math.random() - 0.5f) * 2.0f;
			}
		}
		
		Util.floatMapRescaleCos(octaveMap, tempMap);
		
		Util.floatMapMAD(tempMap, finalMap, multiplier);
		
		octaveSize *= 2;
		
		multiplier *= persistence;
	}
	
	Util.mapFloatMapToRaster(finalMap, -1.0f, 1.0f, black, white, target);
	
	return Util.rasterToImage(target);
	
}
}
