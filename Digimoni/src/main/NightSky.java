package main;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.ImageViewer;
import rafgfxlib.Util;

public class NightSky {

	int[] black = { 9, 46, 99 };
	int[] white = { 23, 32, 45 };
	
	// Broj oktava, odredjuje detaljnost i konacnu velicinu
	int octaves = 10;
	
	// Velicina trenutne oktave, pocinjemo od 2 (2x2)
	int octaveSize = 2;
	
	// Faktor koji odredjuje uticaj pojedinacnih oktava; sto je veci,
	// rezultat ce biti sitniji i ostriji, podesavati po zelji
	float persistence = 0.60f;
	
	// Ako pocinjemo od 2 i imamo 10 oktava, dolazimo do dimenzije 2 ^ 10 = 1024
	int width = (int)Math.pow(octaveSize, octaves);
	int height = width;
	
	// Kreiramo raster u ciljnoj rezoluciji, za rezultujucu sliku
	WritableRaster target = Util.createRaster(width, height, false);
	
	// Privremena matrica ciljne velicine u koju cemo skalirati oktave
	float[][] tempMap = new float[width][height];
	
	// Konacna matrica u kojoj cemo da saberemo sve oktave
	float[][] finalMap = new float[width][height];
	
	// Pocinjemo od mnozitelja 1, koji ce se umanjivati persistence faktorom
	float multiplier = 1.0f;
	
	public BufferedImage create()
	{	
	// Iteriramo kroz zadati broj oktava
	for(int o = 0; o < octaves; ++o)
	{
		// Za svaku oktavu, kreiramo matricu u trenutoj velicini
		// (svaka naredna ce biti 2x veca od svoje prethodne)
		float[][] octaveMap = new float[octaveSize][octaveSize];
		
		// Prolazimo kroz sve celije matrice...
		for(int x = 0; x < octaveSize; ++x)
		{
			for(int y = 0; y < octaveSize; ++y)
			{
				// ...i popunjavamo ih nasumicnim brojevima od -1 do 1
				octaveMap[x][y] = ((float)Math.random() - 0.5f) * 2.0f;
			}
		}
		
		// Skaliramo octaveMap (promjenjive velicine, raste 2x u svakoj iteraciji)
		// u tempMap, koja je uvijek konstantne, ciljne velicine (bilinearno filt.)
		Util.floatMapRescaleCos(octaveMap, tempMap);
		
		// Blok koji ce da svaku pojedinacnu oktavu da iscrta u sliku i sacuva
		// u fajl, kako bi lakse bilo ispratiti sta se desava. Konacna slika
		// predstavlja tezinsku sumu svih slika koje ce se ovdje snimiti
		//{
		//	Util.mapFloatMapToRaster(tempMap, -1.0f, 1.0f, black, white, target);
		//	Util.saveImage(Util.rasterToImage(target), "octave-" + o + ".png");
		//}
		
		// MAD - Multiply + ADd, svako polje iz tempMap se mnozi sa multiplier
		// i sabire na polje istih koordinata u finalMap, formirajuci inkrementalno
		// konacnu tezinsku sumu
		Util.floatMapMAD(tempMap, finalMap, multiplier);
		
		// Svaka naredna oktava treba da bude 2x veca od prethodne
		octaveSize *= 2;
		
		// Svaki sljedeci mnozitelj treba da bude umanjen (persistence < 1)
		multiplier *= persistence;
	}
	
	// Kreira sliku u rasteru mapirajuci vrijednosti iz matrice iz zadatog opsega (-1 do 1)
	// u gradijent izmedju dvije zadate boje (black i white, sa vrha programa)
	Util.mapFloatMapToRaster(finalMap, -1.0f, 1.0f, black, white, target);
	
	
	// Konverzija rastera u BufferedImage i prikaz u prozoru
	//ImageViewer.showImageWindow(Util.rasterToImage(target), "RAF Racunarska Grafika");
	return Util.rasterToImage(target);
	
}
}
