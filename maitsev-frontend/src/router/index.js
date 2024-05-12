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
    },
    {
        path: "/posts/:id",
        name: APost,
        component: APost,
        props: true  // Enables route params to be passed as props to the component
    },
    {
        path: '/posts/addpost',
        name: AddPost,
        component: AddPost,
    },
    {
        path: '/recipes',
        name: Recipes,
        component: Recipes,
    },
    {
        path: '/recipes/addrecipe',
        name: AddRecipe,
        component: AddRecipe,
    },
    {
        path: '/others',
        name: Others,
        component: Others,
    },
    {
        path: "/chat",
        name: Chat,
        component: Chat,
    },
    {
        path: "/chat/:id",
        name: Chat,
        component: Chat,
    },
    {
        path: "/profile/:id",
        name: Profile,
        component: Profile,
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

export default router