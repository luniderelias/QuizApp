package teste.com.quizapp.Util.Rest;




import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import teste.com.quizapp.Model.Answer.Answer;
import teste.com.quizapp.Model.Question.Question;

public interface Api {

    String URL = "http://34.73.190.231:8080/";

    @GET("question")
    Observable<Question> getNextQuestion();



    @Headers("Content-Type: application/json")
    @POST("answer")
    Observable<AnswerResponse> postAnswer(
            @Query("questionId") String id,
            @Body Answer answer
    );

}
