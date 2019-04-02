package teste.com.quizapp.Quiz.Presenter;


import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.Util.Cache;


public class QuizPresenter implements IQuizPresenter{

    private int currentQuestionNumber = -1;

    private IQuizView quizView;


    public QuizPresenter(IQuizView quizView) {
        this.quizView = quizView;
    }


    @Override
    public void getNextQuestion(){
        currentQuestionNumber += 1;
        Cache.questions.add(new Question("question_1","Question 1", "Category", new String[]{
                "Option A", "Option B", "Option C", "Option D", "Option E"
        }));
        setQuestion();
    }


    @Override
    public void setQuestion(){
        quizView.setHorizontalScrollQuestions();
        quizView.setCurrentQuestion(Cache.questions.get(currentQuestionNumber));
    }

    @Override
    public void answerQuestion(String answer) {
        Cache.questions.get(currentQuestionNumber).setResult(Question.RIGHT_ANSWER_CODE);
    }
}
