package teste.com.quizapp.Model.User;

import static teste.com.quizapp.Util.Constants.EMPTY_USERNAME_CODE;
import static teste.com.quizapp.Util.Constants.INVALID_USERNAME_LENGTH_CODE;
import static teste.com.quizapp.Util.Constants.MAX_USERNAME_LENGTH;
import static teste.com.quizapp.Util.Constants.VALID_USERNAME_CODE;

public class User implements IUser {

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
