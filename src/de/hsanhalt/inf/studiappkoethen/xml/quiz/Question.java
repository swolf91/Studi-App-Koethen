package de.hsanhalt.inf.studiappkoethen.xml.quiz;

import java.util.List;

import de.hsanhalt.inf.studiappkoethen.xml.buildings.Building;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategory;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingCategoryManager;
import de.hsanhalt.inf.studiappkoethen.xml.buildings.BuildingManager;

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

    private final byte buildingCategory;

    private final byte building;

    public Question(byte id, byte buildingCategory, byte building, String question, String hint, String[] answers, List<Integer> correctAnswers, String result)
    {
        this.id = id;
        this.question = question;
        this.hint = hint;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.result = result;

        this.buildingCategory = buildingCategory;
        this.building = building;
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

    public Building getBuilding()
    {
        BuildingCategory category = BuildingCategoryManager.getInstance().getCategory(this.buildingCategory);
        return BuildingManager.getInstance().getBuilding(category, this.building);
    }
}
