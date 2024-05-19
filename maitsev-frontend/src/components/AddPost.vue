<template>
  <div class="form">
    <h3>Add a Post</h3>

    
    <label for="description">Description: </label>
    <input
      name="description"
      type="text"
      id="description"
      required
      v-model="post.description"
    />

    <label for="imgUrl">Image URL: </label>
    <input name="imgUrl" type="text" id="imgUrl" v-model="post.imgUrl" />

    <label for="tags">Tags: </label>
    <input
      name="tags"
      type="text"
      id="tags"
      v-model="tagInput"
      @keyup.enter="addTag"
      placeholder="Enter tags separated by commas"
    />

    <button @click="addPost" class="addPost">Add Post</button>
  </div>
</template>
  
  <script>
  import {authState} from '../auth';
export default {
  name: "AddPost",
  data() {
    return {
      post: {
        description: "",
        imgUrl: "",
        tags: [],
        likes: 0,
        createdAt: new Date(),
        postedById: authState.user?.id,
      },
    };
  },
  methods: {
    addTag() {
      const tags = this.tagInput.split(",").map((tag) => tag.trim());
      this.post.tags.push(...tags);
      this.tagInput = "";
    },
    addPost() {
      // Send an HTTP POST request to the specified URI with the defined body
      fetch("http://localhost:8001/api/posts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.post),
      })
        .then((response) => {
          if (response.ok) {
            console.log("Post added successfully");
            this.$router.push("/posts"); // Redirect to the posts page after successful addition
          } else {
            console.log("Failed to add post");
          }
        })
        .catch((error) => {
          console.error("Error adding post:", error);
        });
    },
  },
};
</script>
  
  <style scoped>
.form {
  max-width: 420px;
  margin: 30px auto;
  background: rgb(167, 154, 154);
  text-align: left;
  padding: 40px;
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
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  align-items: center;
  text-align: center;
}
</style>
  