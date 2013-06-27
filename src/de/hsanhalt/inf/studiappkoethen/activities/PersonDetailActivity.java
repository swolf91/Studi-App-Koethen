package de.hsanhalt.inf.studiappkoethen.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.R.layout;
import de.hsanhalt.inf.studiappkoethen.R.string;
import de.hsanhalt.inf.studiappkoethen.xml.persons.Person;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.persons.PersonManager;

/**
 * Detailanzeige Activity fuer die Personen. 
 */
public class PersonDetailActivity extends Activity
{
    private Person person = null;
    

    /**
     * Konstruiert das passende Personen Objekt fuer die gewuenschte Auswahl.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_person_detail);

        byte categorieID = this.getIntent()
                               .getByteExtra("category", (byte)-1);   // ruft die ID der Kategorie aus den uebergebenen Parametern ab
        byte personID = this.getIntent()
                            .getByteExtra("person", (byte)-1);   // ruft die ID der Person aus den uebergebenen Parametern ab

        LinearLayout linearLayout = (LinearLayout)this.findViewById(id.detail_linearlayout);

        if (categorieID < 0 || personID < 0)
        {
            linearLayout.addView(this.createTextView(this.getResources().getString(string.detail_description), true));
            return;
        }

        PersonCategoryManager personCategoryManager = PersonCategoryManager.getInstance();
        PersonManager personManager = PersonManager.getInstance();
        this.person = personManager.getPerson(personCategoryManager.getCategory(categorieID), personID);

        this.fillLinearLayout(linearLayout);
    }

    private void fillLinearLayout(LinearLayout linearLayout)
    {
        Resources resources = this.getResources();

        if (this.person.getState() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_state), true));
            linearLayout.addView(this.createTextView(this.person.getState(), false));
        }

        linearLayout.addView(this.createTextView(resources.getString(string.detail_person_name), true));
        linearLayout.addView(this.createTextView(this.person.getName(), false));

        linearLayout.addView(this.createTextView(resources.getString(string.detail_person_surname), true));
        linearLayout.addView(this.createTextView(this.person.getSurname(), false));
        
        if (this.person.getSpecialField() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_specialfield), true));
            linearLayout.addView(this.createTextView(this.person.getSpecialField(), false));
        }
        
        if (this.person.getStreet() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_street), true));
            linearLayout.addView(this.createTextView(this.person.getStreet(), false));
        }        
        if (this.person.gethouseNumber() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_buildingnumber), true));
            linearLayout.addView(this.createTextView(this.person.gethouseNumber(), false));
        }        
        if (this.person.getPostalCode() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_postalcode), true));
            linearLayout.addView(this.createTextView(this.person.getPostalCode(), false));
        }        
        if (this.person.getCity() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_city), true));
            linearLayout.addView(this.createTextView(this.person.getCity(), false));
        }        
        if (this.person.getBuilding() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_buildings), true));
            linearLayout.addView(this.createTextView(this.person.getBuilding(), false));
        }        
        if (this.person.getRoom() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_room), true));
            linearLayout.addView(this.createTextView(this.person.getRoom(), false));
        }        
        if (this.person.getDescription() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_description), true));
            linearLayout.addView(this.createTextView(this.person.getDescription(), false));
        }        
        if (this.person.getProfession() != null)
        {
        	linearLayout.addView(this.createTextView(resources.getString(string.detail_person_profession), true));
            linearLayout.addView(this.createTextView(this.person.getProfession(), false));
        }        
        if (this.person.getModuls() != null)
        {	
       
        	linearLayout.addView(this.createTextView(resources.getString(string.detail_person_modules), true));
        	String [] tmpModules = this.person.getModuls();
        
        	for(int i=0;i<tmpModules.length;i++){
	             linearLayout.addView(this.createTextView(tmpModules[i], false));
        	}
        }        
        if (this.person.getResponsibilities() != null)
        {
        	linearLayout.addView(this.createTextView(resources.getString(string.detail_person_responsibility), true));
            String [] tmpResponsibilities = this.person.getResponsibilities();
            
            	for(int i=0;i<tmpResponsibilities.length;i++){
    	            
    	            linearLayout.addView(this.createTextView(tmpResponsibilities[i], false));
            	}
        }        
        if (this.person.getTalkTime() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_talktime), true));
            linearLayout.addView(this.createTextView(this.person.getTalkTime(), false));
        }        
        if (this.person.getPhone() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_phone), true));
            linearLayout.addView(this.createTextView(this.person.getPhone(), false));
        }        
        if (this.person.getEmail() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_email), true));
            linearLayout.addView(this.createTextView(this.person.getEmail(), false));
        }        
        if (this.person.getUrl() != null)
        {
            linearLayout.addView(this.createTextView(resources.getString(string.detail_person_url), true));
            linearLayout.addView(this.createTextView(this.person.getUrl().toString(), false));
        }        
        
    }

    private TextView createTextView(String label, boolean headline)
    {
        int padding = headline ? 10 : 20;
        TextView textView = new TextView(this);
        textView.setText(label);
        textView.setPadding(padding, 0, padding, 0);

        if (headline)
        {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }

        return textView;
    }

    /**
     * Beschreibt die Standartfunktionen (Schliessen und Startseite) der Personen Activity.
     *
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
            Intent intent = new Intent(this, MainActivity.class);
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
