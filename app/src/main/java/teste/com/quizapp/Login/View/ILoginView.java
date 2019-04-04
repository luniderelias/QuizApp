package teste.com.quizapp.Login.View;

import android.view.View;

public interface ILoginView {

    void showInvalidUsernameError(int errorMessage);
    void onBeginClick(View view);
    void beginQuiz();
}
