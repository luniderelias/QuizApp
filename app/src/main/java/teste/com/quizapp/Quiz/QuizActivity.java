package teste.com.quizapp.Quiz;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import static teste.com.quizapp.Util.Constants.SNACKBAR_CORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_GET_NEW_QUESTION_FAILED_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_INCORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_NOT_CONNECTED_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_QUESTION_NOT_ANSWERED_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_SEND_ANSWER_FAILED_CODE;

public class QuizActivity extends AppCompatActivity implements IQuizView {

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

    private RadioGroup optionsRadioGroup;
    private LinearLayoutManager layoutManager;
    private QuizPresenter quizPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);


        setUpLoading();
        quizPresenter = new QuizPresenter(this);

        if (savedInstanceState != null) {
            quizPresenter.recoverInstanceState();
        } else {
            quizPresenter.getNextQuestion();
        }
    }


    @Override
    public void setHorizontalScrollQuestions() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        questionsRecyclerView.setLayoutManager(layoutManager);
        questionsRecyclerView.setAdapter(new QuestionsAdapter(this));
    }

    @Override
    public void setCurrentQuestion(Question question) {
        String questionNumber = getString(R.string.question_number, Cache.questions.indexOf(question) + 1);
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
                sendButton.setEnabled(true);
                break;
            case SNACKBAR_CORRECT_ANSWER_RESULT_CODE:
                Snackbar correctAnswerSnackbar = Snackbar.make(questionsRecyclerView,
                        getString(R.string.correct_answer),
                        Snackbar.LENGTH_SHORT);
                correctAnswerSnackbar.getView().setBackgroundColor(getResources()
                        .getColor(R.color.colorPrimaryLight));
                correctAnswerSnackbar.show();
                setHorizontalScrollQuestions();
                nextButton.setEnabled(true);
                break;
            case SNACKBAR_INCORRECT_ANSWER_RESULT_CODE:
                Snackbar incorrectAnswerSnackbar = Snackbar.make(questionsRecyclerView,
                        getString(R.string.incorrect_answer),
                        Snackbar.LENGTH_SHORT);
                incorrectAnswerSnackbar.getView().setBackgroundColor(getResources()
                        .getColor(android.R.color.holo_red_light));
                incorrectAnswerSnackbar.show();
                setHorizontalScrollQuestions();
                nextButton.setEnabled(true);
                break;
            case SNACKBAR_QUESTION_NOT_ANSWERED_CODE:
                Snackbar questionNotAnsweredSnackbar = Snackbar.make(questionsRecyclerView,
                        getString(R.string.question_not_answered),
                        Snackbar.LENGTH_SHORT);
                questionNotAnsweredSnackbar.getView().setBackgroundColor(getResources()
                        .getColor(android.R.color.holo_orange_light));
                questionNotAnsweredSnackbar.show();
                sendButton.setEnabled(true);
                break;
        }
    }

    @Override
    public boolean checkConnection() {
        return ConnectivityUtil.hasNetworkConnection(this);
    }

    @OnClick(R.id.sendButton)
    void onSendAnswer(View v) {
        TextView answerTextView = findViewById(optionsRadioGroup
                .getCheckedRadioButtonId());
        if (answerTextView == null) {
            showSnackbar(SNACKBAR_QUESTION_NOT_ANSWERED_CODE);
            return;
        }
        Answer answer = new Answer(((TextView) findViewById(optionsRadioGroup
                .getCheckedRadioButtonId())).getText().toString());

        quizPresenter.answerQuestion(answer);
        v.setEnabled(false);
    }

    @OnClick(R.id.nextButton)
    void onNextQuestion(View v) {
        v.setEnabled(false);
        sendButton.setEnabled(true);
        quizPresenter.getNextQuestion();
    }

    @Override
    public void setUpLoading() {
        Glide.with(this)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .into(loadingImageView);
        loadingImageView.setVisibility(View.GONE);
    }

    @Override
    public void setLoadingVisibility(boolean visible) {
        if (visible)
            loadingImageView.setVisibility(View.VISIBLE);
        else
            loadingImageView.setVisibility(View.GONE);
    }

    @Override
    public void showFinishedDialog(int score) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.quiz_ended));
        alertDialog.setMessage(getResources().getString(R.string.score_is, score));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.begin_new_quiz),
                (dialog, which) -> {
                    finish();
                    startActivity(new Intent(this, QuizActivity.class));
                    dialog.dismiss();
                });
        alertDialog.show();
    }


}