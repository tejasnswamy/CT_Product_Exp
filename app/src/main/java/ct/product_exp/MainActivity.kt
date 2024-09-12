package ct.product_exp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var isGridView = false
    private lateinit var btnToggle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        btnToggle = findViewById(R.id.btnToggle)


        val productList = getProductList()

        productAdapter = productList?.let { ProductAdapter(it, this, false) }!!
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        btnToggle.setOnClickListener {
            isGridView = !isGridView
            if (isGridView) {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
                btnToggle.text = "Switch to List View"
            } else {
                recyclerView.layoutManager = LinearLayoutManager(this)
                btnToggle.text = "Switch to Grid View"
            }
            productAdapter.setGridView(isGridView)
        }
    }

    private fun getProductList(): List<Product>? {
        var products: List<Product>? = null
        val jsonFileString = getJsonDataFromAsset("products.json")
        if (jsonFileString != null) {
            // Parse the JSON to a list of Product objects using Gson
            val gson = Gson()
            val listProductType = object : TypeToken<List<Product>>() {}.type
             products = gson.fromJson(jsonFileString, listProductType)

            // You now have the list of Product objects, you can use this data in your RecyclerView
            if (products != null) {
                for (product in products) {
                    Log.d("Product Data", product.toString())
                }
            }
        }
        return products
    }

    // Helper function to read the JSON file from the assets folder
    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
