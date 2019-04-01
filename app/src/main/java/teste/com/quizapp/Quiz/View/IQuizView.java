package teste.com.quizapp.Quiz.View;

import java.util.List;

import teste.com.quizapp.Model.Question.Question;

public interface IQuizView {

    void setHorizontalScrollQuestions();
    void setCurrentQuestion(Question question);
    void setQuestionOptions(String[] options);
}
