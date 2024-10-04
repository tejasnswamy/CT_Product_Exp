package ct.product_exp.prd;
import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;
public class ProdExpVariables {

    HashMap<String, Object> home = new HashMap<>();
    HashMap<String, Object> user = new HashMap<>();
    HashMap<String, Object> product = new HashMap<>();

    public ProdExpVariables(CleverTapAPI cleverTapAPI) {

        user.put("userType", "default");
        user.put("plan", "default");

        product.put("product_image_show",true);
        product.put("product_name_show",true);
        product.put("offer_show",true);
        product.put("disc_show",true);
        product.put("category","default");


        home.put("list_view", true);
        home.put("user", user);
        home.put("product", product);


        cleverTapAPI.defineVariable("ProdExp", home);
        cleverTapAPI.syncVariables();
    }
}
