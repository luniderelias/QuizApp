package teste.com.quizapp.Quiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.Presenter.QuizPresenter;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.R;
import teste.com.quizapp.Util.Cache;
import teste.com.quizapp.Util.ConnectivityUtil;

import static teste.com.quizapp.Quiz.Presenter.QuizPresenter.SNACKBAR_CORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Quiz.Presenter.QuizPresenter.SNACKBAR_GET_NEW_QUESTION_FAILED_CODE;
import static teste.com.quizapp.Quiz.Presenter.QuizPresenter.SNACKBAR_INCORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Quiz.Presenter.QuizPresenter.SNACKBAR_NOT_CONNECTED_CODE;
import static teste.com.quizapp.Quiz.Presenter.QuizPresenter.SNACKBAR_SEND_ANSWER_FAILED_CODE;

public class QuizActivity extends AppCompatActivity implements IQuizView {

    private QuizPresenter quizPresenter;

    @BindView(R.id.questionNumberTextView)
    TextView questionNumberTextView;

    @BindView(R.id.questionDescriptionTextView)
    TextView questionDescriptionTextView;

    @BindView(R.id.questionsRecyclerView)
    RecyclerView questionsRecyclerView;

    @BindView(R.id.questionRadioGroup)
    ViewGroup questionRadioGroup;

    @BindView(R.id.sendButton)
    Button sendButton;

    @BindView(R.id.nextButton)
    Button nextButton;

    @BindView(R.id.loadingImageView)
    ImageView loadingImageView;

    RadioGroup optionsRadioGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        setUpLoading();

        quizPresenter = new QuizPresenter(this);

        quizPresenter.getNextQuestion();
    }


    @Override
    public void setHorizontalScrollQuestions() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        questionsRecyclerView.setLayoutManager(layoutManager);
        questionsRecyclerView.setAdapter(new QuestionsAdapter(this));
    }

    @Override
    public void setCurrentQuestion(Question question) {
        String questionNumber = String.valueOf(Cache.questions.indexOf(question)) + "/10";
        questionNumberTextView.setText(questionNumber);
        questionDescriptionTextView.setText(question.getStatement());
        setQuestionOptions(question.getOptions());
    }


    @Override
    public void setQuestionOptions(String[] options) {
        questionRadioGroup.removeAllViews();
        optionsRadioGroup = new RadioGroup(this);
        optionsRadioGroup.setOrientation(LinearLayout.VERTICAL);
        for (String option : options) {
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(option);
            optionsRadioGroup.addView(rdbtn);
        }
        questionRadioGroup.addView(optionsRadioGroup);
    }

    @Override
    public void showSnackbar(int code) {
        switch (code) {
            case SNACKBAR_NOT_CONNECTED_CODE:
                Snackbar.make(questionsRecyclerView,
                        getString(R.string.not_connected),
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.connect, view -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                        .setActionTextColor(getResources()
                                .getColor(R.color.colorPrimaryLight)).show();
                break;
            case SNACKBAR_GET_NEW_QUESTION_FAILED_CODE:
                Snackbar.make(questionsRecyclerView,
                        getString(R.string.connection_failed),
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.try_again, view -> quizPresenter.getNextQuestion())
                        .setActionTextColor(getResources()
                                .getColor(R.color.colorPrimaryLight)).show();
                break;
            case SNACKBAR_SEND_ANSWER_FAILED_CODE:
                Snackbar.make(questionsRecyclerView,
                        getString(R.string.connection_failed),
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.try_again, view -> onSendAnswer(sendButton))
                        .setActionTextColor(getResources()
                                .getColor(R.color.colorPrimaryLight)).show();
                break;
            case SNACKBAR_CORRECT_ANSWER_RESULT_CODE:
                Snackbar correctAnswerSnackbar = Snackbar.make(questionsRecyclerView,
                        getString(R.string.correct_answer),
                        Snackbar.LENGTH_SHORT);
                correctAnswerSnackbar.getView().setBackgroundColor(getResources()
                        .getColor(R.color.colorPrimaryLight));
                correctAnswerSnackbar.show();
                break;
            case SNACKBAR_INCORRECT_ANSWER_RESULT_CODE:
                Snackbar incorrectAnswerSnackbar = Snackbar.make(questionsRecyclerView,
                        getString(R.string.incorrect_answer),
                        Snackbar.LENGTH_SHORT);
                incorrectAnswerSnackbar.getView().setBackgroundColor(getResources()
                        .getColor(android.R.color.holo_red_light));
                incorrectAnswerSnackbar.show();
                break;
        }
    }

    @Override
    public boolean checkConnection() {
        return ConnectivityUtil.hasNetworkConnection(this);
    }

    @OnClick(R.id.sendButton)
    void onSendAnswer(View v) {
        Answer answer = new Answer(((TextView) findViewById(optionsRadioGroup
                .getCheckedRadioButtonId())).getText().toString());

        quizPresenter.answerQuestion(answer);
        v.setEnabled(false);
        nextButton.setEnabled(true);
    }

    @OnClick(R.id.nextButton)
    void onNextQuestion(View v) {
        v.setEnabled(false);
        sendButton.setEnabled(true);
        quizPresenter.getNextQuestion();
    }

    @Override
    public void setUpLoading(){
        Glide.with(this)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .into(loadingImageView);
    }

    @Override
    public void setLoadingVisibility(boolean visible) {
        if(visible)
            loadingImageView.setVisibility(View.VISIBLE);
        else
            loadingImageView.setVisibility(View.GONE);
    }


}