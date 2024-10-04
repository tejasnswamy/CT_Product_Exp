package ct.product_exp

import android.app.Application
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI

class ProdApp : Application() {
    private var clevertapDefaultInstance: CleverTapAPI? = null

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG)
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            this
        )
        clevertapDefaultInstance?.enableDeviceNetworkInfoReporting(true)


    }
}