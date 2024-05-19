<template>
  <header>
    <nav class="navbar">
      <div class="navbar-brand">
        <router-link v-if="authState.isAuthorized" to="/">
          <img class="main-logo" src="../assets/maitsev-logo.png" alt="Logo">
        </router-link>
        <router-link v-else to="/register">
          <img class="main-logo" src="../assets/maitsev-logo.png" alt="Logo">
        </router-link>
      </div>

      <div class="navbar-menu" v-if="authState.isAuthorized">
        <div class="navbar-end">
          <nav class="dropdown notification">
            <button class="dropbtn" @click="toggleNotificationDropdown">Notifications</button>
            <div class="dropdown-content">
              <div v-for="notification in notifications" :key="notification.id" class="notification-item">
                <div :class="getStatusClass(notification.status)">
                  <p :style="{fontWeight: 500}">{{ notification.status }}</p>
                  <p>{{ notification.message }}</p>
                </div>
              </div>
            </div>
          </nav>
          <router-link to="/others" class="navbar-item">Others</router-link>
          <router-link to="/posts" class="navbar-item">Posts</router-link>
          <router-link to="/recipes" class="navbar-item">Recipes</router-link>
          <router-link to="/chat" class="navbar-item">Chat</router-link>
          <nav class="dropdown user-menu">
            <button class="dropbtn" @click="toggleUserMenuDropdown">
              <img class="user-menu-img" src="../assets/user-menu.png" alt="User Menu">
            </button>
            <div class="dropdown-content">
              <router-link :to="`/profile/${authState.user.id}`" class="navbar-item">My Profile</router-link>
              <button class="dropbtn" @click="logout">Logout</button>
            </div>
          </nav>
        </div>
      </div>

      <div class="navbar-menu" v-else>
        <div class="navbar-end">
          <router-link to="/register" class="navbar-item">Sign Up</router-link>
          <router-link to="/register" class="navbar-item">Log In</router-link>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
import { authState } from '../auth';

export default {
  name: 'HeaderComponent',
  data() {
    return {
      authState,
      notifications: []
    };
  },
  created() {
    if (this.authState.isAuthorized) {
      this.fetchNotifications(this.authState.user.id);
    }
  },
  methods: {
    async fetchNotifications(userId) {
      try {
        const response = await fetch(`http://localhost:8006/api/notifications/user/${userId}`);
        let responseJson = await response.json();
        if (responseJson?.length > 0) {
          this.notifications = responseJson;
        }
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    },
    getStatusClass(status) {
      switch (status) {
        case 'NEW CHAT':
          return 'new-chat';
        case 'NEW MESSAGE':
          return 'new-message';
        case 'NEW REVIEW':
          return 'new-review';
        default:
          return '';
      }
    },
    logout() {
      authState.logout();
      this.$router.push('/register'); // Redirect to register page
      console.log("Logged out.");
    },
    toggleNotificationDropdown() {
      // Implement toggle functionality if needed
    },
    toggleUserMenuDropdown() {
      // Implement toggle functionality if needed
    }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 48px;
  margin-bottom: 8px;
  position: sticky;
  top: 24px;
}

.user-menu-img {
  width: 48px;
}
.main-logo {
  width: auto;
  height: 64px;
}
.navbar-end {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.navbar-item {
  text-decoration: none;
  color: #333;
  font-size: 16px;
  font-weight: 600;
  margin-left: 20px;
}
.notification, .user-menu {
  position: relative;
}
.notification > .dropbtn {
  color: #ea4260;
}
.user-menu > .dropdown-content {
  right: 4px;
  padding: 16px 6px;
  flex-direction: column;
  min-width: 160px;
}
.notification > .dropdown-content {
  max-height: 400px;
  overflow-y: auto;
  min-width: 240px;
}
.user-menu .dropbtn {
  margin-top: 16px;
  margin-left: 20px;
}
.dropbtn {
  background-color: transparent;
  color: #007bff;
  padding: 0 8px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
}
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #fafafa;
  z-index: 1;
}
.dropdown:hover .dropdown-content {
  display: block;
}
.notification-item {
  padding: 0px;
}
.notification-item > div {
  padding: 1px 0;
  border-bottom: 1px solid #e3e3e3;
}
.new-chat {
  background-color: #dff0d8;
}
.new-message {
  background-color: #d9edf7;
}
.new-review {
  background-color: #fcf8e3;
}
</style>
