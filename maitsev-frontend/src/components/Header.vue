<template>
  <header>
    <nav class="navbar">
      <div class="navbar-brand">
        <router-link v-if="isAuthorized" to="/">
          <img class="main-logo" src="../assets/maitsev-logo.png" alt="Logo">
        </router-link>
        <router-link v-else to="/register">
          <img class="main-logo" src="../assets/maitsev-logo.png" alt="Logo">
        </router-link>
      </div>

      <div class="navbar-menu" v-if="isAuthorized">
        <div class="navbar-end">
          <nav class="dropdown notification">
            <button class="dropbtn" @click="toggleDropdown">Notifications</button>
            <div v-if="showDropdown" class="dropdown-content">
              <p class="notification" v-for="notification in notifications" :key="notification.id">
                {{ notification.text }}
              </p>
            </div>
          </nav>
          <router-link to="/others" class="navbar-item">Others</router-link>
          <router-link to="/posts" class="navbar-item">Posts</router-link>
          <router-link to="/recipes" class="navbar-item">Recipes</router-link>
          <router-link to="/chat" class="navbar-item">Chat</router-link>
          <nav class="dropdown user-menu">
            <button class="dropbtn" @click="toggleDropdown">
              <img class="user-menu-img" src="../assets/user-menu.png" alt="User Menu">
            </button>
            <div  class="dropdown-content">
              <router-link to="/profile/02" class="navbar-item">My Profile</router-link>
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
export default {
  name: 'HeaderComponent',
  data() {
    return {
      showDropdown: true,
      isAuthorized: true, // Replace with your actual authorization logic
      notifications: [] // Replace with your actual notifications data
    }
  },
  created() {
    // Fetch notifications when component is created
    // Replace 'fetchNotifications' with your actual method
    this.notifications = this.fetchNotifications();
  },
  methods: {
    fetchNotifications() {
      // Fetch notifications from your API or data source
      // This is just a placeholder
      return [
        { id: 1, text: 'Notification 1' },
        { id: 2, text: 'Notification 2' },
        // Add more notifications as needed
      ];
    },
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },
    logout(){
      console.log("Logged out.")
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
.navbar-menu * {
  margin-left: 20px;
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
}
.notification, .user-menu {
  position: relative;
}
.notification > .dropbtn {
  color: #ea4260;
}
.user-menu > .dropdown-content {
  right: 4px;
}
.user-menu  .dropbtn {
  margin-top: 16px;
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
  min-width: 160px;
  z-index: 1;
  padding: 16px 6px;
}

.dropdown:hover .dropdown-content {
  display: block;
}
</style>