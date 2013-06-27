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

    /**
     * Startnachricht die im Quiz angezeigt wird
     */
    private final String startMessage;

    /**
     * Konstruktor der Quiz erstellt
     * @param id - ID
     * @param name - Name des Quizzes
     * @param questions - Fragen die im Quiz gestellt werden
     * @param startMessage - Startnachricht
     */
    public Quiz(byte id, String name, Question[] questions, String startMessage)
    {
        this.id = id;
        this.questions = questions;
        this.startMessage = startMessage;
        this.name = name;
    }

    /**
     * Gibt die ID des Quizzes zurueck
     * @return
     */
    public byte getID()
    {
        return this.id;
    }

    /**
     * Gibt die Frage des Quizzes zurueck
     * @param id - Id der Frage die zurueckgegeben werden soll
     * @return
     */
    public Question getQuestion(int id)
    {
        return this.questions[id];
    }

    /**
     * Gibt zurueck, aus wie vielen Fragen ein Quiz besteht.
     * @return
     */
    public int getNumberOfQuestions()
    {
        return this.questions.length;
    }

    /**
     * Gibt die Startnachricht des Quizzes zurueck
     * @return
     */
    public String getStartMessage()
    {
        return this.startMessage;
    }

    /**
     * Gibt den Namen des Quizzes zurueck
     * @return
     */
    public String getName()
    {
        return this.name;
    }
}
