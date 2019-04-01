package teste.com.quizapp.Quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.Presenter.QuizPresenter;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.R;

public class QuizActivity extends AppCompatActivity implements IQuizView {

    private QuizPresenter quizPresenter;

    @BindView(R.id.categoryTextView)
    TextView categoryTextView;

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

    RadioGroup optionsRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        quizPresenter = new QuizPresenter(this);

        quizPresenter.getQuestions();
    }


    @Override
    public void setHorizontalScrollQuestions() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        questionsRecyclerView.setLayoutManager(layoutManager);
        questionsRecyclerView.setAdapter(new QuestionsAdapter(this));
    }

    @Override
    public void setCurrentQuestion(Question question) {
        categoryTextView.setText(question.getCategory());
        questionDescriptionTextView.setText(question.getQuestion());
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

    @OnClick(R.id.sendButton)
    void onSendAnswer(View v){
        quizPresenter.answerQuestion();
        v.setEnabled(false);
        nextButton.setEnabled(true);
    }
    @OnClick(R.id.nextButton)
    void onNextQuestion(View v){
        v.setEnabled(false);
        sendButton.setEnabled(true);
        quizPresenter.goToNextQuestion();
    }
}