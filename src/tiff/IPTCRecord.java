/*
 * IPTCRecord.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 */

package tiff;

import java.util.ArrayList;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class IPTCRecord
  {
  // Fields
  ArrayList<IPTCDataSet> entriesList;
  
  // First Constructor
  public IPTCRecord(byte[] buf)
    {
    entriesList = new ArrayList<IPTCDataSet>();
    int position = 0;
    while (true)
      {
      IPTCDataSet dataSet = new IPTCDataSet(buf, position);
      if (dataSet.isValid() == false) break;
      entriesList.add(dataSet);
      position += dataSet.getLength();
      }
    }
  
  // Second Constructor
  public IPTCRecord()
    {
    // Establish an empty directory
    entriesList = new ArrayList<IPTCDataSet>();
    }

  // Method to return IPTC record length
  protected int getLength()
    {
    int length = 0;
    for (int n=0; n < entriesList.size(); n++)
      {
      IPTCDataSet dataSet = entriesList.get(n);
      length += dataSet.getLength();
      }
    return length;
    }

  // Method to write out the record
  protected void putBytes(byte[] buffer, long recordPosition)
    {
    int position = (int) recordPosition;
    for (int n=0; n < entriesList.size(); n++)
      {
      IPTCDataSet dataSet = entriesList.get(n);
      dataSet.putBytes(buffer, position);
      position += dataSet.getLength();
      }
    }

  // Method to find IPTC Record Tag Name
  public IPTCDataSet findIPTCDataSetName(String name)
    {
    for (int n=0; n < entriesList.size(); n++)
      {
      IPTCDataSet dataSet = entriesList.get(n);
      if (dataSet.getTag().getIPTCTagName().equals(name)) return dataSet;
      }
    return null;
    }

  // Method to remove IPTC Record Tag Name
  public void removeIPTCDataSet(String name)
    {
    for (int n=0; n < entriesList.size(); n++)
      {
      IPTCDataSet dataSet = entriesList.get(n);
      if (dataSet.getTag().getIPTCTagName().equals(name)) { entriesList.remove(n); return; }
      }
    }

  // Method to add IPTC data set
  public void addIPTCDataSet(IPTCDataSet dataSet)
    {
    entriesList.add(dataSet);
    }


  // Method to return entries list
  protected ArrayList<IPTCDataSet> getEntriesList() { return entriesList; }
  
  // Method to print out the metadata
  public void print(ArrayList<String> printout, String title)
    {
    String banner = "******************************************";
    if (printout.get(printout.size()-1).equals(banner) == false) printout.add(banner);
    printout.add("*    "+title);
    printout.add(banner);
    if (entriesList == null) printout.add("entriesList: "+entriesList);
    else
      {
      printout.add("entriesList size: "+entriesList.size());
      for (int n=0; n < entriesList.size(); n++)
        {
        printout.add("DataSet: "+n);
        IPTCDataSet dataSet = entriesList.get(n);
        if (dataSet != null) dataSet.print(printout);
        }
      }
    printout.add("******************************************");
    }


  }

