package teste.com.quizapp;


import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import teste.com.quizapp.Login.LoginActivity;
import teste.com.quizapp.Login.Presenter.LoginPresenter;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterInstrumentedTest {

    public LoginActivity loginActivity = Mockito.mock(LoginActivity.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnBeginWithEmptyUsername() {
        // given
        LoginPresenter subject = new LoginPresenter(loginActivity);
        String  empty_username= "";
        int expected_response = R.string.empty_username;
        // when
        subject.onBegin(empty_username);
        // then
        Mockito.verify(loginActivity).showInvalidUsernameError(expected_response);
    }

    @Test
    public void testOnBeginWithLongUsername() {
        // given
        LoginPresenter subject = new LoginPresenter(loginActivity);
        String thirty_one_length_long_username= "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        int expected_response = R.string.invalid_username_length;
        // when
        subject.onBegin(thirty_one_length_long_username);
        // then
        Mockito.verify(loginActivity).showInvalidUsernameError(expected_response);
    }

    @Test
    public void testOnBeginWithValidUsername() {
        // given
        LoginPresenter subject = new LoginPresenter(loginActivity);
        String thirty_one_length_long_username= "username";
        // when
        subject.onBegin(thirty_one_length_long_username);
        // then
        Mockito.verify(loginActivity).beginQuiz();
    }

}
