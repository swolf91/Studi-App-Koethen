package de.hsanhalt.inf.studiappkoethen.xml.quiz;

public class Quiz
{
    private final byte id;

    private Question[] questions;

    public Quiz(byte id, int numberOfQuestions)
    {
        this.id = id;
        this.questions = new Question[numberOfQuestions];
    }

    public Quiz(byte id, Question[] questions)
    {
        this.id = id;
        this.questions = questions;
    }

    public byte getID()
    {
        return this.id;
    }

    public Question getQuestion(int id)
    {
        return this.questions[id];
    }

    public void addQuestion(Question question)
    {
        this.questions[question.getID()] = question;
    }

    public int getNumberOfQuestions()
    {
        return this.questions.length;
    }
}
