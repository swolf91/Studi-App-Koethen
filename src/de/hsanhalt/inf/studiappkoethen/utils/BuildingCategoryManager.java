package de.hsanhalt.inf.studiappkoethen.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class BuildingCategoryManager
{
    private static BuildingCategoryManager INSTANCE;
    private List<BuildingCategory> categoryList;

    public static BuildingCategoryManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BuildingCategoryManager();
        }
        return INSTANCE;
    }

    private BuildingCategoryManager()
    {
        this.categoryList = new ArrayList<BuildingCategory>();
    }

    /**
     *    http://xjaphx.wordpress.com/2011/10/16/android-xml-adventure-parsing-xml-data-with-xmlpullparser/
     *    http://stackoverflow.com/questions/15254089/kxmlparser-throws-unexpected-token-exception-at-the-start-of-rss-pasing
     * @param stream
     */
    public void loadXML(InputStream stream)
    {
        if(stream != null)
        {
            try
            {
                XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setInput(stream, null);
                this.parseXML(parser);
            }
            catch (XmlPullParserException e)
            {
                Log.e("XmlPullParserException", e.getMessage(), e);
            }
            catch (IOException e)
            {
                Log.e("IOException", e.getMessage(), e);
            }
            finally
            {
                try
                {
                    stream.close();
                }
                catch(Exception e)
                {
                     Log.e("Stream", "Couldn't close the input stream of the categories.xml", e);
                }
            }
        }
    }

    private void parseXML(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        int eventType = parser.getEventType();

        System.out.println("parserStart: " + parser.getEventType());
        System.out.println("start? " + (eventType == XmlPullParser.START_DOCUMENT));

        int j = 0;
        /*
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_TAG)
            {
                System.out.println("Tagname: " + parser.getName());
            }
            eventType = parser.next();

            j++;
        }
        */
        System.out.println("die Schleife wurde " + j + " mal durchlaufen");
    }

    public boolean hasContent()
    {
        return this.categoryList != null && !this.categoryList.isEmpty();
    }

    public BuildingCategory getCategory(int id)
    {
        return null;
    }

    public BuildingCategory getCategory(String name)
    {
        return null;
    }
}
