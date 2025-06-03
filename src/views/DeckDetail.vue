<template>
    <div id="deck">
      <h2>Deck Details</h2>
      <div v-if="deck" id="deck-details">
        <form @submit.prevent="submitDeckEdits">
        <p><input v-model="deckDetailsForm.name" required="true" name="name" type="text" :placeholder="deck.name" /></p>
        <p><input v-model="deckDetailsForm.details" required="true"  name="details"  type="text" :placeholder="deck.description" /></p>
        <button id = "edit-button" class="submenu-button">Update</button><button id = "delete-button" class="submenu-button" @click="confirmDelete">Delete Deck</button>
        </form>

        <form class="card-search" @submit.prevent="submitSearch">
      <div><input v-model="searchText" form="card-search"  required="true"  name="searchText"  type="text" placeholder="Enter a search parameter"></input>
      </div>
      <div id="search">
        <div id="search-options">
          <div><input id="name-box" type="radio" name="searchType" value="name" v-model="searchType"><label for="name-box">By Name</label></div>
          <div><input id="cost-box" type="radio" name="searchType" value="cost" v-model="searchType"><label for="cost-box">By Cost</label></div>
          <div><input id="color-box" type="radio" name="searchType" value="color" v-model="searchType"><label for="color-box">By Color</label></div>
          <div><input id="type-box" type="radio" name="searchType" value="type" v-model="searchType"><label for="type-box">By Type</label></div>
          <div><input id="keyword-box" type="radio" name="searchType" value="keyword" v-model="searchType"><label for="keyword-box">By Keyword</label></div>
          <div><input id="power-box" type="radio" name="searchType" value="power" v-model="searchType"><label for="power-box">By Power</label></div>
          <div><input id="toughness-box" type="radio" name="searchType" value="toughness" v-model="searchType"><label for="toughness-box">By Toughness</label></div>
        </div>
        <div id="search-button"><button id = "search-button" class="submenu-button" type="submit">Search</button>
        </div>
      </div>
    </form>

        <div class="cardSearch">
            <div v-for="card in results" :key="card.id" class="card"><img :src="'https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=' + card.multiverseId + '&type=card'" />
            <button @click="addCard(card)" class="submenu-button">Add</button>
            </div>
         </div>

         <div id="deck-card-list"><h2>Cards in Deck</h2></div>
         <div class="cardSearch">
        <div v-for="card in deck.cards" :key="card.multiverseId"><img :src="'https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=' + card.multiverseId + '&type=card'" /><br>
            <button @click="addCard(card)" class="submenu-button">Add</button><button @click="deleteCard(card)" class="submenu-button">Delete</button>
        </div></div>
      </div>

      <div v-else>
        <form class="card-search" @submit.prevent="submitSearch">
      <div><input v-model="searchText" form="card-search"  required="true"  name="searchText"  type="text" placeholder="Enter a search parameter"></input>
      </div>
      <div id="search">
        <div id="search-options">
          <div><input id="name-box" type="radio" name="searchType" value="name" v-model="searchType"><label for="name-box">By Name</label></div>
          <div><input id="cost-box" type="radio" name="searchType" value="cost" v-model="searchType"><label for="cost-box">By Cost</label></div>
          <div><input id="color-box" type="radio" name="searchType" value="color" v-model="searchType"><label for="color-box">By Color</label></div>
          <div><input id="type-box" type="radio" name="searchType" value="type" v-model="searchType"><label for="type-box">By Type</label></div>
          <div><input id="keyword-box" type="radio" name="searchType" value="keyword" v-model="searchType"><label for="keyword-box">By Keyword</label></div>
          <div><input id="power-box" type="radio" name="searchType" value="power" v-model="searchType"><label for="power-box">By Power</label></div>
          <div><input id="toughness-box" type="radio" name="searchType" value="toughness" v-model="searchType"><label for="toughness-box">By Toughness</label></div>
        </div>
        <div id="search-button"><button id = "search-button" class="submenu-button" type="submit">Search</button>
        </div>
      </div>
    </form>
    <div class="cardSearch">
            <div v-for="card in results" :key="card.id" class="card"><img :src="'https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=' + card.multiverseId + '&type=card'" />
            <button @click="addCard(card)" class="submenu-button">Add</button>
            </div>
         </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        deck: null,
        searchText: '',
        searchType: '',
        results: [],
        deckDetailsForm: {
            name: '',
            details: '',
            
      }
      };
    },
    async created() {
      const user = this.$route.params.username;
      const id = this.$route.params.id;
      const token = localStorage.getItem('token');
  
      try {
        const response = await axios.get(`http://localhost:8080/users/${user}/decks/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        this.deck = response.data;
      } catch (err) {
        console.error('Failed to load deck:', err);
      }
    },
    methods: {
        submitSearch() {
        const numericFields = ["cost", "power", "toughness"];
        console.log("Search Text:", this.searchText);
        console.log("Search Type:", this.searchType);
      if(!this.searchType) {
        alert("Please select a search type.");
        return;
      }
        if (numericFields.includes(this.searchType) && isNaN(this.searchText)) {
        alert(`Please enter a valid number for search by ${this.searchType}.`);
         return;
  }

      let url = "/cards/search?";
        if (this.searchType === "name") {
          url += `name=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "type") {
          url += `type=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "color") {
          url += `color=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "keyword") {
          url += `keyword=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "cost") {
          url += `cost=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "power") {
          url += `power=${encodeURIComponent(this.searchText)}`;
        } else if (this.searchType === "toughness") {
          url += `toughness=${encodeURIComponent(this.searchText)}`;
        };

        axios.get(url)
        .then(response => {
          this.results = response.data;
        })
        .catch(error => {
          console.error("Search failed:", error);
        })
    },
        async submitDeckEdits() {
    const user = this.$route.params.username;
    const id = this.$route.params.id;
    const token = localStorage.getItem('token');

    try {
      const response = await axios.put(
        `http://localhost:8080/users/${user}/decks/${id}`,
        {
          name: this.deckDetailsForm.name,
          description: this.deckDetailsForm.details
        },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      this.deck = response.data;
      console.log('Deck updated successfully');
    } catch (err) {
      console.error('Error updating deck:', err);
    }
  },
    async confirmDelete() {
    if (confirm("Are you sure you want to delete this deck? This action cannot be undone.")) {
      const user = this.$route.params.username;
      const id = this.$route.params.id;
      const token = localStorage.getItem('token');

      try {
        await axios.delete(`http://localhost:8080/users/${user}/decks/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        alert('Deck deleted successfully.');
        this.$router.push(`/users/${user}/decks`);
      } catch (err) {
        console.error('Failed to delete deck:', err);
        alert('Failed to delete deck.');
      }
    }
  },
  async refreshDeck() {
    const user = this.$route.params.username;
    const id = this.$route.params.id;
    const token = localStorage.getItem('token');

    try {
      const response = await axios.get(`http://localhost:8080/users/${user}/decks/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      this.deck = response.data;
    } catch (error) {
      console.error("Error fetching deck:", error);
    }
  },
  async addCard(card) {
    const user = this.$route.params.username;
    const id = this.$route.params.id;
    const token = localStorage.getItem('token');

    try {
      await axios.post(`http://localhost:8080/users/${user}/decks/${id}`, {
        multiverseId: card.multiverseId, cardName: card.cardName }, {
            headers: {
                Authorization: `Bearer ${token}`
          }
        });
        await this.refreshDeck();
    } catch (err) {
      console.error('Error adding card:', err);
    }
  },
  async deleteCard(card) {
    const user = this.$route.params.username;
    const id = this.$route.params.id;
    const token = localStorage.getItem('token');
    const cardId = card.multiverseId;
    
    try {
      await axios.delete(`http://localhost:8080/users/${user}/decks/${id}/${cardId}`, {
            headers: {
                Authorization: `Bearer ${token}`
          }
        });
        await this.refreshDeck();
    } catch (err) {
      console.error('Error deleting card:', err);
    }
  }
  }
};
  </script>
<style>

div img{
  height: 270px;
}
.cardSearch {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: flex-start;
}

.card {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.card-search {
  padding: 30px;
  width: 900px;
}
#search-options{
  display: flex;
  flex-direction: row;
  height:50px;
  gap:20px;
}

.card-search input {
border: 1px solid #21521c;
background-color: black;
width: 400px;
}

input, textarea {
  color: #2ce019;
  background-color: black;
  border: 1px solid #2ce019;
  padding: 8px;
}

input:focus, textarea:focus {
  outline: none;
  background-color: black;
  border: 2px solid #21521c;
  box-shadow: 0 0 20px #00ffff;
}

.card-search input::placeholder{
background-color: black;
color: #2ce019;
padding: 2px;
}

.card-search input[type="radio"]{
  appearance: none;
  background-color: black;
  border: 2px solid #2ce019;
  width: 15px;
  height: 15px;
  border-radius: 50%;
  outline: none;
  cursor: pointer;
  position: relative;
}

.card-search input[type="radio"]:checked::before {
  content: "";
  position: absolute;
  top: 1px;
  left: 1px;
  width: 14px;
  height: 14px;
  background-color: #266868;
  border-radius: 50%;
}
#deck-card-list{
  padding: 10px;
  text-align: center;
}
</style>