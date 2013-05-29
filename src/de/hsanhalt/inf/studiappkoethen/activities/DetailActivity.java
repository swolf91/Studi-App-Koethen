package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    /**
     * Der Index des Pfades zum Bild, das derzeitig angezeigt wird.
     * Der Pfad kann dann mit this.building.getImagePaths() geholt werden
     * und gilt innerhalb der Assets
     */
    Integer pictureIndex = null;
    /**
     * Beinhaltet die ImageView, die das Bild anzeigt.
     */
    ImageView imageView = null;
    /**
     * Beinhaltet das Gebaeude, das derzeitig angezeigt wird.
     */
    Building building = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        byte categorieID = this.getIntent().getByteExtra("category", (byte) -1);   // ruft die ID der Kategorie aus den uebergebenen Parametern ab
        byte buildingID = this.getIntent().getByteExtra("building", (byte) -1);   // ruft die ID des Gebaeudes aus den uebergebenen Parametern ab

        /*
         * Seite wird mit Inhalt gefuellt, wenn ID's gueltige Werte besitzen
         */
        if(categorieID > -1 && buildingID > -1)
        {
            BuildingCategory category = BuildingCategoryManager.getInstance().getCategory(categorieID);   //setzt Kategorie
            this.building = BuildingManager.getInstance().getBuilding(category, buildingID);    // setzt Gebaeude

            /*
             * Der folgende Code ueberprueft, ob Bilder vorhanden sind und entscheidet anhand dieser Information
             * welches Layout geladen werden soll.
             * Wenn nur ein Bild vorhanden ist, werden aus dem Bildlayout zusaetzlich alle Bildwechselpfeile ausgeblendet.
             */
            String imagePaths[] = building.getImagePaths();
            if(imagePaths.length != 0)
            {
                this.pictureIndex = 0;
                this.setContentView(R.layout.activity_detail_withimage);
                if(imagePaths.length == 1)
                {
                    ImageView arrow = (ImageView) this.findViewById(R.id.detail_arrow_left);
                    arrow.setVisibility(View.INVISIBLE);    // blendet Pfeilgrafik aus
                    arrow = (ImageView) this.findViewById(R.id.detail_arrow_right);
                    arrow.setVisibility(View.INVISIBLE);
                }
                try
                {
                    this.imageView = (ImageView) this.findViewById(R.id.detail_image);
                    this.imageView.setImageBitmap(AndroidUtils.getBitmapFromAsset(this.getAssets(), imagePaths[this.pictureIndex]));
                }
                catch (IOException e)
                {
                    Log.e("DetailActivityError", "Couldn't load image " + imagePaths[this.pictureIndex] + "!", e);
                }
            }
            else
            {
                this.setContentView(R.layout.activity_detail_withoutimage);
            }

//            TextView textView = (TextView) this.findViewById(R.id.detail_description);
//            this.setTextView(textView);
            this.setTextViews();
        }
    }

    /**
     * Diese Methode setzt die Beschreibung des Building-Objektes
     */
    private void setTextViews()
    {
        if(this.building != null)
        {
            TextView textView = (TextView) this.findViewById(R.id.detail_textView_name);
            textView.setText(this.building.getName());

            if(this.building.getStreet() != null && this.building.getHouseNumber() != null && this.building.getPostalCode() != null && this.building.getCity() != null)
            {
                textView = (TextView) this.findViewById(R.id.detail_textView_adress_headline);
                textView.setVisibility(View.VISIBLE);

                textView = (TextView) this.findViewById(R.id.detail_textView_adress);
                textView.setVisibility(View.VISIBLE);

                String value = this.building.getStreet() + " " + this.building.getHouseNumber() + "\n";
                value += this.building.getPostalCode() + " " + this.building.getCity();
                textView.setText(value);
            }

            if(this.building instanceof CollegeBuilding)
            {
                CollegeBuilding collegeBuilding = (CollegeBuilding) this.building;

                if(collegeBuilding.getNumberOfBuilding() != null)
                {
                    textView = (TextView) this.findViewById(R.id.detail_textView_buildingnumber_headline);
                    textView.setVisibility(View.VISIBLE);

                    textView = (TextView) this.findViewById(R.id.detail_textView_buildingnumber);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(collegeBuilding.getNumberOfBuilding().toString());
                }
                if(collegeBuilding.getNumberOfFaculty() != null)
                {
                    textView = (TextView) this.findViewById(R.id.detail_textView_facultynumber_headline);
                    textView.setVisibility(View.VISIBLE);

                    textView = (TextView) this.findViewById(R.id.detail_textView_facultynumber);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(collegeBuilding.getNumberOfFaculty().toString());
                }
            }

            if(this.building.getURL() != null)
            {
                textView = (TextView) this.findViewById(R.id.detail_textView_homepage_headline);
                textView.setVisibility(View.VISIBLE);

                textView = (TextView) this.findViewById(R.id.detail_textView_homepage);
                textView.setVisibility(View.VISIBLE);
                textView.setText(this.building.getURL().toString());
            }

            if(this.building.getDescription() != null)
            {
                textView = (TextView) this.findViewById(R.id.detail_textView_description_headline);
                textView.setVisibility(View.VISIBLE);

                textView = (TextView) this.findViewById(R.id.detail_textView_description);
                textView.setVisibility(View.VISIBLE);
                textView.setText(this.building.getDescription());
            }

//            textView.setText(text);
//            textView.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    /**
     * Diese Methode wird aufgerufen, wenn auf das Bild geklickt wird. Sie oeffnet die
     * ImageActivity und uebergibt den Pfad zum Bild als Parameter
     * @param view
     */
    public void onImageClick(View view)
    {
        if(this.imageView != null && view.getId() == R.id.detail_image)
        {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra("image", this.building.getImagePaths()[this.pictureIndex]);
            this.startActivity(intent);
        }
    }

    /**
     * Diese Methode wird aufgerufen wenn eine der Pfeilgrafiken gedrueckt wird.
     * Sie dient dazu zwischen verschiedenen Bildern zu wechseln.
     * @param view
     */
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

            /*
             * Wenn neuer Index ungleich des alten Indexes ist, wird innerhalb der if-Anweisung das Bild gewechselt.
             * Dies ist nur, damit ein gleiches Bild nicht nochmal geladen wird, man koennte aber darauf verzichen.
             */
            if(newIndex != this.pictureIndex)
            {
                this.pictureIndex = newIndex;
                try
                {
                    this.imageView.setImageBitmap(AndroidUtils.getBitmapFromAsset(this.getAssets(), this.building.getImagePaths()[this.pictureIndex]));
                }
                catch (IOException e)
                {
                    Log.e("DetailActivityError", "Couldn't load image " + this.building.getImagePaths()[this.pictureIndex] + "!", e);
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
            Intent intent  = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
