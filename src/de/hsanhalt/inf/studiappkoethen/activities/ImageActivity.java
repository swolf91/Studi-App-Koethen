package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.AndroidUtils;

public class ImageActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image);

        String path = this.getIntent().getStringExtra("image");
        if(path != null && !path.isEmpty())
        {
            try
            {
                ImageView imageView = (ImageView) this.findViewById(R.id.image_image);
                imageView.setImageBitmap(AndroidUtils.getBitmapFromAsset(this.getAssets(), path));
            }
            catch (IOException e)
            {
                Log.e("ImageLoadError", "Can't load the image", e);
            }
        }
    }
}
