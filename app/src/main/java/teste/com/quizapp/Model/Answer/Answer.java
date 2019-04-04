package teste.com.quizapp.Model.Answer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {


    @SerializedName("answer")
    @Expose
    private String answer;

    public Answer(String answer) {
        this.answer = answer;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
