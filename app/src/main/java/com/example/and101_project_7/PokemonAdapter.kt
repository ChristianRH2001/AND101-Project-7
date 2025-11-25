package com.example.and101_project_7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.pokemonImageView)
        val nameText: TextView = itemView.findViewById(R.id.pokemonNameText)
        val numberText: TextView = itemView.findViewById(R.id.pokemonNumberText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.nameText.text = pokemon.name
        holder.numberText.text = "Number: ${pokemon.number}"
        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = pokemonList.size
}
