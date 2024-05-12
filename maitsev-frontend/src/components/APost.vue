<template>
  <div class="post">
    <div id="form">
      <h3>A Post</h3>

      <label for="description">Description: </label>
      <input name="description" type="text" id="description" required v-model="post.description"/>

      <label for="imgUrl">Image URL: </label>
      <input name="imgUrl" type="text" id="imgUrl" v-model="post.imgUrl" />

      <label for="likes">Likes: </label>
      <span>{{ post.likes }}</span> <br />

      <label for="createdAt">Created At: </label>
      <span>{{ post.createdAt }}</span> <br />

      <label for="postedBy">Posted By: </label>
      <span>{{ postedBy }}</span> <br />

      <label for="tags">Tags: </label>
      <ul>
        <li v-for="(tag, index) in post.tags" :key="index">{{ tag }}</li>
      </ul> <br />

    </div>
    <div>
      <button @click="updatePost" class="updatePost">Update Post</button>
      <button @click="deletePost" class="deletePost">Delete Post</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "APost",
  data() {
    return {
      post: {
        id: "", // Add other attributes of the post here
        description: "",
        imgUrl: "",
        likes: 0,
        createdAt: "",
        postedById: "",
        tags: []
      },
      postedBy: "",
    };
  },
  methods: {
    fetchPost(id) {
      fetch(`http://localhost:8001/api/posts/${id}`)
        .then((response) => response.json())
        .then((data) => {
          this.post = data;
          this.fetchUserName(data.postedById); // Fetch username after fetching post
        })
        .catch((err) => console.log(err.message));
    },
    fetchUserName() {
      fetch(`http://localhost:8000/api/profiles/${this.post.postedById}`)
        .then((response) => response.json())
        .then((data) => {
          this.postedBy = data.username; // Store the username in postedBy variable
        })
        .catch((err) => console.log(err.message));
    },
    updatePost() {
      fetch(`http://localhost:8001/api/posts/${this.post.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.post),
      })
        .then((response) => {
          console.log(response.data);
          this.$router.push("/posts");
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deletePost() {
      fetch(`http://localhost:8001/api/posts/${this.post.id}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            console.log("Post deleted successfully");
          } else {
            console.log("Failed to delete post");
          }
          this.$router.push("/posts");
        })
        .catch((error) => {
          console.error("Error deleting post:", error);
        });
    },
  },
  mounted() {
    this.fetchPost(this.$route.params.id);
  },
};
</script>

<style scoped>
#form {
  max-width: 420px;
  margin: 10px auto;
  background: rgb(167, 154, 154);
  text-align: left;
  padding: 20px;
  border-radius: 10px;
}
h3 {
  text-align: center;
  color: rgb(8, 110, 110);
}
label {
  color: rgb(8, 110, 110);
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.8em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid white;
  color: blue;
}
button {
  background: rgb(8, 110, 110);
  border: 0;
  padding: 10px 20px;
  margin-top: 10px;
  color: white;
  border-radius: 20px;
}
.post {
  margin: 10px;
}
</style>
