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
        <div class="item" v-for="post in posts" :key="post.id">
          <a class="singlepost" :href="'/posts/' + post.id">
            <span class="info"> <b>Posted By:</b> {{ post.postedBy }} </span
            ><br />
            <span class="info"> <b>Likes:</b> {{ post.likes }} </span><br />
            <span class="info"> <b>Created At:</b> {{ post.createdAt }} </span
            ><br />
            <span class="info">
              <b>Description:</b> {{ post.description }} </span
            ><br />
            <img
              class="postImage"
              :src="require('@/assets/maitsev-logo.png')"
              alt="Post Image"
            />
          </a>

          <button @click="likePost(post)" class="likePost">
            <!-- Use local image for the like button -->
            <img class="likeImage" src="@/assets/like.png" alt="Like Button" />
          </button>

          <div class="comments">
            <h2 v-if="post.comments && post.comments.length > 0">Comments:</h2>
            <div
              class="comment"
              v-for="comment in post.comments"
              :key="comment.id"
            >
              <div class="commentInfo">
                <p>Date: {{ comment.createdAt }}</p>
                <p>By: {{ comment.postedBy }}</p>
              </div>
              <span>{{ comment.text }}</span>
              <button
                @click="deleteComment(post.id, comment.id)"
                class="deleteComment"
              >
                Delete
              </button>
            </div>

            <textarea
              class="addcomment"
              v-model="commentText"
              placeholder="Enter your comment..."
            ></textarea>

            <button @click="submitComment(post)" class="submitComment">
              Submit
            </button>
          </div>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Posts",
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
          .then((data) => {
            post.comments = data; // Assign comments to the post
          })
          .catch((err) => console.log(err.message));
      });
    },
    submitComment(post) {
      if (!this.commentText.trim()) {
        return;
      }

      const data = {
        id: "08",
        text: this.commentText,
        createdAt: new Date(),
        postedById: post.postedById,
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
            this.commentText = "";
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
      post.likes += 1;

      // Send a PUT request to update the post data including the number of likes
      fetch(`http://localhost:8001/api/posts/${post.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(post),
      })
        .then((response) => {
          if (response.ok) {
            console.log("Post updated successfully");
            this.$router.push("/posts"); // Redirect to the posts page after successful update
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
.item {
  background: rgb(255, 255, 255);
  margin-bottom: 5px;
  padding: 3px 5px;
  border-radius: 10px;
}
.info {
  width: 100%;
  align-self: left;
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

.postImage {
  max-width: 80%;
  height: 150px;
}

.likeImage {
  width: 20px;
  height: 20px;
}

.comments {
  height: max-content;
  width: 100%;
  background-color: rgb(100, 100, 100);
  border-radius: 5px;
  padding: 10px;
  color: white;
}

.comment {
  margin-top: 5px;
  display: flex;
  flex-direction: column;
  background-color: rgb(143, 141, 141);
  padding: 5px;
  border-radius: 10px;
}
.commentInfo {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  background-color: rgb(180, 180, 180);
}
.addcomment {
  margin-top: 10px;
  background-color: rebeccapurple;
}
.deleteComment {
  width: fit-content;
  height: fit-content;
  border-radius: 10px;
  background-color: red;
  padding: 5px;
}
</style>
