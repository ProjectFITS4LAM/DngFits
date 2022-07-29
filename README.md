# DngFits
Adobe DNG to FITS  and FITS to Adobe DNG converter

Compile the code with Java 8/11, using the main class src/fileio/Run.java.

Run the code from command line:

dng to fits conversion : java -jar DngFits.jar dng2fits input.dng output.fits

fits to dng conversion : java -jar DngFits.jar fits2dng input.fits output.dng

The software works only with uncompressed DNG with the mosaic (no demosaiced images), below a list of specifications used on Adobe DNG converter to produce suited DNG images.

Compatibility: Custom \
Version <= DNG 1.4\
Uncompressed, with mosaic\
\
JPEG Preview: None\
Don't embed fast load data\
Don't use lossy compression\
Preserve Pixel Count\
Don't embed original\
