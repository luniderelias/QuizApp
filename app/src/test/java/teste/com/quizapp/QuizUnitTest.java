package teste.com.quizapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import teste.com.quizapp.Login.LoginActivity;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Model.User.User;
import teste.com.quizapp.Quiz.Presenter.QuizPresenter;
import teste.com.quizapp.Quiz.QuizActivity;
import teste.com.quizapp.Util.Cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class QuizUnitTest {

    public QuizActivity quizActivity = Mockito.mock(QuizActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void checkGetScoreTest() {
        // Given
        QuizPresenter quizPresenter = new QuizPresenter(quizActivity);
        boolean[] results = new boolean[]{true, false, true, true, false, false, true, false, true, true};
        for(int ii = 0; ii < results.length; ii++){
            Question question = new Question();
            question.setResult(results[ii]);
            Cache.questions.add(question);
        }
        int expectedResult = 6;
        // When
        int result = quizPresenter.getScore();
        // Then
        assertEquals(expectedResult, result);
    }
}
