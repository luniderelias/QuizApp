package teste.com.quizapp.Quiz.Presenter;


import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Quiz.View.IQuizView;
import teste.com.quizapp.Util.Cache;
import teste.com.quizapp.Util.ConnectivityUtil;
import teste.com.quizapp.Util.Service.QuizService;


public class QuizPresenter implements IQuizPresenter {

    private int currentQuestionNumber = -1;

    private IQuizView quizView;
    public final static int SNACKBAR_NOT_CONNECTED_CODE = 0;
    public final static int SNACKBAR_GET_NEW_QUESTION_FAILED_CODE = 1;
    public final static int SNACKBAR_SEND_ANSWER_FAILED_CODE = 2;
    public final static int SNACKBAR_CORRECT_ANSWER_RESULT_CODE = 3;
    public final static int SNACKBAR_INCORRECT_ANSWER_RESULT_CODE = 4;


    public QuizPresenter(IQuizView quizView) {
        this.quizView = quizView;
    }


    @Override
    public void getNextQuestion() {
        quizView.setLoadingVisibility(true);
        if (quizView.checkConnection()) {
            currentQuestionNumber = Cache.questions.size();
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
        } else {
            quizView.showSnackbar(SNACKBAR_NOT_CONNECTED_CODE);
            quizView.setLoadingVisibility(false);
        }
    }


    @Override
    public void setQuestion(Question question) {
        Cache.questions.add(question);
        quizView.setHorizontalScrollQuestions();
        quizView.setCurrentQuestion(Cache.questions.get(currentQuestionNumber));
    }

    @Override
    public void answerQuestion(Answer answer) {
        quizView.setLoadingVisibility(true);
        if (quizView.checkConnection()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    QuizService.postAnswer(Cache.questions.get(currentQuestionNumber), answer)
                            .onErrorResumeNext(response -> {
                                quizView.showSnackbar(SNACKBAR_SEND_ANSWER_FAILED_CODE);
                                quizView.setLoadingVisibility(false);
                            }).subscribe(response -> {
                        Cache.questions.get(currentQuestionNumber).setResult(response.result);
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
}
