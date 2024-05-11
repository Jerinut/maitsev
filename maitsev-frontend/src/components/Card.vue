<template>
    <div class="card-container">
      <div v-for="recipe in recipes.value" :key="recipe.id">
        <div class="card-bg">
          <img v-if="recipe.image" :src="recipe.image" alt="Recipe Image">
          <img v-else src="placeholder.jpg" alt="Placeholder Image"> <div class="card-content">
            <h3>{{ recipe.title }}</h3>
            <p v-if="recipe.description">{{ recipe.description }}</p>
            </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CardComponent',
    data() {
      return {
        recipes: [] // Replace with your actual notifications data
      }
    },
    created() {
      // Fetch notifications when component is created
      // Replace 'fetchNotifications' with your actual method
      this.recipes = this.fetchRecipes();
    },
    methods: {
      fetchRecipes() {
        // Fetch notifications from your API or data source
        // This is just a placeholder
        return [
          {id:'01',title:"pasta"},
          {id:'01',title:"chicken soup"}
          // Add more notifications as needed
        ];
      }
    }
  }
  </script>
  
  <style scoped>
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.card-bg {
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  padding: 1rem;
  width: calc(33% - 1rem); /* Adjust width for responsive layout */
}
.card-content {
  margin-top: 1rem;
}
</style>