<template>
  <div class="root">
    <div class="chat-container">
      <div class="chat-sidebar">
        <ul>
          <li v-for="chat in chats" :key="chat.id"
              :style="{ background: chat.id === selectedChat.id ? '#f5f7f6' : 'transparent' }"
              @click="loadMessages(chat.id)" class="chat-item">
            <div :style="{background: getRandomColor(chat.id)}" class="avatar">
              <p>
                {{ chat.userName.charAt(0).toUpperCase() }}
              </p>
            </div>
            <div class="chat-info">
              <h3>{{ chat.userName }}</h3>
              <p v-if="chat.messages[chat.messages.length-1]">
                {{chat.messages[chat.messages.length-1].content }}
              </p>
            </div>
          </li>
        </ul>
      </div>
      <div class="chat-messages">
        <div class="chat-header" v-if="selectedChat">
          <div :style="{background: getRandomColor(selectedChat.id)}" class="avatar">
            <p>
              {{ selectedChat.userName.charAt(0).toUpperCase() }}
            </p>
          </div>
          <h3>{{ selectedChat.userName }}</h3>
        </div>
        <div class="message-list" ref="messageBox">
          <div v-for="message in messages" :key="message.id"
               :class="{ 'my-message': message.sender === userId, 'other-message': message.sender !== userId }">
            <p>{{ message.content }}</p>
          </div>
        </div>
        <div class="message-input">
          <input :disabled="chats.length === 0" type="text" v-model="newMessage" placeholder="Type your message...">
          <button @click="sendMessage">Send</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>

export default {
  data() {
    return {
      chats: [],
      selectedChat: null,
      messages: [],
      newMessage: '',
      userId: '01' // Replace with actual user id
    };
  },
  computed: {
    randomGradient() {
      return `${this.getRandomColor()}`;
    }
  },
  watch: {
    messages: {
      handler() {
        this.$nextTick(() => {
          this.scrollToBottom(); // Scroll to bottom when messages state changes
        });
      },
      deep: true // Watch for changes in nested properties of messages array
    }
  },
  methods: {
    scrollToBottom() {
      this.$refs.messageBox.scrollTop = this.$refs.messageBox.scrollHeight;
    },

    getRandomColor(chatId) {
      const numericChatId = parseInt(chatId.replace(/[^\d]/g, ''), 10);
      const seed = numericChatId % 1000;
      const randomComponent = (value) => (value * seed) % 256;
      const red = randomComponent(2).toString(16).padStart(2, '0');
      const green = randomComponent(3).toString(16).padStart(2, '0');
      const blue = randomComponent(5).toString(16).padStart(2, '0');
      return `#${red}${green}${blue}`;
    },

    async fetchUserData(userId) {
      const profileResponse = await fetch(`http://localhost:8000/api/profiles/${userId}`);
      return await profileResponse.json();
    },

    async fetchChats(userId) {
      const response = await fetch(`http://localhost:8005/api/chats/user/${userId}`);
      let responseJson = await response.json();


      let chats = []
      for (let i = 0; i < responseJson.length; i++) {
        let otherUserId = responseJson[i].user1Id === userId ? responseJson[i].user2Id : responseJson[i].user1Id;
        let userData = await this.fetchUserData(otherUserId);

        chats.push({
          userName: userData.username,
          user1Id: responseJson[i].user1Id,
          user2Id: responseJson[i].user2Id,
          messages: responseJson[i].messages,
          id: responseJson[i].id,
          latestMessage: responseJson[i].messages[responseJson[i].messages.length-1],
        })
      }

      let chatId = this.$route.params.id;
      const singleChatResponse = await fetch(`http://localhost:8005/api/chats/${chatId}`);

      if(chatId && singleChatResponse.ok){
        let singleChatResponseJson = await singleChatResponse.json();

        let otherUserId = singleChatResponseJson.user1Id === userId ? singleChatResponseJson.user2Id : singleChatResponseJson.user1Id;
        let userData = await this.fetchUserData(otherUserId);

        this.selectedChat = {
          userName: userData.username,
          user1Id: singleChatResponseJson.user1Id,
          user2Id: singleChatResponseJson.user2Id,
          messages: singleChatResponseJson.messages,
          id: singleChatResponseJson.id,
          latestMessage: singleChatResponseJson.messages[singleChatResponseJson.messages?.length-1],
        }

        this.messages = singleChatResponseJson.messages;
      }else{
        this.selectedChat = chats[0];
        this.messages = chats[0]?.messages;
      }
      this.chats = chats.reverse();
    },

    async loadMessages(chatId) {
      let selectedChat = this.chats.find((chat) => chat.id === chatId);
      this.messages = selectedChat?.messages;
      this.selectedChat = selectedChat;
    },

    async sendMessage() {
      if (!this.newMessage.trim()) {
        return;
      }
      try {
        let newMessage = {
          sender: this.userId,
          receiver: this.selectedChat.user1Id === this.userId ? this.selectedChat.user2Id : this.selectedChat.user1Id,
          content: this.newMessage,
          createdAt:new Date().toISOString(),
        }
        const response = await fetch(`http://localhost:8005/api/chats/${this.selectedChat.id}/messages`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(newMessage)
        });
        if (response.ok) {
          this.newMessage = '';
          this.messages.push(newMessage);
        } else {
          console.error('Failed to send message:', response.statusText);
        }
      } catch (error) {
        console.error('Failed to send message:', error);
      }
    }
  },
  mounted() {
    // const userId = 'your_user_id_here';
    this.fetchChats(this.userId);
    this.scrollToBottom();
  }
};
</script>

<style scoped>

.root {
  background-color: #f5f7f6;
  padding: 60px 180px;
  display: flex;
}
.chat-container {
  display: flex;
  width:100%;
  height: 68vh;
  background: #ffffff;
  border-radius: 12px;
  border: 1px solid #e6e6e6;
}
.chat-sidebar {
  flex: 1;
  border-right: 1px solid #e6e6e6;
  overflow-y: auto;
}
.chat-sidebar ul{
  padding:0;
  margin:0;
}
.chat-item {
  display: flex;
  padding: 16px 32px;
  cursor: pointer;
  gap: 16px;
  transition: background-color 0.3s;
}
.chat-item:hover {
  background-color: #f5f7f6;
}
.avatar {
  min-width: 64px;
  min-height: 64px;
  max-width: 64px;
  max-height: 64px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.avatar > p {
  font-size: 32px;
  font-weight: bold;
  color: #ffffff;
}

.chat-info {
  margin: 6px 0;
  text-align: start;
}

.chat-info h3 {
  font-weight: bold;
  margin: 0 0 8px 0;
}
.chat-info p {
  color: #666;
  margin: 0;
}
.chat-messages {
  flex: 3;
  padding: 20px;
  display:flex;
  flex-direction: column;
}

.chat-header {
  text-align: center;
  padding-bottom: 10px;
  margin-bottom: 10px;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  gap: 16px;
}
.message-list {
  list-style: none;
  padding: 0;
  flex:1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
}

.my-message {
  background-color: #43abf1;
  border-radius: 10px;
  padding: 6px 10px;
  display: flex;
  align-self: flex-end;
  width: max-content;
  margin-right: 12px;
  margin-bottom: 5px;
}
.other-message {
  text-align: left;
  background-color: #f3607b;
  border-radius: 10px;
  margin-left: 12px;
  margin-bottom: 5px;
  padding: 6px 10px;
  display: flex;
  align-self: flex-start;
  width: max-content;
}
.my-message > p, .other-message > p {
  margin: 0;
  padding: 0;
  color: #ffffff;
  font-weight: 600;
}
.message-input {
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.message-input input {
  flex: 1;
  padding: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

.message-input button {
  margin-left: 10px;
  padding: 16px;
  background-color: #43abf1;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
}
</style>