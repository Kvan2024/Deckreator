<template>
  <div class="deck-page">
    <h2>{{ username }}'s Decks</h2>

    <div v-if="decks.length">
      <router-link v-for="deck in decks" :key="deck.id":to="`/users/${username}/decks/${deck.id}`"><p>{{ deck.name }}</p></router-link>
      
    </div>
    <div v-else>
      <p>No decks found for this user.</p>
    </div>

    <div v-if="error" class="error">{{ error }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'DeckView',
  data() {
    return {
      username: '',
      decks: [],
      error: ''
    };
  },
  async created() {
    this.username = this.$route.params.username;

    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`https://deckreator.onrender.com/users/${this.username}/decks`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      this.decks = response.data;
    } catch (err) {
      if (err.response?.status === 403) {
        this.error = 'You are not allowed to view this user’s decks.';
      } else if (err.response?.status === 404) {
        this.error = 'No decks found.';
      } else {
        this.error = 'Failed to load decks.';
      }
      console.error(err);
    }
  }
};
</script>

<style scoped>
#deck-page {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-content: center;
  height: 75vh;
  width: 75vw;
  gap: 50px;
}
#title {
  background-color: black;
  font-family: 'Amarante', sans-serif;
  color: #2ce019;
  text-align: center;
  font-size: 32px;
  align-items: start;
  margin: 0px;
}
#deck-list {
  display: grid;
  grid-template-columns: auto auto auto;
  grid-template-rows: auto auto;
  gap: 50px;
}

.deck-profile {
  display: grid;
  grid-template-columns: auto auto;
  grid-template-rows: auto auto auto;
  background-color: black;
  font-family: 'Amarante', sans-serif;
  color: #2ce019;
  text-align: start;
  font-size: 16px;
  height: 250px;
  width: 200px;
}
</style>