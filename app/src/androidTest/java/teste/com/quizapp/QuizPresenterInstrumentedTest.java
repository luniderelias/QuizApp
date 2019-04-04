package teste.com.quizapp;


import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.Presenter.QuizPresenter;
import teste.com.quizapp.Quiz.QuizActivity;
import teste.com.quizapp.Util.Cache;
import teste.com.quizapp.Utils.OrientationChangeAction;
import teste.com.quizapp.Utils.WaitUtils;

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
        WaitUtils.waitTime();
        Mockito.verify(quizActivity).setHorizontalScrollQuestions();
    }

    @Test
    public void testAnswerQuestion() {
        // given
        QuizPresenter subject = new QuizPresenter(quizActivity);
        subject.getNextQuestion();
        WaitUtils.waitTime();
        Question question = Cache.questions.get(Cache.questions.size()-1);
        Answer answer = new Answer(question.getOptions()[0]);
        // when
        subject.answerQuestion(answer);
        // then
        WaitUtils.waitTime();
        Mockito.verify(quizActivity).setHorizontalScrollQuestions();
    }

    @Test
    public void testOrientationChange() {
        // given
        QuizPresenter subject = new QuizPresenter(quizActivity);
        subject.getNextQuestion();
        WaitUtils.waitTime();
        Question question = Cache.questions.get(Cache.questions.size()-1);

        // when
        OrientationChangeAction.orientationLandscape();
        WaitUtils.waitTime();
        String expectedValue = ((TextView) quizActivity.findViewById(R.id.questionDescriptionTextView)).getText().toString();
        // then
        assert expectedValue.equals(question.getStatement());
    }
}
