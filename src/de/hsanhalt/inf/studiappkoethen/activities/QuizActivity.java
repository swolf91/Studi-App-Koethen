package de.hsanhalt.inf.studiappkoethen.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import de.hsanhalt.inf.studiappkoethen.R;
import android.app.Activity;
import android.os.Bundle;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.Quiz;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.QuizManager;

public class QuizActivity extends Activity
{
    SharedPreferences quizPreferences ;    // Gibt die id des zuletzt geloesten Quizes zurueck.


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_quiz);
        quizPreferences = getSharedPreferences("Quiz Status", MODE_PRIVATE);
        int lastQuiz = quizPreferences.getInt("lastQuiz", -1);

        if(lastQuiz != -1)
        {
            // TODO load quiz!

            TextView textView = (TextView) this.findViewById(id.quiz_textView_noquiz);
            textView.setVisibility(View.GONE);

            textView = (TextView) this.findViewById(id.quiz_textView_question_headline);
            textView.setVisibility(View.VISIBLE);

            textView = (TextView) this.findViewById(id.quiz_textView_question);
            textView.setVisibility(View.VISIBLE);
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

        case id.action_select_quiz:
            View view = this.findViewById(id.action_select_quiz);
            this.registerForContextMenu(view);
            this.openContextMenu(view);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        QuizManager quizManager = QuizManager.getInstance();
        menu.setHeaderTitle("Quiz Auswahl");
        for(Quiz quiz : quizManager.getQuizs())
        {
            menu.add(0, v.getId(), 0, quiz.getName());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }
}