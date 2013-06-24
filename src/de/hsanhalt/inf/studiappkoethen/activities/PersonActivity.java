package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.xml.persons.Person;

/**
 * Detailanzeige Activity fuer die Personen. *
 */
public class PersonActivity extends Activity
{
	Person person = null;
	/**
	 * Konstruiert das passende Personen Objekt fuer die gewuenschte Auswahl.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		byte categorieID = this.getIntent().getByteExtra("category", (byte) -1);   // ruft die ID der Kategorie aus den uebergebenen Parametern ab
        byte personID = this.getIntent().getByteExtra("person", (byte) -1);   // ruft die ID der Person aus den uebergebenen Parametern ab
	}
	/**
	 * Fuegt den Text aus der Personen XML in die entsprechenden Views zum anzeigen ein.
	 */
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
	/**
	 * Beschreibt die Standartfunktionen (Schliessen und Startseite) der Personen Activity.
	 * @return Ist true, wenn eine Ausfuehrung erfolgreich war.
	 */
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

//        case R.id.action_list: //TODO never used!?
//            startActivity(new Intent(this, ExpandableListActivity.class));
//            return true;


        default:
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //TODO never used? delete?!
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

}
