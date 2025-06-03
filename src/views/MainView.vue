<template>
    <div id="top-cards">
        <h1>Top Cards</h1>
    </div>
  <div id="cards-layout">
       <div id="upper-line">
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16451&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16450&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16449&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16448&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16447&type=card" /></div></div>
        <div id="lower-line">
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16446&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16445&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16444&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16443&type=card" /></div>
        <div class="cards"><img src="https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16442&type=card" /></div></div>
    
    </div>
</template>

<script>
</script>

<style scoped>
#top-cards {
  background-color: black;
  font-family: 'Amarante', sans-serif;
  color: #2ce019;
  text-align: center;
  font-size: 32px;
}

#cards-layout {
    display:grid;
    grid-template-columns: auto;
    grid-template-rows: auto auto;
    grid-template-areas: 
    "upper-line"
    "lower-line";
    gap:20px;
    box-sizing: border-box;
    width: 50vw;
    height: 40vh;
    align-content: start;

}
#upper-line {
    grid-area: "upper-line";
    display: flex;
    flex-direction: row;
    gap:25px;
}
#lower-line {
    grid-area: "lower-line";
    display: flex;
    flex-direction: row;
    gap:25px;
}

.cards img{
    width:150px;
    height:225px;
}
</style>
