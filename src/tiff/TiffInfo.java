/*
 * TiffInfo.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Stand-alone routine to read and display the header of a TIFF image.
 *
 */

package tiff;


import javax.swing.JFileChooser;
import java.util.*;
import java.io.*;


/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffInfo
  {
  // Fields
  private RandomAccessFile raf;

  public TiffInfo(String name)
    {
    // Open the file for random access and get the TiffHandle
    try { raf = new RandomAccessFile(name, "r"); }
    catch(FileNotFoundException e) { System.out.println("File not found."); }
    catch(IOException e) {  }
    TiffHandle handle = new TiffHandle(raf);
    ArrayList<String> printout = handle.print(name);
    for (int n=0; n < printout.size(); n++) System.out.println(printout.get(n));

    // Now close the file
    try { raf.close(); }
    catch(FileNotFoundException e) {  }
    catch(IOException e) {  }
    }

  public static void main(String[] args)
    {
    // Go find the file to show
    File file = getFile();
    String filename = null;
    if (file == null) return;
    try {filename = file.getCanonicalPath();}
    catch (IOException e) { }    
    TiffInfo ti = new TiffInfo(filename);
    }

  private static File getFile()
    {
    JFileChooser fc = new JFileChooser();
    fc.setAcceptAllFileFilterUsed(false);
    fc.addChoosableFileFilter(new ReadFileNameFilter());
    int result = fc.showOpenDialog(null);
    File file = null;
    if (result == JFileChooser.APPROVE_OPTION) file = fc.getSelectedFile();
    return file;
    }

  }


class ReadFileNameFilter extends javax.swing.filechooser.FileFilter
  {
  // Fields
  String extension = ".tif";
  
  public boolean accept(File file)
    {
    if (file.isDirectory() == true) return true;
    String filename = file.getName();
    if (filename.endsWith(".tif") == true) return true;
    if (filename.endsWith(".dng") == true) return true;
    if (filename.endsWith(".hdr") == true) { extension = ".hdr"; return true; }
    return false;
    }
  
  public String getDescription()
    {
    String name = "TIFF";
    if (extension.equals(".hdr")) name = "ENVI";
    return name+" files (*"+extension+")";
    }
  }

