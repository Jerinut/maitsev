<template>
  <div class="item">
    <a class="singlepost" :href="'/posts/' + post.id">
      <span class="info"> <b>Posted By:</b> {{ post.postedBy }} </span><br />
      <span class="info"> <b>Likes:</b> {{ post.likes }} </span><br />
      <span class="info"> <b>Created At:</b> {{ post.createdAt }} </span><br />
      <span class="info"> <b>Description:</b> {{ post.description }} </span><br />
      <img class="postImage" :src="require('@/assets/maitsev-logo.png')" alt="Post Image" />
    </a>

    <button @click="likePost(post)" class="likePost">
      <img class="likeImage" src="@/assets/like.png" alt="Like Button" />
    </button>

    <div class="comments">
      <h2 v-if="post.comments && post.comments.length > 0">Comments:</h2>
      <div class="comment" v-for="comment in post.comments" :key="comment.id">
        <div class="commentInfo">
          <p>Date: {{ comment.createdAt }}</p>
          <p>By: {{ comment.postedBy }}</p>
        </div>
        <span>{{ comment.text }}</span>
        <button @click="deleteComment(post.id, comment.id)" class="deleteComment">
          Delete
        </button>
      </div>

      <textarea class="addcomment" v-model="commentText" placeholder="Enter your comment..."></textarea>
      <button @click="submitComment(post)" class="submitComment">Submit</button>
    </div>
  </div>
</template>

<script>


export default {
  name: "Post",
  props: {
    post: Object
  },
  data() {
    return {
      commentText: ''
    };
  },
  methods: {
    likePost(post) {
      this.$emit('like-post', post);
    },
    submitComment(post) {
      if (this.commentText.trim()) {
        this.$emit('submit-comment', post, this.commentText);
        this.commentText = '';
      }
    },
    deleteComment(postId, commentId) {
      this.$emit('delete-comment', postId, commentId);
    }
  }
};
</script>

<style scoped>
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
