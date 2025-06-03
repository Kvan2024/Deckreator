<template>
  <form @submit.prevent="submitDeck" class="new-deck-form">
    <div>
      <label for="deck-name">Deck Name: </label>
      <input v-model="deckForm.name" id="deck-name" required type="text" />
    </div>
    <br><br>
    <div>
      <label for="deck-description">Description: </label>
      <input v-model="deckForm.description" id="deck-description" required type="text"/>
    </div>
<br><br>
    <button type="submit" class="submenu-button">Create Deck</button>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      deckForm: {
        name: '',
        description: ''
      }
    };
  },
  methods: {
    async submitDeck() {
      const user = this.$store.state.username;
      const token = localStorage.getItem('token');

      try {
        const response = await axios.post(
          `http://localhost:8080/users/${user}/decks`,
          {
            name: this.deckForm.name,
            description: this.deckForm.description
          },
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );
        this.$router.push(`/users/${user}/decks/`);
      } catch (err) {
        console.error('Failed to create deck:', err);
        alert('Deck creation failed. Check inputs and try again.');
      }
    }
  }
};
</script>
<style>
.new-deck-form {
  gap:10px;
}
.new-deck-form input[type="text"]{
  border: 1px solid #21521c;
  width: 250px;
}

input, textarea {
  color: #2ce019;
  background-color: black;
  border: 1px solid #21521c;
  padding: 8px;
}

input:focus, textarea:focus {
  outline: none;
  border: 2px solid #21521c;
  box-shadow: 0 0 20px #00ffff;
}

.new-deck-form input::placeholder{
background-color: black;
color: #2c7575;
padding: 2px;
}

</style>