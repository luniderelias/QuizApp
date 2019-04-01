package teste.com.quizapp.Model.Question;

public class Question {

    private String id;
    private String question;
    private String category;
    private String[] options;
    private int result;

    public final static int NOT_ANSWERED_CODE = 0;
    public final static int RIGHT_ANSWER_CODE = 1;
    public final static int WRONG_ANSWER_CODE = 2;

    public Question(String id, String question, String category, String[] options) {
        this.id = id;
        this.question = question;
        this.category = category;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getOptions() {
        return options;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setOptions(String[] options) {

        this.options = options;
    }
}
