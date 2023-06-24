package hw13_1;

import hw13_1.modelUser.UserEntity;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Optional;


public class UserApp {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static final Gson gson = new Gson();

    public static UserEntity createUser() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String requestBody = "{\"name\":\"Randy Paush\"," +
                    "\"username\":\"paushRandy\"," +
                    "\"email\":\"rpaush@google.com\"," +
                    "\"address\":{\"street\":\"Road. 798\",\"suite\":\"Apt. 556\",\"city\":\"GreenWay\",\"zipcode\":\"12356-2569\"," +
                    "\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}}}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }
            int responseCode = connection.getResponseCode();
            if (
                    HttpURLConnection.HTTP_CREATED == responseCode
            ) {
                return getUserEntity(connection);
            }
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException("Something goes wrong");
        }
        return null;
    }

    public static UserEntity updateUser(int id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String requestBody = "{\"id\":5,\"name\":\"Kris Vlec\",\"username\":\"VlecKris\",\"email\":\"krispi@google.com\"," +
                    "\"address\":{\"street\":\"Royal. 954\",\"suite\":\"Apt. 598\",\"city\":\"Orlando\",\"zipcode\":\"12356-9969\"," +
                    "\"geo\":{\"lat\":\"-37.3159\",\"lng\":\"81.1496\"}}}";
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestBody.getBytes());
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                return getUserEntity(connection);
            }

        connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteUser(int id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            connection.disconnect();
            return responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<UserEntity> getAllUsers() {
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
               try (
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder responce = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        responce.append(line);
                    }
                    Type userListType = new TypeToken<List<UserEntity>>() {
                    }.getType();
                    return gson.fromJson(responce.toString(), userListType);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Optional<UserEntity> findUserById(int id) {
        try {
            URL url = new URL(BASE_URL + "/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responceCode = connection.getResponseCode();
            if (responceCode == HttpURLConnection.HTTP_OK) {
                try (
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    UserEntity user = gson.fromJson(response.toString(), UserEntity.class);
                    return Optional.ofNullable(user);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    public static Optional<UserEntity> getUserByUsername(String username) {
        try {
            URL url = new URL(BASE_URL + "?username=" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Type userListType = new TypeToken<List<UserEntity>>() {
                    }.getType();
                    List<UserEntity> users = gson.fromJson(response.toString(), userListType);
                    if (!users.isEmpty()) {
                        return Optional.of(users.get(0));}
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    private static UserEntity getUserEntity(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        return gson.fromJson(response.toString(), UserEntity.class);
    }
}
