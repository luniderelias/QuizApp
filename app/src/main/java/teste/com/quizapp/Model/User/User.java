package teste.com.quizapp.Model.User;

public class User implements IUser {

    public final static int VALID_USERNAME_CODE = 0;
    public final static int EMPTY_USERNAME_CODE = 1;
    public final static int INVALID_USERNAME_LENGTH_CODE = 2;
    public final static int MAX_USERNAME_LENGTH = 30;
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int isValidData() {
        if(checkNullUsername())
            return EMPTY_USERNAME_CODE;
        if(checkUsernameInvalidLength())
            return INVALID_USERNAME_LENGTH_CODE;
        return VALID_USERNAME_CODE;
    }

    @Override
    public boolean checkNullUsername() {
        return username.equals("");
    }

    @Override
    public boolean checkUsernameInvalidLength() {
        return username.length() > MAX_USERNAME_LENGTH;
    }
}
