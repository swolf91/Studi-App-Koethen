package de.hsanhalt.inf.studiappkoethen.xml.quiz;

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
    private final int rightAnswer;

    /**
     * Der Text, der nach Beantwortung der Frage kommen soll.
     */
    private final String result;

    /**
     * Ein Hinweis zur Loesung der Frage
     */
    private final String hint;

    public Question(byte id, String question, String hint, String[] answers, int rightAnswer, String result)
    {
        this.id = id;
        this.question = question;
        this.hint = hint;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
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

    public int getRightAnswerID()
    {
        return this.rightAnswer;
    }

    public String getResult()
    {
        return this.result;
    }
}