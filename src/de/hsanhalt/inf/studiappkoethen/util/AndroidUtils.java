package de.hsanhalt.inf.studiappkoethen.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Diese Klasse soll nuetliche Klassen beim Umgang mit Android enthalten
 */
public final class AndroidUtils
{
    private AndroidUtils()
    {}

    /**
     * Gibt ein Bitmap-Objekt aus dem Assets-Ordner zurueck.
     * @param assetManager - Objekt des AssetManagers
     * @param path - Pfad zum Bild
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFromAsset(AssetManager assetManager, String path) throws IOException
    {
        InputStream inputStream = assetManager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }
}
