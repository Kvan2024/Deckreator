<template>
  <div>
    <form id="card-search" @submit.prevent="submitSearch">
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
    <div v-if="results.length > 0">
  <h2>Search Results:</h2>
    <p>
      <img v-for="card in results" :key="card.id" :src="'https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=' + card.multiverseId + '&type=card'" />
    </p>
</div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CardSearchView',
  data() {
    return {
      searchText: '',
      searchType: '',
      results: []
    };
  },
  methods: {
    submitSearch() {
        console.log("Search Text:", this.searchText);
        console.log("Search Type:", this.searchType);
      if(!this.searchType) {
        alert("Please select a search type.");
        return;
      }

      let url = "https://deckreator.onrender.com/cards/search?";
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
    }
  }
}
</script>

<style scoped>
#card-search{
}
#card-search input[type="text"]{
  border: 1px solid #21521c;
  width: 700px;
}
#card-search input[type="radio"]{
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

#card-search input[type="radio"]:checked::before {
  content: "";
  position: absolute;
  top: 1px;
  left: 1px;
  width: 14px;
  height: 14px;
  background-color: #266868;
  border-radius: 50%;
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

#card-search input::placeholder{
background-color: black;
color: #2c7575;
padding: 2px;
}

#search {
  display: grid;
  grid-template-columns: 80% 1fr;
  max-width: 700px;
  gap: 5px;
  padding: 10px;
}

#search-options {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  max-width: 500px;
  gap: 5px;
}

#search-button {
  justify-self: end;
  align-self: start;
}

p img{
  height: 250px;
}
</style>