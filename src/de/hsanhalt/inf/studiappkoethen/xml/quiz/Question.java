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

    /**
     * Die Gebaeudekategorie vom Gebaeude, dass zur Frage gehoert
     */
    private final byte buildingCategory;

    /**
     * Die Gebaeudeid
     */
    private final byte building;

    /**
     * Konstrutor erstellt eine neue Frage
     * @param id - ID
     * @param buildingCategory - GebaeudekategorieID
     * @param building - GebaeudeID
     * @param question - Frage
     * @param hint - Hinweistext
     * @param answers - Frage
     * @param correctAnswers - Liste mit richtigen Antworten
     * @param result - Auswertungstext
     */
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

    /**
     * Gibt die ID zurueck
     * @return
     */
    public byte getID()
    {
        return this.id;
    }

    /**
     * Gibt die Frage zurueck
     * @return
     */
    public String getQuestion()
    {
        return this.question;
    }

    /**
     * Gibt den Hinweistext zurueck
     * @return
     */
    public String getHint()
    {
        return this.hint;
    }

    /**
     * Gibt alle Antwortmoeglichkeiten zurueck
     * @return
     */
    public String[] getAnswers()
    {
        return this.answers;
    }

    /**
     * Gibt zurueck, ob es sich um eine korrekte Antwort handelt
     * @param id - ID der Antwort die zu pruefen ist
     * @return
     */
    public boolean isCorrectAnswer(int id)
    {
        return this.correctAnswers.contains(id);
    }

    /**
     * Gibt den Auswertungstext zurueck
     * @return
     */
    public String getResult()
    {
        return this.result;
    }

    /**
     * Gibt das Gebaeude zurueck
     * @return
     */
    public Building getBuilding()
    {
        BuildingCategory category = BuildingCategoryManager.getInstance().getCategory(this.buildingCategory);
        return BuildingManager.getInstance().getBuilding(category, this.building);
    }
}
