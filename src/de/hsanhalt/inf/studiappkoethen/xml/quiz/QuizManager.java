package de.hsanhalt.inf.studiappkoethen.xml.quiz;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import de.hsanhalt.inf.studiappkoethen.xml.parsing.IXmlParsing;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuizManager implements IXmlParsing
{
    private static QuizManager INSTANCE;
    private List<Quiz> quizList;

    public static QuizManager getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new QuizManager();
        }
        return INSTANCE;
    }

    private QuizManager()
    {
        this.quizList = new ArrayList<>(3);
    }

    public Quiz getQuiz(byte id)
    {
        for(Quiz quiz : quizList)
        {
            if(quiz.getID() == id)
            {
                return quiz;
            }
        }
        return null;
    }

    public Quiz[] getQuizs()
    {
        return this.quizList.toArray(new Quiz[this.quizList.size()]);
    }

    /**
     * Fuegt alle Inhalte innerhalb der Node in die Klasse ein.
     *
     * @param node - node welches als Inhalt das Starttag hat.
     */
    @Override
    public void addNode(Node node)
    {
        if(!node.getNodeName().equals(this.getStartTag()))
        {
            return;
        }
        if(node.hasChildNodes())
        {
            NodeList nodeList = node.getChildNodes();

            byte id = -1;
            List<Question> questions = new ArrayList<>();
            String startmsg = null;
            String name = null;

            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node subNode = nodeList.item(i);
                if(subNode.getNodeName().equals("id"))
                {
                    id = Byte.parseByte(subNode.getTextContent());
                }
                else if(subNode.getNodeName().equals("question"))
                {
                    questions.add(this.getQuestion(subNode));
                }
                else if(subNode.getNodeName().equals("startMessage"))
                {
                    startmsg = subNode.getTextContent();
                }
                else if(subNode.getNodeName().equals("name"))
                {
                    name = subNode.getTextContent();
                }
            }

            if(id == -1 || questions.isEmpty() || name == null)
            {
                Log.e("QuizManagerError", "Could not create quiz!");
                return;
            }

            Quiz quiz = new Quiz(id, name, questions.toArray(new Question[questions.size()]), startmsg);
            Log.d("QuizManagerDebug", "Created Quiz: " + quiz.getID() + " with " + quiz.getNumberOfQuestions() + " questions.");
            this.quizList.add(quiz);
        }
    }

    private Question getQuestion(Node node)
    {
        byte id = -1;
        String question = null;
        List<String> answer = new ArrayList<>(4);
        List<Integer> correctAnswers = new ArrayList<>(1);
        String hint = null;
        String result = null;
        byte buildingCategory = -1;
        byte building = -1;

        if(node.hasChildNodes())
        {
            NodeList nodeList = node.getChildNodes();
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node subNode = nodeList.item(i);
                String content = subNode.getTextContent();
                if(subNode.getNodeName().equals("id"))
                {
                    id = Byte.parseByte(content);
                }
                else if(subNode.getNodeName().equals("question"))
                {
                    question = content;
                }
                else if(subNode.getNodeName().equals("answer"))
                {
                    answer.add(content);
                    if(subNode.hasAttributes())
                    {
                        Node attribute = subNode.getAttributes().getNamedItem("answer");
                        if(attribute != null && attribute.getTextContent().equals("true"))
                        {
                            correctAnswers.add(answer.size() - 1);
                        }
                    }
                }
                else if(subNode.getNodeName().equals("hint"))
                {
                    hint = content;
                }
                else if(subNode.getNodeName().equals("result"))
                {
                    result = content;
                }
                else if(subNode.getNodeName().equals("buildingCategory"))
                {
                    buildingCategory = Byte.valueOf(content);
                }
                else if(subNode.getNodeName().equals("building"))
                {
                    building = Byte.valueOf(content);
                }
            }
        }

        if(id == -1 || question == null || answer.isEmpty() || correctAnswers.isEmpty() || (building >= 0 && buildingCategory < 0) || (building < 0 && buildingCategory >= 0))
        {
            Log.e("QuizManagerError", "Could not parse question!");
            return null;
        }

        Question quest = new Question(id, buildingCategory, building, question, hint, answer.toArray(new String[answer.size()]), correctAnswers, result);
        Log.d("QuizManagerDebug", "Created question: " +  id);
        return quest;
    }

    /**
     * Gibt das Starttag zurueck
     */
    @Override
    public String getStartTag()
    {
        return "quiz";
    }
}