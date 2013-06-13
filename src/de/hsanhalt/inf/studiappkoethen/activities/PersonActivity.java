package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.IOException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.util.AndroidUtils;
import de.hsanhalt.inf.studiappkoethen.util.FilterBundle;
import de.hsanhalt.inf.studiappkoethen.xml.persons.Person;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategory;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonManager;

public class PersonActivity extends Activity
{
	Person person = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		byte categorieID = this.getIntent().getByteExtra("category", (byte) -1);   // ruft die ID der Kategorie aus den uebergebenen Parametern ab
        byte personID = this.getIntent().getByteExtra("person", (byte) -1);   // ruft die ID der Person aus den uebergebenen Parametern ab
	}

	private void setTextViews()
	{
		 if(this.person != null)
	        {
	            TextView textView = (TextView) this.findViewById(R.id.detail_textView_name);
	            textView.setText(this.person.getName());

	            if(this.person.getStreet() != null && this.person.gethouseNumber() != null && this.person.getPostalCode() != null && this.person.getCity() != null)
	            {
	                textView = (TextView) this.findViewById(R.id.detail_textView_adress_headline);
	                textView.setVisibility(View.VISIBLE);

	                textView = (TextView) this.findViewById(R.id.detail_textView_adress);
	                textView.setVisibility(View.VISIBLE);

	                String value = this.person.getStreet() + " " + this.person.gethouseNumber() + "\n";
	                value += this.person.getPostalCode() + " " + this.person.getCity();
	                textView.setText(value);
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
