package teste.com.quizapp.Quiz.Presenter;

import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;

public interface IQuizPresenter {
    void getNextQuestion();
    void setQuestion(Question question);
    void answerQuestion(Answer answer);
    int getScore();
}
