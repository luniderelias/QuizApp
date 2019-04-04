package teste.com.quizapp.Util.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AnswerResponse {

    @SerializedName("result")
    @Expose
    public Boolean result;
}