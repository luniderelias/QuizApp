package teste.com.quizapp.Quiz.Presenter;

import teste.com.quizapp.Model.Question.Question;

public interface IQuizPresenter {
    void getNextQuestion();
    void setQuestion();
    void answerQuestion(String answer);
}
