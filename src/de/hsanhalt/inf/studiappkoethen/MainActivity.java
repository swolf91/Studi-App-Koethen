package de.hsanhalt.inf.studiappkoethen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import de.hsanhalt.inf.studiappkoethen.utils.BuildingCategoryManager;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



        BuildingCategoryManager buildingCategoryManager = BuildingCategoryManager.getInstance();
        if(!buildingCategoryManager.hasContent())
        {
            buildingCategoryManager.loadXML(this.getResources().openRawResource(R.xml.categories));
        }
        buildingCategoryManager.getCategory(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
