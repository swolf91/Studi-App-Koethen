package de.hsanhalt.inf.studiappkoethen.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class AndroidUtils
{
    private AndroidUtils()
    {}

    public static Bitmap getBitmapFromAsset(AssetManager assetManager, String path) throws IOException
    {
        InputStream inputStream = assetManager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }
}
