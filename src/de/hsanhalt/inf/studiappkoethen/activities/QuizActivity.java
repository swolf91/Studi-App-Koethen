package de.hsanhalt.inf.studiappkoethen.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import de.hsanhalt.inf.studiappkoethen.R;
import de.hsanhalt.inf.studiappkoethen.R.drawable;
import de.hsanhalt.inf.studiappkoethen.R.id;
import de.hsanhalt.inf.studiappkoethen.R.string;
import de.hsanhalt.inf.studiappkoethen.util.FilterBundle;
import de.hsanhalt.inf.studiappkoethen.util.quiz.QuizState;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.Question;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.Quiz;
import de.hsanhalt.inf.studiappkoethen.xml.quiz.QuizManager;

public class QuizActivity extends Activity
{
    /**
     * beinhaltet ob die gegebenen Antworten richtig oder falsch waren
     */
    private List<Boolean> answers;
    /**
     * beinhaltet das Quiz, dass gerade abgearbeitet wird
     */
    private Quiz quiz;
    /**
     * beinhaltet die Station, in dem sich das Quiz gerade befindet
     */
    private QuizState state;
    /**
     * liest die zuletzt abgearbeitete quizID aus.
     */
    private SharedPreferences quizPreferences ;    // Gibt die id des zuletzt geloesten Quizes zurueck.

    /**
     * OnButtonClickListener, der Klicks auf Buttons verwaltet
     */
    private OnClickListener OnButtonClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(v.getId() == QuizState.WELCOME_MESSAGE.hashCode() * Button.class.hashCode() || v.getId() == QuizState.ANALYSIS.hashCode() * Button.class.hashCode())
            {
                nextStage(0);
            }
            else if(v.getId() == QuizState.RESULT.hashCode() * Button.class.hashCode())
            {
                nextStage(answers.size() - 1);
            }
            else if(v.getId() == QuizState.QUESTION.hashCode() * Button.class.hashCode() * String.class.hashCode())
            {
                Toast.makeText(getBaseContext(), quiz.getQuestion(answers.size()).getHint(), Toast.LENGTH_LONG).show();
            }
            else if(v.getId() == QuizState.QUESTION.hashCode() * Button.class.hashCode() * GoogleMapsActivity.class.hashCode())
            {
                Building building = quiz.getQuestion(answers.size()).getBuilding();
                FilterBundle filterBundle = new FilterBundle(building.getBuildingCategory().getID());
                filterBundle.addNewBuilding(building.getID());

                Intent intent = new Intent(getBaseContext(), GoogleMapsActivity.class);
                intent.putExtras(filterBundle.getBundle());
                startActivity(intent);
            }
            else
            {
                int amount = v.getId() - (QuizState.QUESTION.hashCode() * Button.class.hashCode());
                Question question = quiz.getQuestion(answers.size());
                if(amount >= 0 && amount < question.getAnswers().length)
                {
                    boolean result = question.isCorrectAnswer(amount);
                    Toast.makeText(getBaseContext(), result ? "richtig" : "falsch", Toast.LENGTH_SHORT).show();
                    answers.add(result);
                    nextStage(answers.size() - 1);
                }
            }
        }
    };

    /**
     * Erstellt das Layout des Quizes.
     * Dieses wird aus der State die anzuzeigen wird erzeugt. Also die State gibt die Information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_quiz);

        this.quizPreferences = this.getSharedPreferences("Quiz Status", MODE_PRIVATE);
        byte lastQuiz = (byte) quizPreferences.getInt("lastQuiz", -1);

        LinearLayout linearLayout = (LinearLayout) this.findViewById(id.quiz_linearlayout_mainlayout);

        TextView messageView = new TextView(this);
        messageView.setPadding(10, 10, 10, 10);
        messageView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        if(lastQuiz != -1)                                                                     // TODO refacotoring (minimize code duplication)
        {
            this.quiz = QuizManager.getInstance().getQuiz(lastQuiz);
            this.state = this.load();

            if(this.state == QuizState.WELCOME_MESSAGE) // Layout wenn sich Quiz im Willkommensbildschirm befindet
            {
                MarginLayoutParams params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 75);
                messageView.setLayoutParams(new LinearLayout.LayoutParams(params));
                messageView.setText(quiz.getStartMessage());
                linearLayout.addView(messageView);

                params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 75);
                params.setMargins(10, -80, 10, 10);
                Button button = new Button(this);
                button.setBackgroundDrawable(this.getResources().getDrawable(drawable.button));
                button.setTextColor(Color.WHITE);
                button.setLayoutParams(new LinearLayout.LayoutParams(params));
                button.setText(this.getResources().getString(string.quiz_button_startbutton));
                button.setOnClickListener(this.OnButtonClickListener);
                button.setId(this.state.hashCode() * Button.class.hashCode());
                linearLayout.addView(button);
            }
            else if(this.state == QuizState.QUESTION) // Layout wenn sich Quiz im Fragebildschirm befindet
            {
                Question question = this.quiz.getQuestion(this.answers.size());

                boolean hasBuilding = question.getBuilding() != null;
                boolean hasHint = question.getHint() != null;

                if(hasBuilding || hasHint)  // Erstellt extra-Bereich fuer Map- und Hinweisbutton
                {
                    LinearLayout linearLayoutInner = new LinearLayout(this);
                    linearLayoutInner.setPadding(0,0,0,0);
                    linearLayoutInner.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayoutInner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75));
                    linearLayout.addView(linearLayoutInner);

                    Button buildingButton = new Button(this);
                    MarginLayoutParams params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 75);
                    params.setMargins(10, 5, 85, 5);
                    buildingButton.setBackgroundDrawable(this.getResources().getDrawable(drawable.button));
                    buildingButton.setTextColor(Color.WHITE);
                    buildingButton.setLayoutParams(new LinearLayout.LayoutParams(params));
                    linearLayoutInner.addView(buildingButton);

                    if(hasBuilding)
                    {
                        buildingButton.setText(this.getResources().getString(string.detail_mapbutton));
                        buildingButton.setId(state.hashCode() * Button.class.hashCode() * GoogleMapsActivity.class.hashCode());
                        buildingButton.setOnClickListener(this.OnButtonClickListener);
                    }
                    else
                    {
                        buildingButton.setVisibility(View.INVISIBLE);
                    }
                    if(hasHint)
                    {
                        Button button = new Button(this);
                        params = new MarginLayoutParams(60, 60);
                        params.setMargins(-75, 0, 0, 0);
                        button.setLayoutParams(new LinearLayout.LayoutParams(params));
                        button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.quiz_hint_button));
                        button.setId(state.hashCode() * Button.class.hashCode() * String.class.hashCode());
                        button.setOnClickListener(this.OnButtonClickListener);
                        linearLayoutInner.addView(button);
                    }
                }

                messageView.setText(question.getQuestion());
                messageView.setTypeface(Typeface.DEFAULT_BOLD);
                messageView.setTextSize(18f);
                linearLayout.addView(messageView);

                List<Button> buttonlist = new ArrayList<Button>(question.getAnswers().length);
                int i = 0;
                for(String answer : question.getAnswers())
                {
                    Button button = new Button(this);
                    button.setVisibility(View.VISIBLE);
                    button.setText(answer);
                    button.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    button.setHeight(50);
                    button.setId(state.hashCode() * Button.class.hashCode() + (i++));
                    button.setOnClickListener(this.OnButtonClickListener);
                    buttonlist.add(button);
                }
                Random random = new Random();
                while(!buttonlist.isEmpty())
                {
                    Button button = buttonlist.get(random.nextInt(buttonlist.size()));
                    linearLayout.addView(button);
                    buttonlist.remove(button);
                }
            }
            else if(this.state == QuizState.RESULT) // Layout wenn sich Quiz auf der Fragenauswertungsseite befindet
            {
                MarginLayoutParams params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 75);
                messageView.setLayoutParams(new LinearLayout.LayoutParams(params));
                messageView.setText(quiz.getQuestion(this.answers.size() - 1).getResult());
                linearLayout.addView(messageView);

                params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 75);
                params.setMargins(10, -80, 10, 10);
                Button button = new Button(this);
                button.setBackgroundDrawable(this.getResources().getDrawable(drawable.button));
                button.setTextColor(Color.WHITE);
                button.setLayoutParams(new LinearLayout.LayoutParams(params));
                button.setText(this.getResources().getString(string.quiz_button_nextquestion));
                button.setOnClickListener(this.OnButtonClickListener);
                button.setId(this.state.hashCode() * Button.class.hashCode());
                linearLayout.addView(button);
            }
            else if(this.state == QuizState.ANALYSIS)  // Layout fuer die Auswertungsseite wenn Quiz beendet wurde
            {
                int numberOfCorrectAnswers = 0;
                for(boolean bool : this.answers)
                {
                    if(bool)
                    {
                        numberOfCorrectAnswers++;
                    }
                }
                float percentage = (float)numberOfCorrectAnswers / (float)this.quiz.getNumberOfQuestions();

                MarginLayoutParams params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 75);
                messageView.setLayoutParams(new LinearLayout.LayoutParams(params));
                linearLayout.addView(messageView);

                String message;
                if(numberOfCorrectAnswers >= this.quiz.getNumberOfQuestions())
                {
                    message = this.getResources().getString(string.quiz_analysis_perfekt);
                }
                else if(percentage > 0.8f)
                {
                    message = this.getResources().getString(string.quiz_analysis_verygood);
                }
                else if(percentage > 0.6f)
                {
                    message = this.getResources().getString(string.quiz_analysis_good);
                }
                else if(percentage > 0.4f)
                {
                    message = this.getResources().getString(string.quiz_analysis_medium);
                }
                else if(percentage > 0.2f)
                {
                    message = this.getResources().getString(string.quiz_analysis_bad);
                }
                else
                {
                    message = this.getResources().getString(string.quiz_analysis_verybad);
                }
                message = message.replace("=p", ((int)(100 * percentage)) + "");
                message = message.replace("=q", this.quiz.getNumberOfQuestions() + "");
                message = message.replace("=c", numberOfCorrectAnswers + "");
                messageView.setText(message);

                params = new MarginLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 75);
                params.setMargins(10, -80, 10, 10);
                Button button = new Button(this);
                button.setBackgroundDrawable(this.getResources().getDrawable(drawable.button));
                button.setTextColor(Color.WHITE);
                button.setLayoutParams(new LinearLayout.LayoutParams(params));
                button.setText(this.getResources().getString(string.quiz_button_exitquiz));
                button.setOnClickListener(this.OnButtonClickListener);
                button.setId(this.state.hashCode() * Button.class.hashCode());
                linearLayout.addView(button);
            }
        }
        else    // Wird aufgerufen, wenn kein Quiz ausgewaehlt ist!
        {
            ScrollView scrollView = new ScrollView(this);
            messageView.setText(this.getResources().getString(string.quiz_startmsg));
            scrollView.addView(messageView);
            linearLayout.addView(scrollView);
        }
    }

    /**
     * Methode laedt das Quiz
     * @return
     */
    private QuizState load()
    {
        this.answers = new ArrayList<Boolean>(quiz.getNumberOfQuestions());

        FileInputStream fileInputStream = null;
        QuizState state = QuizState.WELCOME_MESSAGE;
        try
        {
            try
            {
                fileInputStream = this.openFileInput("quiz" + quiz.getID());
            }
            catch (FileNotFoundException ignored)
            {
                return state;
            }
            state = QuizState.getByID(fileInputStream.read());
            while(fileInputStream.available() != 0)
            {
                this.answers.add(Boolean.valueOf(fileInputStream.read() != 0));
            }
            if(state == null && this.answers.isEmpty())
            {
                state = QuizState.WELCOME_MESSAGE;
            }
            Log.d("QuizActivity", "Quiz-file: quiz" + quiz.getID() + " was parsed successfully. Found " + this.answers.size() + " answers.");
        }
        catch(Exception e)
        {
             Log.e("QuizActivityError", "Couldn't load the last quiz state!", e);
        }
        finally
        {
            try
            {
                fileInputStream.close();
            }
            catch (Exception e)
            {
                Log.e("QuizActivityError", "Couldn't close the quiz file: quiz" + quiz.getID() + "!", e);
            }
        }
        return state;
    }

    /**
     * Methode speichert Quiz ab
     * @param state
     */
    private void save(QuizState state)
    {
        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = this.openFileOutput("quiz" + quiz.getID(), Context.MODE_PRIVATE);
            if(state != null)
            {
                fileOutputStream.write(state.getID());
            }
            for(Boolean bool : this.answers)
            {
                fileOutputStream.write(bool ? 1 : 0);
            }
        }
        catch (Exception e)
        {
            Log.e("QuizActivityError", "Couldn't save the current quiz state: quiz" + quiz.getID() + "!", e);
        }
        finally
        {
            try
            {
                fileOutputStream.close();
            }
            catch (Exception e)
            {
                Log.e("QuizActivityError", "Couldn't close the quiz file: quiz" + quiz.getID() + "!", e);
            }
        }
    }

    /**
     * Ruft die naechste abzuarbeitende Seite im Quiz auf
     * @param questionNumber
     */
    public void nextStage(int questionNumber)
    {
        this.state = QuizState.getNextState(this.state, this.quiz, questionNumber);
        if(this.state == null)
        {
            this.answers.clear();
            this.quizPreferences.edit().remove("lastQuiz").commit();
        }
        this.save(this.state);
        this.recreate();
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
        menu.setHeaderTitle(this.getResources().getString(string.quiz_context_choosequiz));
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