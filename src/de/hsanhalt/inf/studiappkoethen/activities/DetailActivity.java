package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.AndroidUtils;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.CollegeBuilding;

public class DetailActivity extends Activity
{
    Integer pictureIndex = null;
    ImageView imageView = null;
    Building building = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        byte categorieID = this.getIntent().getByteExtra("category", (byte) -1);
        byte buildingID = this.getIntent().getByteExtra("building", (byte) -1);

        if(categorieID != -1 && buildingID != -1)
        {
            BuildingCategory category = BuildingCategoryManager.getInstance().getCategory(categorieID);
            this.building = BuildingManager.getInstance().getBuilding(category, buildingID);

            String imagePaths[] = building.getImagePaths();
            if(imagePaths.length != 0)
            {
                this.pictureIndex = 0;
                this.setContentView(R.layout.activity_detail_withimage);
                try
                {
                    this.imageView = (ImageView) this.findViewById(R.id.detail_image);
                    this.imageView.setImageBitmap(AndroidUtils
                                                      .getBitmapFromAsset(this.getAssets(), imagePaths[this.pictureIndex]));
                }
                catch (IOException e)
                {
                    Log.e("ImageError", "Couldn't load image " + imagePaths[0] + "!", e);
                }
            }
            else
            {
                this.setContentView(R.layout.activity_detail_withoutimage);
            }

            TextView textView = (TextView) this.findViewById(R.id.detail_description);
            this.setTextView(textView);
        }
    }

    private void setTextView(TextView textView)
    {
        if(this.building != null)
        {
            String text = "";

            String value = "Name:\n\t" + this.building.getName() + "\n\n";
            text += value;

            if(this.building.getStreet() != null && this.building.getHouseNumber() != null && this.building.getPostalCode() != null && this.building.getCity() != null)
            {
                value = "Adresse:\n\t";
                value += this.building.getStreet() + " " + this.building.getHouseNumber() + "\n\t";
                value += this.building.getPostalCode() + " " + this.building.getCity() + "\n\n";
                text += value;
            }

            if(this.building instanceof CollegeBuilding)
            {
                CollegeBuilding collegeBuilding = (CollegeBuilding) this.building;

                if(collegeBuilding.getNumberOfBuilding() != null)
                {
                    value = "Gebaeudenummer:\n\t" + collegeBuilding.getNumberOfBuilding() + "\n\n";
                    text += value;
                }
                if(collegeBuilding.getNumberOfFaculty() != null)
                {
                    value = "Fachbereichsnummer\n\t" + collegeBuilding.getNumberOfFaculty() + "\n\n";
                    text += value;
                }
            }

            if(this.building.getURL() != null)
            {
                value = "Homepage:\n\t" + this.building.getURL() + "\n\n";
                text += value;
            }

            if(this.building.getDescription() != null)
            {
                value = "Beschreibung:\n" + this.building.getDescription();
                text += value;
            }

            textView.setText(text);
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    public void onImageClick(View view)
    {
        if(this.imageView != null && view.getId() == R.id.detail_image)
        {
//            Toast.makeText(this, "Image was clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ImageActivity.class);
            String path = this.building.getImagePaths()[this.pictureIndex];
            intent.putExtra("image", path);
            this.startActivity(intent);
        }
    }

    public void onImageChange(View view)
    {
        if(this.pictureIndex != null && this.imageView != null && this.building != null)
        {
            int newIndex = this.pictureIndex;
            int imageLength = this.building.getImagePaths().length;

            switch (view.getId())
            {
                case R.id.detail_arrow_left:
                    newIndex--;
                    break;
                case R.id.detail_arrow_right:
                    newIndex++;
                    break;
                default: return;
            }

            if(newIndex < 0)
            {
                newIndex = imageLength - 1;
            }
            else if(newIndex >= imageLength)
            {
                newIndex = 0;
            }

            if(newIndex != this.pictureIndex)
            {
                this.pictureIndex = newIndex;
                try
                {
                    this.imageView.setImageBitmap(AndroidUtils.getBitmapFromAsset(this.getAssets(), this.building.getImagePaths()[this.pictureIndex]));
                }
                catch (IOException e)
                {
                    Log.e("ImageError", "Couldn't load image " + this.building.getImagePaths()[this.pictureIndex] + "!", e);
                }
            }
        }
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
