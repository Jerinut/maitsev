<template>
  <div class="profile">
    <div id="form">
      <h3>Edit Profile</h3>

      <label for="username">Username: </label>
      <input name="username" type="text" id="username" required v-model="profile.username" />
      
      <label for="bio">Bio: </label>
      <textarea name="bio" id="bio" required v-model="profile.bio"></textarea>
      
      <label for="cuisines">Cuisines: </label>
      <select name="cuisines" id="cuisines" multiple v-model="profile.cuisines">
        <option v-for="cuisine in cuisines" :key="cuisine" :value="cuisine">{{ cuisine }}</option>
      </select>

      <label for="likedIngredients">Liked Ingredients: </label>
      <select name="likedIngredients" id="likedIngredients" multiple v-model="profile.likedIngredients">
        <option v-for="ingredient in likedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
      </select>

      <label for="dislikedIngredients">Disliked Ingredients: </label>
      <select name="dislikedIngredients" id="dislikedIngredients" multiple v-model="profile.dislikedIngredients">
        <option v-for="ingredient in dislikedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
      </select>
    </div>
    <div>
      <button @click="updateProfile" class="updateProfile">Update Profile</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyProfile",
  data() {
    return {
      profile: {
        username: '',
        bio: '',
        cuisines: [],
        likedIngredients: [],
        dislikedIngredients: []
      },
      cuisines: ['Italian', 'Mexican', 'Japanese', 'Indian'],
      likedIngredients: ['Salt', 'Sugar', 'Pepper', 'Tomato'],
      dislikedIngredients: ['Mushroom', 'Cilantro', 'Anchovy', 'Olive']
    };
  },
  methods: {
    fetchProfile() {
      const id = 'e3cf4b4b-a57b-41ba-a19e-355b75f90a2e'; // Using a fixed id since authentication isn't implemented
      fetch(`http://localhost:8000/api/profiles/${id}`)
        .then(response => response.json())
        .then(data => this.profile = data)
        .catch(err => console.error('Failed to fetch profile:', err.message));
    },
    updateProfile() {
      const id = 'e3cf4b4b-a57b-41ba-a19e-355b75f90a2e'; // Using a fixed id
      fetch(`http://localhost:8000/api/profiles/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.profile),
      })
      .then(() => {
        // Redirect or perform any follow-up actions immediately after sending the data
        console.log('Form submitted');
        this.$router.push("/api/profiles"); // Adjust the path as needed
      })
      .catch(error => {
        console.error('Failed to submit form:', error);
        this.errorMessage = 'Submission failed';
      });
    }
  },
  mounted() {
    this.fetchProfile();
  },
};
</script>

<style scoped>
.profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

#form {
  display: flex;
  flex-direction: column;
}

input[type="text"], textarea, select {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
}

button.updateProfile {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>