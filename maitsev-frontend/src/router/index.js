import { createRouter, createWebHistory } from 'vue-router'

import Feed from "@/views/Feed.vue";
import Others from "@/views/Others.vue";
import Recipes from "@/views/Recipes.vue";
import Posts from "@/views/Posts.vue";
import Chat from "@/views/Chat.vue";
import Profile from "@/views/Profile.vue";
import Register from "@/views/Register.vue";
import AddPost from "@/components/AddPost.vue";
import APost from '@/components/APost.vue';
import AddRecipe from '@/components/AddRecipe.vue';

import { authState } from '../auth';

const routes = [
    {
       path: '/',
       name: Feed,
       component: Feed,
    },
    {
        path: '/posts',
        name: Posts,
        component: Posts,
        meta: { requiresAuth: true }
    },
    {
        path: "/posts/:id",
        name: APost,
        component: APost,
        meta: { requiresAuth: true },
        props: true  // Enables route params to be passed as props to the component
    },
    {
        path: '/posts/addpost',
        name: AddPost,
        component: AddPost,
        meta: { requiresAuth: true }
    },
    {
        path: '/recipes',
        name: Recipes,
        component: Recipes,
        meta: { requiresAuth: true }
    },
    {
        path: '/recipes/addrecipe',
        name: AddRecipe,
        component: AddRecipe,
        meta: { requiresAuth: true }
    },
    {
        path: '/others',
        name: Others,
        component: Others,
        meta: { requiresAuth: true }
    },
    {
        path: "/chat",
        name: Chat,
        component: Chat,
        meta: { requiresAuth: true }
    },
    {
        path: "/chat/:id",
        name: Chat,
        component: Chat,
        meta: { requiresAuth: true }
    },
    {
        path: "/profile/:id",
        name: Profile,
        component: Profile,
        meta: { requiresAuth: true },
        props: true  // Enables route params to be passed as props to the component
    },
    {
        path: "/register",
        name: "Register",
        component: Register,
    },
    { //will route to AllPosts view if none of the previous routes apply
        path: "/:catchAll(.*)",
        name: "Feed",
        component: Feed,
    }
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
      if (!authState.isAuthorized) {
        next({
          path: '/register',
          query: { redirect: to.fullPath }
        });
      } else {
        next();
      }
    } else {
      next();
    }
  });
  
export default router