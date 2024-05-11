<template>
 <div class="root">
   <div class="login-form">
     <h2>Login</h2>
     <form @submit.prevent="handleLoginSubmit">
       <div class="form-group">
         <label for="username">Username:</label>
         <input type="text" id="username" v-model="form.username" required>
       </div>
       <div class="form-group">
         <label for="password">Password:</label>
         <input type="password" id="password" v-model="form.password" required>
       </div>
       <button type="submit">Login</button>
     </form>
     <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
     <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
   </div>
   <div class="signup-form">
     <h2>Sign Up</h2>
     <form @submit.prevent="handleSubmit">
       <div class="form-group">
         <label for="username">Username:</label>
         <input type="text" id="username" v-model="form.username" required>
       </div>
       <div class="form-group">
         <label for="password">Password:</label>
         <input type="password" id="password" v-model="form.password" required>
       </div>
       <div class="form-group">
         <label for="bio">Bio:</label>
         <textarea id="bio" v-model="form.bio"></textarea>
       </div>
       <div class="form-group">
         <label for="cuisines">Cuisines:</label>
         <select id="cuisines" v-model="form.cuisines" multiple required>
           <option v-for="cuisine in cuisines" :key="cuisine" :value="cuisine">{{ cuisine }}</option>
         </select>
       </div>
       <div class="form-group">
         <label for="likedIngredients">Liked Ingredients:</label>
         <select id="likedIngredients" v-model="form.likedIngredients" multiple required>
           <option v-for="ingredient in likedIngredients" :key="ingredient" :value="ingredient">{{ ingredient }}</option>
         </select>
       </div>
       <div class="form-group">
         <label for="dislikedIngredients">Disliked Ingredients:</label>
         <select id="dislikedIngredients" v-model="form.dislikedIngredients" multiple required>
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
export default {
  data() {
    return {
      form: {
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
    handleSubmit() {
      const apiUrl = 'http://localhost:8000/api/profiles';
      fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.form)
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