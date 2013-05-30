package de.hsanhalt.inf.studiappkoethen.xml.quiz;

public class Quiz
{
    private final byte id;

    private final Question[] questions;

    private final String startMessage;

    public Quiz(byte id, Question[] questions, String startMessage)
    {
        this.id = id;
        this.questions = questions;
        this.startMessage = startMessage;
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
}
