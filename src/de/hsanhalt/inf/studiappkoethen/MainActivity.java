package de.hsanhalt.inf.studiappkoethen;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import de.hsanhalt.inf.studiappkoethen.utils.XmlParser;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        if(!XmlParser.isCreated())
        {

            XmlParser xmlParser = XmlParser.getInstance();
            AssetManager assets = this.getAssets();

            try
            {
                xmlParser.parse(assets.open("xml/categories.xml"));
            }
            catch (IOException e)
            {
                Log.e("FileException", "Can't parse file categories.xml from assets", e);
            }

            //xmlParser.parse(assets.open("xml/"));
            //TODO alle xml-Dateien laden.
        }


//        try
//        {
//            BuildingCategoryManager buildingCategoryManager = BuildingCategoryManager.getInstance();
//            if (!buildingCategoryManager.hasContent())
//            {
//                buildingCategoryManager.loadXML(this.getAssets().open("xml/categories.xml"));
//                //buildingCategoryManager.loadXML(this.getResources().openRawResource(R.assets.categories));
//            }
//            buildingCategoryManager.getCategory(1);
//        }
//        catch (Exception e)
//        {
//            Log.e("ResourceError", "Can't load the Resource: categories.xml", e);
//        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
