<template>
  <div>
    <ul>
      <li v-for="profile in profiles" :key="profile.id">
        <router-link :to="`/profile/${profile.id}`">{{ profile.username }}</router-link>
        <button @click="startChat(profile.id)">Message</button>
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
        .catch(error => console.error('Error fetching users:', error));
    },
    startChat(profileId) {
      // Check if a chat exists, if not create one, then navigate
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
