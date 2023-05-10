package com.mkt.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mkt.myapplication.adapter.CategoryAdapter
import com.mkt.myapplication.adapter.DrinksAdapter
import com.mkt.myapplication.dto.Category
import com.mkt.myapplication.dto.CocktailsCategoryDto
import com.mkt.myapplication.dto.Drink
import com.mkt.myapplication.network.ApiClientString
import com.mkt.myapplication.network.ApiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("MissingInflatedId")
class MainActivity : AppCompatActivity() {
    //declare variables
    lateinit var drinksAdapter: DrinksAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var rvCategory: RecyclerView
    lateinit var rvDrinks: RecyclerView
    var categoryListResponse: CocktailsCategoryDto? = null
    var categoryList: MutableList<Category>? = null
    var drinksList: MutableList<Drink> = ArrayList()
    lateinit var tvCategoryLabel: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init variables
        rvCategory = findViewById(R.id.rvCategory)
        rvDrinks = findViewById(R.id.rvDrinks)
        tvCategoryLabel = findViewById(R.id.tvCategoryLabel)
        fetchCategories()

    }

    private fun fetchCategories() {
        //todo showProgress
        val apiInterface: ApiInterface =
            ApiClientString.getClient().create(ApiInterface::class.java)
        val call: Call<String?>? = apiInterface.getCategoryList("list")
        call?.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                try {
                    Log.d("onResponse", response.body().toString())
                    val categoryItemList: MutableList<Category> = ArrayList()
                    if (response.isSuccessful) {
                        val jsonObj = JSONObject(response.body().toString())
                        val categoryArray = jsonObj.optJSONArray("drinks")
                        Log.d("onResponseObj", jsonObj.optJSONArray("drinks")[0].toString())
                        if (categoryArray != null) {
                            for (i in 0 until categoryArray.length()){
                                val category = categoryArray.optJSONObject(i)
                                categoryItemList.add(Category(strCategory = category.optString("strCategory")))
                                Log.d("onResponseArray", category.toString())
                            }
                        }
                        //initialise the recyclerView and adapter
                        categoryAdapter = CategoryAdapter(categoryItemList, this@MainActivity)
                        //populate adapter
                        rvCategory.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        rvCategory.adapter = categoryAdapter
                        categoryAdapter.setOnClickListener(object : CategoryAdapter.OnItemClickListener{
                            override fun onItemSelected(Category: Category?, position: Int) {
                                TODO("Not yet implemented")
                            }

                        })

                        //fetchPopularCocktails from Api
                        fetchPopularCocktails("Cocktail")
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Sorry something went wrong while processing your request",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.d("onResponseException", e.message.toString())
                }

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Sorry something went wrong while processing your request",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun fetchPopularCocktails(title: String) {

        //todo showProgress
        val apiInterface: ApiInterface =
            ApiClientString.getClient().create(ApiInterface::class.java)
        val call: Call<String?>? = apiInterface.getDrinksPerCategoryList(title)
        call?.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                try {
                    Log.d("onResponse", response.body().toString())
                    val categoryItemList: MutableList<Category> = ArrayList()
                    if (response.isSuccessful) {

                        val jsonObj = JSONObject(response.body().toString())
                        val drinksArray = jsonObj.optJSONArray("drinks")
                        Log.d("onResponseObj", jsonObj.optJSONArray("drinks")[0].toString())
                        if (drinksArray != null) {
                            for (i in 0 until 10){
                                val drink = drinksArray.optJSONObject(i)
                                drinksList.add(
                                    Drink(
                                        strDrink = drink.optString("strDrink"),
                                        strDrinkThumb = drink.optString("strDrinkThumb"),
                                        idDrink = drink.optString("idDrink")
                                    )
                                )
                                Log.d("onResponseArray", drink.toString())
                            }
                        }

                        //initialise the recyclerView and adapter
                        drinksAdapter = DrinksAdapter(drinksList, this@MainActivity)
                        //populate adapter
                        rvDrinks.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        rvDrinks.adapter = drinksAdapter

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Sorry something went wrong while processing your request",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.d("onResponseException", e.message.toString())
                }
//menu.strDrinkThumb
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Sorry something went wrong while processing your request",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }
}