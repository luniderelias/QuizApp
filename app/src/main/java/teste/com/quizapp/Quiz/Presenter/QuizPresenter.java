package teste.com.quizapp.Quiz.Presenter;

import java.util.ArrayList;
import java.util.List;

import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.Util.Cache;


public class QuizPresenter implements IQuizPresenter{

    private int currentQuestionNumber = 0;

    IQuizView quizView;


    public QuizPresenter(IQuizView quizView) {
        this.quizView = quizView;
    }


    @Override
    public void getQuestions(){
        Cache.questions.add(new Question("question_1","Question 1", "Category", new String[]{
                "Option A", "Option B", "Option C", "Option D", "Option E"
        }));
        Cache.questions.add(new Question("question_2","Question 2", "Category", new String[]{
                "Option A", "Option B", "Option C", "Option D", "Option E"
        }));
        Cache.questions.add(new Question("question_3","Question 3", "Category", new String[]{
                "Option A", "Option B", "Option C", "Option D", "Option E"
        }));
        setQuestion();
    }

    @Override
    public void goToNextQuestion(){
        currentQuestionNumber += 1;
        setQuestion();
    }

    @Override
    public void setQuestion(){
        quizView.setHorizontalScrollQuestions();
        quizView.setCurrentQuestion(Cache.questions.get(currentQuestionNumber));
    }

    @Override
    public void answerQuestion() {
        Cache.questions.get(currentQuestionNumber).setResult(Question.RIGHT_ANSWER_CODE);
    }
}
