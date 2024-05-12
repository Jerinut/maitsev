
<template>
  <div class="fetch">
    <h1>Recipes </h1>
    <div class="recipe-btn-container">
      <a href="/recipes/addrecipe">
        <h1>Add a Recipe</h1>
      </a>
    </div>
    <get-data :recipes="recipes"/>
  </div>

</template>

<script>
// @ is an alias to /src
import getData from "@/components/getData.vue";   
export default {
  name: "Recipe",
  components: {
    getData
  },
  data: function() {
    return {
    recipes:[{
      id: 1,
      title: "chicken"
    }]
    }
    },
    mounted() {
        fetch('http://localhost:8080/api/recipes')
        .then((response) => 
        response.json())
        .then(data => this.recipes = data)
        .catch(err => console.log(err.message))
        console.log(this.recipes)
    }
};
</script>

