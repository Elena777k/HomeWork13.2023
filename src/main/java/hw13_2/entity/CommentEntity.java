package hw13_2.entity;


public class CommentEntity {

     private int postId;
     private int id;
     private String name;
     private String email;

     public CommentEntity(int postId, int id, String name, String email) {
          this.postId = postId;
          this.id = id;
          this.name = name;
          this.email = email;
     }

     public int getPostId() {
          return postId;
     }

     public void setPostId(int postId) {
          this.postId = postId;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }
     @Override
     public String toString() {
          return "UserEntity{" +
                  "postId=" + postId +
                  ", id=" + id +
                  ", name='" + name + '\'' +
                  ", email='" + email + '\'' +
                  '}';
     }
}

