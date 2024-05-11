<template>
  <div class="others-page">
    <h2 class="page-title">Other Profiles</h2>
    <ul class="profile-list">
      <li v-for="profile in profiles" :key="profile.id" class="profile-item">
        <router-link :to="`/profile/${profile.id}`" class="profile-link">{{ profile.username }}</router-link>
        <button @click="startChat(profile.id)" class="message-btn">Message</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profiles: [],
      currentProfileId: 'e3cf4b4b-a57b-41ba-a19e-355b75f90a2e'  // Placeholder
    };
  },
  created() {
    this.fetchProfiles();
  },
  methods: {
    fetchProfiles() {
      fetch('http://localhost:8000/api/profiles')
        .then(response => response.json())
        .then(data => {
          this.profiles = data.filter(profile => profile.id !== this.currentProfileId);
        })
        .catch(error => console.error('Error fetching profiles:', error));
    },
    startChat(profileId) {
      fetch(`http://localhost:8000/api/chats/${profileId}`, {
        method: 'POST'
      })
      .then(response => response.json())
      .then(chat => {
        this.$router.push(`/chat/${chat.id}`);
      })
      .catch(error => console.error('Error starting chat:', error));
    }
  }
};
</script>

<style scoped>
.others-page {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 80%;
  margin: 20px auto;
}

.page-title {
  text-align: center;
  color: #333;
}

.profile-list {
  list-style: none;
  padding: 0;
}

.profile-item {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 10px;
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.profile-link {
  font-weight: bold;
  color: #007bff;
  text-decoration: none;
}

.message-btn {
  padding: 5px 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.message-btn:hover {
  background-color: #0056b3;
}
</style>
