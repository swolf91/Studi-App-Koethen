package de.hsanhalt.inf.studiappkoethen.xml.parsing;

/**
 * Exception die geworfen wird, wenn eine XML-Datei falsch implementiert wurde
 */
public class XmlParseException extends Exception
{
    public XmlParseException(String message)
    {
        super(message);
    }

    public XmlParseException(String message, Throwable t)
    {
        super(message, t);
    }
}
