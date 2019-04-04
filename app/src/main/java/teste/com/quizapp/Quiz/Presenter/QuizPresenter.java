package teste.com.quizapp.Quiz.Presenter;



import java.util.ArrayList;

import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.Util.Cache;
import teste.com.quizapp.Util.Service.QuizService;

import static teste.com.quizapp.Util.Constants.SNACKBAR_CORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_GET_NEW_QUESTION_FAILED_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_INCORRECT_ANSWER_RESULT_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_NOT_CONNECTED_CODE;
import static teste.com.quizapp.Util.Constants.SNACKBAR_SEND_ANSWER_FAILED_CODE;


public class QuizPresenter implements IQuizPresenter {

    private IQuizView quizView;


    public QuizPresenter(IQuizView quizView) {
        this.quizView = quizView;
    }


    @Override
    public void getNextQuestion() {
        quizView.setLoadingVisibility(true);
        if (quizView.checkConnection()) {
            if(Cache.questions.size() >= 10) {
                Cache.questions = new ArrayList<>();
                quizView.showFinishedDialog(getScore());
            }else {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        QuizService.getNextQuestion()
                                .onErrorResumeNext(response -> {
                                    quizView.showSnackbar(SNACKBAR_GET_NEW_QUESTION_FAILED_CODE);
                                    quizView.setLoadingVisibility(false);
                                }).subscribe(response -> {
                            setQuestion(response);
                            quizView.setLoadingVisibility(false);
                        }).isDisposed();
                    }
                };
                thread.start();
            }
        } else {
            quizView.showSnackbar(SNACKBAR_NOT_CONNECTED_CODE);
            quizView.setLoadingVisibility(false);
        }
    }


    @Override
    public void setQuestion(Question question) {
        Cache.questions.add(question);
        quizView.setHorizontalScrollQuestions();
        quizView.setCurrentQuestion(Cache.questions.get(Cache.questions.size()-1));
    }

    @Override
    public void answerQuestion(Answer answer) {
        quizView.setLoadingVisibility(true);
        if (quizView.checkConnection()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    QuizService.postAnswer(Cache.questions.get(Cache.questions.size()-1), answer)
                            .onErrorResumeNext(response -> {
                                quizView.showSnackbar(SNACKBAR_SEND_ANSWER_FAILED_CODE);
                                quizView.setLoadingVisibility(false);
                            }).subscribe(response -> {
                        Cache.questions.get(Cache.questions.size()-1).setResult(response.result);
                        if (response.result)
                            quizView.showSnackbar(SNACKBAR_CORRECT_ANSWER_RESULT_CODE);
                        else
                            quizView.showSnackbar(SNACKBAR_INCORRECT_ANSWER_RESULT_CODE);

                        quizView.setLoadingVisibility(false);
                    }).isDisposed();
                }
            };
            thread.start();
        } else {
            quizView.showSnackbar(SNACKBAR_NOT_CONNECTED_CODE);
            quizView.setLoadingVisibility(false);
        }
    }

    @Override
    public int getScore() {
        int score = 0;
        for (Question question:Cache.questions) {
            if(question.getResult())
                score++;
        }
        return score;
    }

    @Override
    public void recoverInstanceState() {
        setQuestion(Cache.questions.remove(Cache.questions.size()-1));
    }


}
