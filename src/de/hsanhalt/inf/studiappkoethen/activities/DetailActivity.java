package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import de.hsanhalt.inf.studiappkoethen.R;
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

            Toast.makeText(this, "Gebaeude: " + building.getName(), Toast.LENGTH_LONG).show();
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
