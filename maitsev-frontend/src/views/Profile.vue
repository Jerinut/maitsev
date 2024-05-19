<template>
  <div class="profile">
    <div class="form-container">
      <h2 class="form-title">{{ isOwnProfile ? 'Edit Your Profile' : 'View Profile' }}</h2>

      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" required v-model="profile.username" disabled />
      </div>

      <div class="form-group">
        <label for="bio">Bio:</label>
        <textarea id="bio" required v-model="profile.bio" :disabled="!isOwnProfile"></textarea>
      </div>

      <div class="form-group">
        <label for="cuisines">Cuisines:</label>
        <select id="cuisines" multiple v-model="profile.cuisines" :disabled="!isOwnProfile">
          <option v-for="cuisine in cuisines" :key="cuisine" :value="cuisine">{{ cuisine }}</option>
        </select>
      </div>

      <div class="form-group">
        <label for="likedIngredients">Liked Ingredients:</label>
        <select id="likedIngredients" multiple v-model="profile.likedIngredients" :disabled="!isOwnProfile">
          <option v-for="ingredient in likedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
        </select>
      </div>

      <div class="form-group">
        <label for="dislikedIngredients">Disliked Ingredients:</label>
        <select id="dislikedIngredients" multiple v-model="profile.dislikedIngredients" :disabled="!isOwnProfile">
          <option v-for="ingredient in dislikedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
        </select>
      </div>

      <button v-if="isOwnProfile" @click="updateProfile" class="btn btn-primary">Update Profile</button>
    </div>
  </div>
</template>

<script>
import { jwtDecode } from 'jwt-decode';

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
      dislikedIngredients: ['Mushroom', 'Cilantro', 'Anchovy', 'Olive', 'Onion', 'Garlic']
    };
  },
  computed: {
    isOwnProfile() {
      const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode(token);
        return this.$route.params.id === decoded.userId;
      }
      return false;
    }
  },
  methods: {
    fetchProfile(id) {
      fetch(`http://localhost:8000/api/profiles/${id}`)
        .then(response => response.json())
        .then(data => {
          this.profile = data;
        })
        .catch(err => console.error('Failed to fetch profile:', err.message));
    },
    updateProfile() {
      if (!this.isOwnProfile) return; // Prevent updating if not viewing own profile

      const token = localStorage.getItem('jwtToken');
      const decoded = jwtDecode(token);
      const id = decoded.userId;
      
      fetch(`http://localhost:8000/api/profiles/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(this.profile),
      })
        .then(() => {
          console.log('Profile updated successfully');
          this.$router.push(`/profile/${id}`); // Adjust the path as needed
        })
        .catch(error => {
          console.error('Failed to update profile:', error);
          this.errorMessage = 'Update failed';
        });
    }
  },
  watch: {
    '$route.params.id': function(newId) {
      if (newId) {
        this.fetchProfile(newId);  // Call fetchProfile whenever the ID in the URL changes
      }
    }
  },
  mounted() {
    this.fetchProfile(this.$route.params.id);
  },
};
</script>

<style scoped>
.profile {
  display: flex;
  justify-content: center;
  padding: 20px;
  background: #f9f9f9;
}

.form-container {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 600px;
  box-sizing: border-box;
}

.form-title {
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
textarea,
select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

select[multiple] {
  height: auto;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-align: center;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
