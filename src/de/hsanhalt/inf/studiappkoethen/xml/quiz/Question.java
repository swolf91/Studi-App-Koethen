package de.hsanhalt.inf.studiappkoethen.xml.quiz;

import java.util.List;

public class Question
{
    /**
     * Die ID der Frage innerhalb des Quizes (Rangfolge)
     */
    private final byte id;

    /**
     * Die Frage
     */
    private final String question;

    /**
     * Die Antwortmoeglichkeiten
     */
    private final String[] answers;

    /**
     * die ID der richtigen Antwort im answer-Array
     */
    private final List<Integer> correctAnswers;

    /**
     * Der Text, der nach Beantwortung der Frage kommen soll.
     */
    private final String result;

    /**
     * Ein Hinweis zur Loesung der Frage
     */
    private final String hint;

    public Question(byte id, String question, String hint, String[] answers, List<Integer> correctAnswers, String result)
    {
        this.id = id;
        this.question = question;
        this.hint = hint;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.result = result;
    }

    public byte getID()
    {
        return this.id;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String getHint()
    {
        return this.hint;
    }

    public String[] getAnswers()
    {
        return this.answers;
    }

    public boolean isCorrectAnswer(int id)
    {
        return this.correctAnswers.contains(id);
    }

    public String getResult()
    {
        return this.result;
    }
}
