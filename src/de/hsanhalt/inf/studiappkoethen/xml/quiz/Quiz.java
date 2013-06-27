package de.hsanhalt.inf.studiappkoethen.xml.quiz;

public class Quiz
{
    /**
     * ID des Quizzes
     */
    private final byte id;

    /**
     * Array, dass die ganzen Fragen beinhaltet
     */
    private final Question[] questions;

    /**
     * Name des Quizzes
     */
    private final String name;

    private final String startMessage;

    public Quiz(byte id, String name, Question[] questions, String startMessage)
    {
        this.id = id;
        this.questions = questions;
        this.startMessage = startMessage;
        this.name = name;
    }

    public byte getID()
    {
        return this.id;
    }

    public Question getQuestion(int id)
    {
        return this.questions[id];
    }

    public int getNumberOfQuestions()
    {
        return this.questions.length;
    }

    public String getStartMessage()
    {
        return this.startMessage;
    }

    public String getName()
    {
        return this.name;
    }
}
