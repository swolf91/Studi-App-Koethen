package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.R.menu;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;

public class DetailActivity extends Activity
{
//    public void onButtonClick(View view)
//    {
//        switch (view.getId())
//        {
//        case R.id.btn_googlemaps:
//            startActivity(new Intent(this, GoogleMapsActivity.class));
//            break;
//        case R.id.btn_koethen:
//            Intent intent = new Intent(this, DetailActivity.class);
//            this.startActivity(intent);
//            break;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);

        byte categorieID = this.getIntent().getByteExtra("categorie", (byte) -1);
        byte buildingID = this.getIntent().getByteExtra("building", (byte) -1);

        if(categorieID != -1 && buildingID != -1)
        {
            BuildingCategory category = BuildingCategoryManager.getInstance().getCategory(categorieID);
            Building building = BuildingManager.getInstance().getBuilding(category, buildingID);

            String imagePaths[] = building.getImagePaths();
            if(imagePaths.length != 0)
            {
                try
                {
                    ImageView imageView = (ImageView) this.findViewById(R.id.detail_imageView);
                    imageView.setImageBitmap(this.getBitmapFromAsset(imagePaths[0]));
                }
                catch (IOException e)
                {
                    Log.e("ImageError", "Couldn't load image " + imagePaths[0] + "!", e);
                }
            }

            TextView textView = (TextView) this.findViewById(id.detail_textView);
            textView.setText(
                "Name: " + building.getName() +  "\n" +
                "Beschreibung: " + building.getDescription()
            );

            Toast.makeText(this, "Gebaeude: " + building.getName(), Toast.LENGTH_LONG).show();
        }
    }

    public void imageClick(View view)
    {
        Toast.makeText(this, "In Zukunft wird das Bild gross angezeigt ;)", Toast.LENGTH_LONG).show();
    }

    private Bitmap getBitmapFromAsset(String path) throws IOException
    {
        AssetManager assets = this.getAssets();
        InputStream inputStream = assets.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_close:
            moveTaskToBack(true);
            return true;

        case R.id.action_main:
            startActivity(new Intent(this, MainActivity.class));
            return true;

//        case R.id.action_list:
//            startActivity(new Intent(this, ExpandableListActivity.class));
//            return true;


        default:
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

}
