package teste.com.quizapp.Login.Presenter;

import teste.com.quizapp.Model.User.User;
import teste.com.quizapp.R;
import teste.com.quizapp.Login.View.ILoginView;

import static teste.com.quizapp.Model.User.User.EMPTY_USERNAME_CODE;
import static teste.com.quizapp.Model.User.User.INVALID_USERNAME_LENGTH_CODE;
import static teste.com.quizapp.Model.User.User.MAX_USERNAME_LENGTH;
import static teste.com.quizapp.Model.User.User.VALID_USERNAME_CODE;

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;
    private User user;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onBegin(String username) {
        user = new User(username);
        switch (user.isValidData()){
            case VALID_USERNAME_CODE:
                loginView.beginQuiz();
                break;
            case EMPTY_USERNAME_CODE:
                loginView.showInvalidUsernameError(R.string.empty_username);
                break;
            case INVALID_USERNAME_LENGTH_CODE:
                loginView.showInvalidUsernameError(R.string.invalid_username_length);
                break;
        }
    }
}
