package teste.com.quizapp.Util.Service;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;
import teste.com.quizapp.Util.Rest.AnswerResponse;
import teste.com.quizapp.Util.Rest.RestUtil;

public class QuizService {

    public static synchronized Observable<Question> getNextQuestion()  {
        return RestUtil.api.getNextQuestion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static synchronized Observable<AnswerResponse> postAnswer(Question question, Answer answer)  {
        return RestUtil.api.postAnswer(question.getId(),answer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
