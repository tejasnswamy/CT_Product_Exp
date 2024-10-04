package ct.product_exp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(
    private var product: HashMap<String, Any> = HashMap<String, Any>(),
    private val productList: List<Product>,
    private val context: Context,
    private var isGridView: Boolean
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun setGridView(isGridView: Boolean) {
        this.isGridView = isGridView
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = if (isGridView) {
            LayoutInflater.from(context).inflate(R.layout.item_grid_view, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false)
        }
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val productImageShow = product["product_image_show"] as Boolean
        val productNameShow = product["product_name_show"] as Boolean
        val offerShow = product["offer_show"] as Boolean
        val discShow = product["disc_show"] as Boolean
        var category = product["category"] as String

        val product = productList[position]
        if(!productImageShow) holder.productImage.visibility = View.GONE
        if(!productNameShow) holder.productTitle.visibility = View.GONE
        if(!offerShow) holder.productDiscount.visibility = View.GONE
        if(!discShow) holder.productDescription.visibility = View.GONE


        holder.productTitle.text = product.title
        holder.productDescription.text = product.description
        holder.productPrice.text = product.price
        holder.productDiscount.text = product.discount
        Glide.with(context)
            .load(product.imageResource)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.productImage)
        //holder.productImage.setImageResource(product.imageResource)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productTitle: TextView = itemView.findViewById(R.id.productTitle)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productDiscount: TextView = itemView.findViewById(R.id.productDiscount)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
    }
}
