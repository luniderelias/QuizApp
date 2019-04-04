package teste.com.quizapp.Model.Question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import teste.com.quizapp.Quiz.Presenter.IQuizPresenter;

public class Question implements IQuestion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("statement")
    @Expose
    private String statement;
    @SerializedName("options")
    @Expose
    private String[] options;
    private Boolean result;


    public Question(String id, String statement, String[] options) {
        this.id = id;
        this.statement = statement;
        this.options = options;
    }

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String[] getOptions() {
        return options;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public void setOptions(String[] options) {

        this.options = options;
    }
}
