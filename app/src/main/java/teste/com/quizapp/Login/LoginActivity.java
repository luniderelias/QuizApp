package teste.com.quizapp.Login;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teste.com.quizapp.Login.Presenter.LoginPresenter;
import teste.com.quizapp.R;
import teste.com.quizapp.Login.View.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.usernameInputLayout)
    TextInputLayout textInputLayout;


    public LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void showInvalidUsernameError(int errorMessage) {
        textInputLayout.getEditText().setError(getText(errorMessage));
    }

    @Override
    @OnClick(R.id.beginButton)
    public void onBeginClick(View view) {
        loginPresenter.onBegin(textInputLayout.getEditText().getText().toString());
    }

    @Override
    public void beginQuiz() {

    }
}
