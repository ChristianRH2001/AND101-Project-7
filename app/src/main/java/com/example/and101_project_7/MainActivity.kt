package com.example.and101_project_7

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private val pokemonList = mutableListOf<Pokemon>()
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.pokemonRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = adapter

        // Fetch initial 20 Pokémon
        fetchRandomPokemon(20)

        // Refresh button handler
        findViewById<Button>(R.id.refreshButton).setOnClickListener {
            pokemonList.clear()
            adapter.notifyDataSetChanged()
            fetchRandomPokemon(20)
        }
    }

    private fun fetchRandomPokemon(count: Int) {
        val client = AsyncHttpClient()

        repeat(count) {
            val num = (1..1025).random()
            client["https://pokeapi.co/api/v2/pokemon/$num", object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    val sprites = json.jsonObject.getJSONObject("sprites")
                    val imageUrl = sprites.getString("front_default")
                    val name = json.jsonObject.getString("name")
                    val id = json.jsonObject.getInt("id")

                    val formattedName = name.split("-", "_")
                        .joinToString(" ") { it.replaceFirstChar(Char::uppercase) }

                    // Add the Pokémon to the list and refresh the RecyclerView
                    pokemonList.add(Pokemon(formattedName, id, imageUrl))
                    adapter.notifyItemInserted(pokemonList.size - 1)
                }

                override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {
                    Log.e("Pokemon Error", "Failed to fetch Pokémon: $errorResponse")
                }
            }]
        }
    }
}