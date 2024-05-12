<template>
  <div class="AllPosts">
    <div id="posts-list">
      <div class="heading">
        <h2>All Posts</h2>
        <a href="/posts/addpost">
          <h1 >Add a post</h1>
        </a>
      </div>
      
      <ul>
        <div class="item" v-for="post in posts" :key="post.id">
          <a class="singlepost" :href="'/posts/' + post.id">
            <span class="info"> <b>Posted By:</b> {{ post.postedBy }} </span><br />
            <span class="info"> <b>Likes:</b> {{ post.likes }} </span><br />
            <span class="info">
              <b>Created At:</b> {{ post.createdAt }} 
            </span><br />
            <span class="info">
              <b>Description:</b> {{ post.description }} 
            </span><br />
            <img class="postImage" :src="require('@/assets/maitsev-logo.png')" alt="Post Image" />
          </a>
          
          <button @click="likePost(post)" class="likePost">
            <!-- Use local image for the like button -->
            <img class="likeImage" src="@/assets/like.png" alt="Like Button" />
          </button>
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
</style>
