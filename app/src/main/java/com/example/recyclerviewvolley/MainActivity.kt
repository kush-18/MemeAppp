package com.example.recyclerviewvolley

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.recyclerviewvolley.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url = "https://meme-api.com/gimme"
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getMemeData()

        binding.btnNewMeme.setOnClickListener{
            getMemeData()
        }
    }
    fun getMemeData()
    {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while the meme is loading")

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,url,
            {
                    response ->

                var responseObject = JSONObject(response)

                binding.memeTitle.text = responseObject.getString("title")
                binding.memeAuthor.text = responseObject.getString("author")
    //                binding.memeimage.setImageResource(responseObject.getInt("image"))
                Glide.with(this).load(responseObject.get("url")).into(binding.memeimage)
                progressDialog.dismiss()
            },
            { error->
                Toast.makeText(this@MainActivity,"${error.localizedMessage}",Toast.LENGTH_LONG)
                    .show()
                progressDialog.dismiss()

            })
        queue.add(stringRequest)
    }


}