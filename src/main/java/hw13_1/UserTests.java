package hw13_1;

import java.io.IOException;

import static hw13_1.UserApp.*;

public class UserTests {
    public static void main(String[] args) throws IOException {
        System.out.println("createUser() = " + createUser());
        System.out.println("updateUser() = " + updateUser(2));
        System.out.println("deleteUser(1) = " + deleteUser(11));
        System.out.println("getAllUsers() = " + getAllUsers());
        System.out.println("findUserById(5) = " + findUserById(2));
        System.out.println("getUserByUsername(\"Ervin\") = " + getUserByUsername("Antonette"));
    }
}
