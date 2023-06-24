package hw13_3;

import java.util.List;


import static hw13_3.UserApp.getOpenTasksForUser;


public class Main {
    public static void main(String[] args) {
        int userId = 9;

        List<UserEntity> openTasks = getOpenTasksForUser(userId);

        if (openTasks != null){
            System.out.println("Open tasks for User ID " + userId + ":");
            for (UserEntity task : openTasks) {
                System.out.println(task.getId() + ": " + task.getTitle());
            }
        } else {
            System.out.println("Failed to retrieve open tasks for User ID " + userId);
        }
    }
}