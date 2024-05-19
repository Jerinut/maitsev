<template>
  <div class="root">
    <div class="login-form">
      <h2>Login</h2>
      <form @submit.prevent="handleLoginSubmit">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="loginForm.username" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="loginForm.password" required>
        </div>
        <button type="submit">Login</button>
      </form>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
    </div>
    <div class="signup-form">
      <h2>Sign Up</h2>
      <form @submit.prevent="handleSignupSubmit">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="signupForm.username" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="signupForm.password" required>
        </div>
        <div class="form-group">
          <label for="bio">Bio:</label>
          <textarea id="bio" v-model="signupForm.bio"></textarea>
        </div>
        <div class="form-group">
          <label for="cuisines">Cuisines:</label>
          <select id="cuisines" v-model="signupForm.cuisines" multiple required>
            <option v-for="cuisine in cuisines" :key="cuisine" :value="cuisine">{{ cuisine }}</option>
          </select>
        </div>
        <div class="form-group">
          <label for="likedIngredients">Liked Ingredients:</label>
          <select id="likedIngredients" v-model="signupForm.likedIngredients" multiple required>
            <option v-for="ingredient in likedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
          </select>
        </div>
        <div class="form-group">
          <label for="dislikedIngredients">Disliked Ingredients:</label>
          <select id="dislikedIngredients" v-model="signupForm.dislikedIngredients" multiple required>
            <option v-for="ingredient in dislikedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
          </select>
        </div>
        <button type="submit">Sign Up</button>
      </form>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
    </div>
  </div>
</template>

<script>
import { authState } from '../auth';
export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      signupForm: {
        username: '',
        password: '',
        bio: '',
        cuisines: [],
        likedIngredients: [],
        dislikedIngredients: []
      },
      errorMessage: '',
      successMessage: '',
      // Sample options for cuisines, liked ingredients, and disliked ingredients
      cuisines: ['Italian', 'Mexican', 'Japanese', 'Indian'],
      likedIngredients: ['Salt', 'Sugar', 'Pepper', 'Tomato'],
      dislikedIngredients: ['Mushroom', 'Cilantro', 'Anchovy', 'Olive']
    };
  },
  methods: {
    async handleLoginSubmit() {
      const loginData = {
        name: this.loginForm.username,
        password: this.loginForm.password
      };

      try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(loginData)
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const responseJson = await response.json();
        const token = responseJson.jwtToken;
        const userId = responseJson.userId;

        if (token.startsWith("ey")) {
          authState.login({ id: userId }, token);

          this.successMessage = "Login successful!";
          this.$router.push("/");
        }
      } catch (error) {
        console.error('Login failed:', error);
        this.errorMessage = 'Login failed';
      }
    },
    async handleSignupSubmit() {
      const userData = {
        name: this.signupForm.username,
        password: this.signupForm.password,
        roles: 'USER' // Hardcoded role
      };

      try {
        const userResponse = await fetch("http://localhost:8080/api/auth/signup", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(userData)
        });

        if (!userResponse.ok) {
          throw new Error(`HTTP error! status: ${userResponse.status}`);
        }

        const responseJson = await userResponse.json();
        const token = responseJson.jwtToken;
        const userId = responseJson.userId;

        if (token.startsWith("ey")) {
          authState.login({ id: userId }, token);

          const profileData = {
            id: userId,
            username: this.signupForm.username,
            bio: this.signupForm.bio,
            cuisines: this.signupForm.cuisines,
            likedIngredients: this.signupForm.likedIngredients,
            dislikedIngredients: this.signupForm.dislikedIngredients
          };

          const profileResponse = await fetch("http://localhost:8080/api/profiles", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(profileData)
          });

          if (!profileResponse.ok) {
            throw new Error(`HTTP error! status: ${profileResponse.status}`);
          }

          this.successMessage = "Signup successful!";
          this.$router.push("/");
        }
      } catch (error) {
        console.error('Failed to register:', error);
        this.errorMessage = 'Registration failed';
      }
    }
  }
};
</script>

<style scoped>
.root {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
.login-form {
  min-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  height: max-content;
}
.signup-form {
  min-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: bold;
}

input[type="text"],
input[type="password"],
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

button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.error-message,
.success-message {
  margin-top: 10px;
  padding: 10px;
  border-radius: 4px;
}

.error-message {
  background-color: #ffcdd2;
  color: #c62828;
}

.success-message {
  background-color: #c8e6c9;
  color: #2e7d32;
}
</style>
