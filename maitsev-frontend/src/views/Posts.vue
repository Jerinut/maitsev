<template>
  <div class="AllPosts">
    <div id="posts-list">
      <div class="heading">
        <h2>All Posts</h2>
        <a href="/posts/addpost">
          <h1>Add a post</h1>
        </a>
      </div>

      <ul>
        <Post
          v-for="post in posts"
          :key="post.id"
          :post="post"
          @like-post="likePost"
          @submit-comment="submitComment"
          @delete-comment="deleteComment"
        />
      </ul>
    </div>
  </div>
</template>

<script>
import Post from "@/components/Post.vue";
import { jwtDecode } from "jwt-decode";
//import {authState} from '../auth';

export default {
  name: "Posts",
  components: {
    Post,
  },
  data() {
    return {
      posts: [],
    };
  },
  methods: {
    fetchPosts() {
      fetch(`http://localhost:8001/api/posts`)
        .then((response) => response.json())
        .then((data) => {
          this.posts = data;
          this.fetchUsernames();
          this.fetchComments();
        })
        .catch((err) => console.log(err.message));
    },
    fetchUsernames() {
      this.posts.forEach((post) => {
        fetch(`http://localhost:8000/api/profiles/${post.postedById}`)
          .then((response) => response.json())
          .then((data) => {
            post.postedBy = data.username;
          })
          .catch((err) => console.log(err.message));
      });
    },
    fetchComments() {
      this.posts.forEach((post) => {
        fetch(`http://localhost:8001/api/posts/${post.id}/comments`)
          .then((response) => response.json())
          .then(async (comments) => {
            // Fetch the username for each comment
            for (const comment of comments) {
              try {
                const response = await fetch(
                  `http://localhost:8000/api/profiles/${comment.postedById}`
                );
                if (response.ok) {
                  const data = await response.json();
                  // Add the username to the comment object
                  comment.username = data.username;
                } else {
                  console.log(
                    `Failed to fetch username for comment ${comment.id}`
                  );
                }
              } catch (err) {
                console.log(
                  `Error fetching username for comment ${comment.id}: ${err.message}`
                );
              }
            }
            // Assign the comments array to the post
            post.comments = comments;
          })
          .catch((err) => console.log(err.message));
      });
    },
    getCurrentUser() {
      const token = localStorage.getItem("jwtToken");
      if (token) {
        const decoded = jwtDecode(token);
        return decoded.userId;
      }
      return "";
    },

    submitComment(post, commentText) {
      if (!commentText.trim()) {
        return;
      }

      const data = {
        text: commentText,
        createdAt: new Date(),
        postedById: this.getCurrentUser(),
      };

      fetch(`http://localhost:8001/api/posts/${post.id}/comments`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (response.ok) {
            console.log("Comment added successfully");
            this.fetchComments();
          } else {
            console.log("Failed to add comment");
          }
        })
        .catch((error) => {
          console.error("Error adding comment:", error);
        });
    },
    deleteComment(postId, commentId) {
      fetch(`http://localhost:8001/api/posts/${postId}/comments/${commentId}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            console.log("Comment deleted successfully");
            const postIndex = this.posts.findIndex(
              (post) => post.id === postId
            );
            if (postIndex !== -1) {
              const commentIndex = this.posts[postIndex].comments.findIndex(
                (comment) => comment.id === commentId
              );
              if (commentIndex !== -1) {
                this.posts[postIndex].comments.splice(commentIndex, 1);
              }
            }
          } else {
            console.log("Failed to delete comment");
          }
        })
        .catch((error) => {
          console.error("Error deleting comment:", error);
        });
    },

    likePost(post) {
      // Create a copy of the post object to avoid directly mutating props
      const updatedPost = { ...post };

      // Delete fields that should be appended
      delete updatedPost.postedBy;
      delete updatedPost.comments;

      // Check if the user ID is in the likes array
      const userId = this.getCurrentUser();
      const likesIndex = updatedPost.likes.indexOf(userId);

      if (likesIndex !== -1) {
        // Remove the user ID from the likes array
        updatedPost.likes.splice(likesIndex, 1);
      } else {
        // Add the user ID to the likes array
        updatedPost.likes.push(userId);
      }

      // Send the updated post to the server
      fetch(`http://localhost:8001/api/posts/${post.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedPost),
      })
        .then((response) => {
          if (response.ok) {
            console.log("Post updated successfully");
            // Update the local post object to reflect the changes
            this.$emit("update-post", updatedPost);
          } else {
            console.log("Failed to update post");
          }
        })
        .catch((error) => {
          console.error("Error updating post:", error);
        });
    },
  },
  mounted() {
    this.fetchPosts();
  },
};
</script>

<style scoped>
.heading {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
h1 {
  font-size: 20px;
  color: rgb(0, 255, 47);
  padding: 10px;
  background-color: rgb(216, 231, 194);
  border-radius: 15px;
}
h2 {
  font: 30px;
  font-weight: bolder;
}
a {
  text-decoration: none;
}
a:hover {
  text-decoration: underline;
}
#posts-list {
  background: #bdebff;
  box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.2);
  margin-bottom: 30px;
  padding: 10px 20px;
  margin: auto;
  width: 50%;
  border-radius: 20px;
}
#posts-list ul {
  padding: 0;
}
</style>
