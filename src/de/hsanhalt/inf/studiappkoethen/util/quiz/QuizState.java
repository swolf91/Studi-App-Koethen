package de.hsanhalt.inf.studiappkoethen.util.quiz;

import de.hsanhalt.inf.studiappkoethen.xml.quiz.Quiz;

public enum QuizState
{
    WELCOME_MESSAGE(0),
    QUESTION(1),
    RESULT(2),
    ANALYSIS(3);

    private int id;

    QuizState(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return this.id;
    }

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
