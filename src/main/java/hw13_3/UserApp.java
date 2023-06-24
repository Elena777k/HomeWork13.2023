package hw13_3;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class UserApp {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final Gson gson = new Gson();

    public static List<UserEntity> getOpenTasksForUser(int userId) {
        try {
            URL url = new URL(BASE_URL + "/users/" + userId + "/todos?completed=false");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    UserEntity[] users = gson.fromJson(reader, UserEntity[].class);
                    return Arrays.asList(users);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
