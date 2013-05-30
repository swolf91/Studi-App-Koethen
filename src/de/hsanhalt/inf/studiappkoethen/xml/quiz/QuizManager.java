package de.hsanhalt.inf.studiappkoethen.xml.quiz;

import java.util.ArrayList;
import java.util.List;

import de.hsanhalt.inf.studiappkoethen.xml.parsing.IXmlParsing;
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
            String startmsg;

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
            }
        }
    }

    private Question getQuestion(Node node)
    {
        byte id = -1;
        String question = null;
        List<String> answer = new ArrayList<>(4);
        int rightAnswerID = -1;
        String hint = null;
        String result = null;

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
                    if(subNode.hasAttributes())     // TODO ueberarbeiten und sicherer machen :p
                    {
                        rightAnswerID = answer.size() - 1;
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
            }
        }

        if(id == -1 || question == null || answer.isEmpty() || rightAnswerID == -1)
        {
            return null;
        }

        return new Question(id, question, hint, answer.toArray(new String[answer.size()]), rightAnswerID, result);
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