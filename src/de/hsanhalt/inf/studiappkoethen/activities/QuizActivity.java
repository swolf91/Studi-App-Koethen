package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private List<Boolean> answers;
    private QuizManager quizManager;
    SharedPreferences quizPreferences ;    // Gibt die id des zuletzt geloesten Quizes zurueck.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_quiz);
        quizPreferences = getSharedPreferences("Quiz Status", MODE_PRIVATE);

        this.quizManager = QuizManager.getInstance();
        byte lastQuiz = (byte) quizPreferences.getInt("lastQuiz", -1);

        if(lastQuiz != -1)
        {
            // TODO load quiz!

            TextView textView = (TextView) this.findViewById(id.quiz_textView_noquiz);
            textView.setVisibility(View.GONE);

            // TODO status laden!
            Quiz quiz = this.quizManager.getQuiz(lastQuiz);
            this.load(quiz);

            textView = (TextView) this.findViewById(id.quiz_textView_question_headline);
            textView.setVisibility(View.VISIBLE);

            textView = (TextView) this.findViewById(id.quiz_textView_question);
            textView.setVisibility(View.VISIBLE);

            textView.setText(quiz.getQuestion(this.answers.size()).getQuestion());

            this.answers.add(false);
            this.save(quiz);
        }
    }

    private void load(Quiz quiz)
    {
        this.answers = new ArrayList<Boolean>(quiz.getNumberOfQuestions());

        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        DataInputStream dataInputStream = null;

        try
        {
            fileInputStream = this.openFileInput("quiz" + quiz.getID());
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            dataInputStream = new DataInputStream(bufferedInputStream);

            while(true)
            {
                this.answers.add(Boolean.valueOf(dataInputStream.readBoolean()));
            }
        }
        catch (FileNotFoundException ignored)
        {
            Log.d("QuizActivityDebug", "Can't found file quiz" + quiz.getID() + "!");
        }
        catch (Exception e)
        {
            Log.e("QuizActivityError", "Can't read FileInputStream from quiz" + quiz.getID() + "!", e);
        }
        finally
        {
            try
            {
                fileInputStream.close();
                bufferedInputStream.close();
                dataInputStream.close();
            }
            catch (Exception e)
            {
                Log.e("QuizActivityError", "Failed to load quiz status. Can't close files!", e);
            }
        }
    }

    private void save(Quiz quiz)
    {
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        DataOutputStream dataOutputStream = null;

        try
        {
            fileOutputStream = this.openFileOutput("quiz" + quiz.getID(), Context.MODE_PRIVATE);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            dataOutputStream = new DataOutputStream(bufferedOutputStream);

            for(Boolean bool : this.answers)
            {
                dataOutputStream.writeBoolean(bool);
            }
        }
        catch (Exception e)
        {
            Log.e("QuizActivity", "Can't write the quiz status for quiz" + quiz.getID() + "!", e);
        }
        finally
        {
            try
            {
                fileOutputStream.close();
                bufferedOutputStream.close();
                dataOutputStream.close();
            }
            catch (Exception e)
            {
                Log.e("QuizActivity", "Can't close the quiz save file quiz" + quiz.getID() + "!", e);
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
            menu.add(0, quiz.getID(), 0, quiz.getName());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        this.quizPreferences.edit().putInt("lastQuiz", item.getItemId()).commit();
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        this.recreate();
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