<template>
    <div class="card-container">
      <div v-for="recipe in recipes" :key="recipe.id">
        <div class="card-bg">
          <img class="card-image" v-if="recipe.image" :src="recipe.image" alt="Image" />
          <img class="card-image" v-else src="../assets/food-stock.png" alt="Placeholder Image" />
          <div class="card-content">
            <h3>Dish : {{ recipe.title }}</h3>
            <h3>Serving size : {{ recipe.servingSize }}</h3>
            <h3>Time to cook :{{ recipe.cookingTime}}</h3>
            <h3>Cuisine Type : {{recipe.cuisineType}}</h3>
            <h3>Steps Needed : {{recipe.steps}}</h3>
            </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "getData",
    props: ["recipes"],
  };
  </script>
  
  <style scoped>
  .card-content {
    position: relative;
  }
  
  .card-bg {
    max-width: 100%;
    max-height: 100%;
    display: block;
  }
  
  .card-container {
    width: 80%;
    position: relative;
    padding: 10px;
    margin: 10px auto;
    text-align: left;
  }

  .card-image {
    width: 300px; /* Specify the width */
    height: 200px; /* Specify the height */
    object-fit: cover; /* This will make sure the image is scaled correctly */
  }
  </style>
  