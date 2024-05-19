<template>
  <div class="feed">
    <h1>For You Page</h1>
    <div class="posts">
      <h2>Posts</h2>
      <div v-if="postsLoading">Loading posts...</div>
      <div v-else-if="postsError">{{ postsError }}</div>
      <div v-else>
        <Post
            v-for="post in posts"
            :key="post.id"
            :post="post"
            @like-post="likePost"
            @submit-comment="submitComment"
            @delete-comment="deleteComment"
        />
      </div>
    </div>
    <div class="recipes">
      <h2>Recipes</h2>
      <div v-if="recipesLoading">Loading recipes...</div>
      <div v-else-if="recipesError">{{ recipesError }}</div>
      <div v-else>
        <get-data :recipes="recipes"/>
      </div>
    </div>
  </div>
</template>

<script>
import Post from "@/components/Post.vue";
import getData from "@/components/getData.vue";

export default {
  name: 'Feed',
  components: {getData, Post},
  data() {
    return {
      userId: '01', // Replace this with the actual user ID
      posts: [],
      postsLoading: true,
      postsError: null,
      recipes: [],
      recipesLoading: true,
      recipesError: null,
    };
  },
  created() {
    this.fetchPosts();
    this.fetchRecipes();
  },
  methods: {
    async fetchPosts() {
      this.postsLoading = true;
      this.postsError = null;
      try {
        const response = await fetch(`http://localhost:8007/api/for-you/posts/${this.userId}`);
        console.log(response);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        this.posts = data;
      } catch (error) {
        this.postsError = 'Failed to load posts';
      } finally {
        this.postsLoading = false;
      }
    },
    async fetchRecipes() {
      this.recipesLoading = true;
      this.recipesError = null;
      try {
        const response = await fetch(`http://localhost:8007/api/for-you/recipes/${this.userId}`);
        console.log(response);

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        this.recipes = data;
      } catch (error) {
        this.recipesError = 'Failed to load recipes';
      } finally {
        this.recipesLoading = false;
      }
    },
    likePost(post) {
      post.likes += 1;

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
            } else {
              console.log("Failed to update post");
            }
          })
          .catch((error) => {
            console.error("Error updating post:", error);
          });
    },
    submitComment(post, commentText) {
      if (!commentText.trim()) {
        return;
      }

      const data = {
        id: "08",
        text: commentText,
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
  },
};
</script>

<style scoped>
.feed {
  padding: 20px;
}

.posts, .recipes {
  margin-bottom: 20px;
}

.post, .recipe {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

h1, h2, h3 {
  margin: 0;
  padding-bottom: 10px;
}

p {
  margin: 0;
}
</style>
