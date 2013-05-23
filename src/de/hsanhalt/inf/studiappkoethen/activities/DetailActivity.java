package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.util.xml.buildings.BuildingManager;

public class DetailActivity extends Activity
{
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
}
