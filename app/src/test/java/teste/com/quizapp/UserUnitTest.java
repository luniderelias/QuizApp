package teste.com.quizapp;

import org.junit.Test;

import teste.com.quizapp.Model.User.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static teste.com.quizapp.Model.User.User.VALID_USERNAME_CODE;

public class UserUnitTest {

    @Test
    public void checkNullUsernameTest() {
        // Given
        String empty_string = "";
        User user = new User(empty_string);

        // When
        boolean result = user.checkNullUsername();

        // Then
        assertTrue(result);
    }

    @Test
    public void checkUsernameInvalidLengthTest() {
        // Given
        String thirty_one_length_long_string = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        User user = new User(thirty_one_length_long_string);

        // When
        boolean result = user.checkUsernameInvalidLength();

        // Then
        assertTrue(result);
    }

    @Test
    public void validUsernameTest() {
        // Given
        String valid_username_string = "username";
        User user = new User(valid_username_string);
        int expected_result = VALID_USERNAME_CODE;

        // When
        int result = user.isValidData();

        // Then
        assertEquals(expected_result, result);
    }


}
