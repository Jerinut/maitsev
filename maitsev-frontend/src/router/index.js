import { createRouter, createWebHistory } from 'vue-router'

import Feed from "@/views/Feed.vue";
import Others from "@/views/Others.vue";
import Recipes from "@/views/Recipes.vue";
import Posts from "@/views/Posts.vue";
import Chat from "@/views/Chat.vue";
import MyProfile from "@/views/MyProfile.vue";
import Register from "@/views/Register.vue";



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
        path: '/recipes',
        name: Recipes,
        component: Recipes,
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
        path: "/my-profile",
        name: MyProfile,
        component: MyProfile,
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