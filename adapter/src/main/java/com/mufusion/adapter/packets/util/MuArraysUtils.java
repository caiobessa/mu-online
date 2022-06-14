package com.mufusion.adapter.packets.util;

public class MuArraysUtils {

    public void copy(Integer startIndex, byte[] dest, byte[] src)  {
        System.arraycopy(src, 0, dest, 7, src.length);
    }
}
