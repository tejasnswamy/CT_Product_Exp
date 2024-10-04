package ct.product_exp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ct.product_exp.prd.ProdExpVariables
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var home = HashMap<String, Any>()
    private var product = HashMap<String, Any>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var isGridView = false
    private lateinit var btnToggle: Button
    private var clevertapDefaultInstance: CleverTapAPI? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            this
        )
        var productVariables = ProdExpVariables(clevertapDefaultInstance)

        recyclerView = findViewById(R.id.recyclerView)
        btnToggle = findViewById(R.id.btnToggle)
        home = clevertapDefaultInstance?.getVariableValue("ProdExp") as HashMap<String, Any>;
        product = home["product"] as HashMap<String, Any>



        val productList = getProductList()

        productAdapter = productList?.let { ProductAdapter(product,it, this, false) }!!
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
        btnToggle.visibility= View.GONE
        isGridView = home["list_view"] as Boolean
        Toast.makeText(applicationContext,isGridView.toString(),Toast.LENGTH_SHORT).show()

            if (isGridView) {
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            } else {
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
            productAdapter.setGridView(isGridView)
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
