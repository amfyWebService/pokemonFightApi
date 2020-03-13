package com.ynov.pokemon.model

data class Trainer(val name : String, var currentPokemon : Pokemon?, val backPack : BackPack) {
    init {
        if (currentPokemon == null) {
            currentPokemon = backPack.pokemons.random()
        }

    }

    fun useItem(item : Item, pokemon : Pokemon): Pokemon{
        if(!this.backPack.items.contains(item)){
            throw IllegalStateException("You can't select an item that doesn't exist in your backpack")
        }
        if(!this.backPack.pokemons.contains(pokemon)){
            throw IllegalStateException("You can't apply an item on a pokemon that doesn't exist in your backpack")
        }
        this.backPack.items.remove(item)

        return item.apply(pokemon)
    }

    fun pickUpPokemon(pokemon : Pokemon){
        if(!this.backPack.pokemons.contains(pokemon)){
            throw IllegalStateException("You can't select a pokemon that doesn't exist in your backpack")
        }
        if(pokemon.isKo()){
            throw IllegalStateException("You can't select a pokemon that is ok")
        }
        this.currentPokemon = pokemon
    }
}