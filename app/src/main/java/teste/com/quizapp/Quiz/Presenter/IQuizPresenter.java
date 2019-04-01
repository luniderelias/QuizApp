package teste.com.quizapp.Quiz.Presenter;

import teste.com.quizapp.Model.Question.Question;

public interface IQuizPresenter {
    void getQuestions();
    void goToNextQuestion();
    void setQuestion();
    void answerQuestion();
}
