package teste.com.quizapp.Quiz.Presenter;

import android.os.Bundle;

import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;

public interface IQuizPresenter {
    void getNextQuestion();
    void setQuestion(Question question);
    void answerQuestion(Answer answer);
    int getScore();
    void recoverInstanceState();
}
