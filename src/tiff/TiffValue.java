/*
 * TiffValue.java
 * version 1.0
 * 14 July 2022
 * Copyright 2022 Keith T. Knox
 * GNU General Public License v3.0
 *
 * Value of a TIFF directory entry.
 *
 */

package tiff;

/**
 * 
 * @author Keith T. Knox
 * @version 1.0
 *
 */

public class TiffValue
  {
  // Fields
  private int length;
  private byte[] valuesByteBuffer;

  TiffValue(byte[] buffer, int position, int length)
    {
    // Check for no data
    if (buffer == null || length == 0) { valuesByteBuffer = null; return; }
    this.length = length;
    
    // Copy in the value bytes
    byte[] b = new byte[length];
    for (int n=0; n < length; n++)  b[n] = buffer[n+position];
    setValuesByteBuffer(b);
    }

  protected long getValuesByteLength()
    {
    return length;
    }

  protected byte[] getValuesByteBuffer()
    {
    return valuesByteBuffer;
    }

  protected void setValuesByteBuffer(byte[] buffer)
    {
    valuesByteBuffer = buffer;
    length = buffer.length;
    if (buffer.length % 2 == 1)
      {
      byte[] buf = new byte[buffer.length+1];
      for (int i=0; i < buffer.length; i++) buf[i] = buffer[i];
      buf[buffer.length] = 0;
      valuesByteBuffer = buf;
      }
    }

  // Method to print out the metadata
  protected void print()
    {
    System.out.println("valuesByteBuffer: "+valuesByteBuffer);
    if (valuesByteBuffer != null)
      {
      for (int n=0; n < valuesByteBuffer.length; n++) System.out.print(valuesByteBuffer[n]);
      System.out.println(" ");
      }
    }

  }



