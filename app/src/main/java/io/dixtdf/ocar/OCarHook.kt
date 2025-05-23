package io.dixtdf.ocar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class OCarHook:IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        XposedBridge.log("OCarHook Loaded package: " + lpparam!!.packageName)
        val activityClass = XposedHelpers.findClass(
            "com.oplus.ocar.connect.ui.ConnectActivity",
            lpparam.classLoader
        )
        XposedHelpers.findAndHookMethod(activityClass, "onCreate", Bundle::class.java, object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                XposedBridge.log("OCarHook onCreate: " + lpparam.packageName)
                XposedBridge.log("OCarHook ConnectActivity 被阻止显示")
                val activity = param.thisObject as Activity
                activity.finish()
                param.setResult(null)
            }
        })
    }
}
