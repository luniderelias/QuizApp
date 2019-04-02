package teste.com.quizapp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import teste.com.quizapp.Login.LoginActivity;
import teste.com.quizapp.Login.Presenter.LoginPresenter;
import teste.com.quizapp.Quiz.Presenter.QuizPresenter;
import teste.com.quizapp.Quiz.QuizActivity;

@RunWith(MockitoJUnitRunner.class)
public class QuizPresenterInstrumentedTest {

    public QuizActivity quizActivity = Mockito.mock(QuizActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNextQuestion() {
        // given
        QuizPresenter subject = new QuizPresenter(quizActivity);
        // when
        subject.getNextQuestion();
        // then
        Mockito.verify(quizActivity).setHorizontalScrollQuestions();
    }
}
