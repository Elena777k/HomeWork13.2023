package hw13_2;

import com.google.gson.Gson;
import hw13_2.entity.CommentEntity;
import hw13_2.entity.PostEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommentApp {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final Gson gson = new Gson();

    private static CommentEntity[] getCommentsForPost (int userID){
        try {
            URL url = new URL(BASE_URL + "/posts/" + userID + "/comments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                CommentEntity[] comments = gson.fromJson(inputStreamReader, CommentEntity[].class);
                inputStreamReader.close();
                return comments;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }

        private static PostEntity getLastPostUser (int userID) {
            try {
                URL url = new URL(BASE_URL + "/users/" + userID + "/posts");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    PostEntity[] posts = gson.fromJson(inputStreamReader, PostEntity[].class);
                    inputStreamReader.close();
                    if (posts.length > 0) {
                        int maxId = Integer.MIN_VALUE;
                        PostEntity lastPost = null;
                        for (PostEntity post : posts) {
                            if (post.getId() > maxId) {
                                maxId = post.getId();
                                lastPost = post;
                            }
                        }
                        return lastPost;
                    }
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

    private static boolean saveCommentsToFile(CommentEntity[] comments, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(comments, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getAndSaveCommentsLastPost(int userId) {
        PostEntity lastPost = getLastPostUser(userId);
        if (lastPost == null) {
            System.out.println("No posts found: " + userId);
        }

     CommentEntity[] comments = getCommentsForPost(lastPost.getId());
    if (comments == null) {
        System.out.println("No comments found: " + lastPost.getId());
    }

    String fileName = "user-" + userId + "-post-" + lastPost.getId() + "-comments.json";
        boolean success = saveCommentsToFile(comments, fileName);

        if (success) {
            System.out.println("Comments for userId " + userId + ", PostId " + lastPost.getId() + " saved to " + fileName);
        } else {
            System.out.println("Failed to save");
        }
    }
}
