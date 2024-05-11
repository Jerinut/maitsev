<template>
  <div class="profile">
    <div id="form">
      <h3>{{ isOwnProfile ? 'Edit Your Profile' : 'View Profile' }}</h3>

      <label for="username">Username: </label>
      <input name="username" type="text" id="username" required v-model="profile.username" :disabled="!isOwnProfile" />

      <label for="bio">Bio: </label>
      <textarea name="bio" id="bio" required v-model="profile.bio" :disabled="!isOwnProfile"></textarea>

      <label for="cuisines">Cuisines: </label>
      <select name="cuisines" id="cuisines" multiple v-model="profile.cuisines" :disabled="!isOwnProfile">
        <option v-for="cuisine in cuisines" :key="cuisine" :value="cuisine">{{ cuisine }}</option>
      </select>

      <label for="likedIngredients">Liked Ingredients: </label>
      <select name="likedIngredients" id="likedIngredients" multiple v-model="profile.likedIngredients" :disabled="!isOwnProfile">
        <option v-for="ingredient in likedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
      </select>

      <label for="dislikedIngredients">Disliked Ingredients: </label>
      <select name="dislikedIngredients" id="dislikedIngredients" multiple v-model="profile.dislikedIngredients" :disabled="!isOwnProfile">
        <option v-for="ingredient in dislikedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}
        </option>
      </select>

      <button v-if="isOwnProfile" @click="updateProfile" class="updateProfile">Update Profile</button>
    </div>
  </div>
</template>


<script>
export default {
  name: "Profile",
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
      dislikedIngredients: ['Mushroom', 'Cilantro', 'Anchovy', 'Olive'],
      currentProfileId: 'e3cf4b4b-a57b-41ba-a19e-355b75f90a2e' // Replace 'default-own-id' with logic to get the current user's ID
    };
  },
  computed: {
    isOwnProfile() {
      // Check if the current route's user ID matches the "logged in" user's ID
      return this.$route.params.id === this.currentProfileId;
    }
  },
  methods: {
    fetchProfile() {
      const id = this.currentProfileId; // Now using dynamic ID
      fetch(`http://localhost:8000/api/profiles/${id}`)
        .then(response => response.json())
        .then(data => {
          this.profile = data;
          this.isOwnProfile = (id === 'default-own-id'); // Logic to determine if it's the current user's profile
        })
        .catch(err => console.error('Failed to fetch profile:', err.message));
    },
    updateProfile() {
      if (!this.isOwnProfile) return; // Prevent updating if not viewing own profile

      const id = this.currentProfileId;
      fetch(`http://localhost:8000/api/profiles/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.profile),
      })
        .then(() => {
          console.log('Profile updated successfully');
          this.$router.push("/profiles"); // Adjust the path as needed
        })
        .catch(error => {
          console.error('Failed to update profile:', error);
          this.errorMessage = 'Update failed';
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

input[type="text"],
textarea,
select {
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