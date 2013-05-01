package de.hsanhalt.inf.studiappkoethen.util;

public class StringUtils
{
    private StringUtils()
    {}
//  TODO nuetzliche statische Methoden einfuegen. ( trim etc )

    public static String getFileExtension(String fileName)
    {
        int index = fileName.lastIndexOf(".");
        if(index < 0)
        {
            return "";
        }
        return fileName.substring(index + 1, fileName.length());
    }
}
