package de.hsanhalt.inf.studiappkoethen.util.quiz;

import de.hsanhalt.inf.studiappkoethen.xml.quiz.Quiz;

public enum QuizState
{
    /**
     * Diese State gibt an, dass sich das Quiz gerade im Willkommensbildschirm befindet
     */
    WELCOME_MESSAGE(0),
    /**
     * Diese State gibt an, dass sich das Quiz gerade im Fragebildchirm befindet
     */
    QUESTION(1),
    /**
     * Diese State gibt an, dass sich das Quiz gerade im Frageauswertungsbildschirm befindet
     */
    RESULT(2),
    /**
     * Diese State gibt an, dass sich das Quiz im Auswertebildschirm befindet und abgeschlossen wurde.
     */
    ANALYSIS(3);

    /**
     * ID der State
     */
    private int id;

    QuizState(int id)
    {
        this.id = id;
    }

    /**
     * Gibt die ID der State zurueck
     * @return
     */
    public int getID()
    {
        return this.id;
    }

    /**
     * Gibt die State mithilfe der ID zurueck
     * @param id
     * @return
     */
    public static QuizState getByID(int id)
    {
        for(QuizState state : values())
        {
            if(state.id == id)
            {
                return state;
            }
        }
        return null;
    }

    /**
     * Gibt die State zurueck, die die QuizActivity als naechstes laden muss.
     * @param state - State indem sich die QuizActivity zurzeit befindet
     * @param quiz - Quiz, dass gerade abgearbeitet wird
     * @param questionNumber - Die Fragenummer, bei der man sich gerade befindet
     * @return
     */
    public static QuizState getNextState(QuizState state, Quiz quiz, int questionNumber)
    {
        if(state.equals(QuizState.WELCOME_MESSAGE))
        {
            return QuizState.QUESTION;
        }
        else if(state.equals(QuizState.QUESTION))
        {
            String result = quiz.getQuestion(questionNumber).getResult();
            if(result == null || result.isEmpty())
            {
                if(questionNumber + 1 >= quiz.getNumberOfQuestions())
                {
                    return QuizState.ANALYSIS;
                }
                else
                {
                    return QuizState.QUESTION;
                }
            }
            else
            {
                return QuizState.RESULT;
            }
        }
        else if(state.equals(QuizState.RESULT))
        {
            if(questionNumber + 1 >= quiz.getNumberOfQuestions())
            {
                return QuizState.ANALYSIS;
            }
            else
            {
                return QuizState.QUESTION;
            }
        }
        return null;
    }
}
