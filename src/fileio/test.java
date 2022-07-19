package fileio;

import java.io.File;
import java.io.IOException;

import fits.HeaderParameters;

public class test {

	public static void main(String[] args) throws IOException {
		//File single = new File("Z:\\RAW\\Barb.lat.4304\\DET_Barb.lat.4304\\Barb.lat.4304\\Barb.lat.4304.ff.0014_orig.dng");
		//File single = new File("Z:\\RAW\\Vat.lat.5750_iiq\\DET_Vat.lat.5750\\Multispettrale fatto da CIMA\\Vat.lat.5750\\Vat.lat.5750_pa_0061_P1__450nm__orig.dng");
		File single = new File("E:\\rawFinal\\1. Canon EOS D5 Mark II.dng");
		if (single.getName().endsWith(".dng")) {
				System.out.println(single.getAbsolutePath());
				File output=new File(single.getAbsolutePath().replace(".dng", ".fits"));
				HeaderParameters headParams=new HeaderParameters();
				WriteFits.convert(single, output, headParams.getHeaderParams());
				File outputdng=new File(single.getAbsolutePath().replace(".dng", "_conv.dng"));
				WriteDng.convert(output, outputdng);
				
		}
	}

}
