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
 * Detailanzeige Activity fuer die Personen. *
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
        this.setContentView(layout.activity_person_detail_withimage);

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

        this.fillScrollView(linearLayout);
    }

    private void fillScrollView(LinearLayout linearLayout)
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
