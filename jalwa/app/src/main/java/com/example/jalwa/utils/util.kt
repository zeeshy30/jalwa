package com.vc.voco.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.crashlytics.FirebaseCrashlytics
//import com.helpshift.Core
//import com.helpshift.HelpshiftUser
//import com.helpshift.support.ApiConfig
//import com.vc.voco.core.listeners.DialogButtonClickListener
//import com.vc.voco.core.location.SeatedLocationManager
//import com.vc.voco.core.models.Restaurant
//import com.vc.voco.core.providers.AlertDialogProvider
//import com.vc.voco.core.session.PickerUtility
//import com.vc.voco.core.session.Session
//import com.vc.voco.features.search.models.RestaurantTimeSlot
import java.util.*


object JalwaUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboardFrom(
            context: Context,
            view: View
    ) {
        val imm: InputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun makeSpannableText(
            strings: ArrayList<String>,
            typeface: ArrayList<Int>, textSizes: ArrayList<Float>?
    ): SpannableStringBuilder {
        var string = ""
        for (str in strings) {
            string += str
        }
        val spannable = SpannableStringBuilder(string)
        var count = 0
        for (i in 0 until strings.count()) {
            spannable.setSpan(
                    StyleSpan(typeface[i]), count, count + strings[i].count(),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            textSizes?.let {
                spannable.setSpan(
                        RelativeSizeSpan(it[i]), count, count + strings[i].count(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            count += strings[i].count()
        }
        return spannable
    }

    fun getPixel(dp: Float, displayMetrics: DisplayMetrics): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                displayMetrics
        ).toInt()
    }

//    fun getHelpShiftUserData(userId: String): ApiConfig {
//        val user = HelpshiftUser.Builder(userId, Session.userEmail)
//                .setName(Session.userFirstName)// or Session.userFirstName or Constants.SPACE_STRING
//                .build()
//        Core.login(user)
//
//        val customIssueFields = HashMap<String, Array<String>>()
//        customIssueFields["User ID"] = arrayOf(userId)
//        customIssueFields["First Name"] = arrayOf(Session.userFirstName)
//        customIssueFields["Last Name"] = arrayOf(Session.userLastName)
//        customIssueFields["Phone Number"] = arrayOf(Session.userPhoneNumber)
//        customIssueFields["Payment Email"] = arrayOf(Session.userEmail)
//        customIssueFields["City"] = arrayOf("")
//        customIssueFields["VIP"] = arrayOf("")
//        customIssueFields["Twilio Number"] = arrayOf("")
//        customIssueFields["Signup Date"] = arrayOf("")
//        return ApiConfig.Builder()
//                .setCustomIssueFields(customIssueFields)
//                .build()
//    }

    fun openSharingIntent(context: Context, str: String) {
        val sharingIntent = Intent(Intent.ACTION_VIEW)
        sharingIntent.action = Intent.ACTION_SEND
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, str)
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

//    fun showMissingDeliveryAndPickupAlertDialog(context: Context) {
//        AlertDialogProvider.showAlertDialog(context,
//                object : DialogButtonClickListener {
//                    override fun onClick(alertDialog: AlertDialog) {
//                        alertDialog.dismiss()
//                    }
//                },
//                object : DialogButtonClickListener {
//                    override fun onClick(alertDialog: AlertDialog) {
//                        alertDialog.dismiss()
//                    }
//                })
//    }

    fun convertDpToPx(context: Context, dip: Float): Float {
        val r: Resources = context.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.displayMetrics
        )
    }

//    fun getLocationUnavailableReason(activity: Activity?): String {
//        activity?.let {
//            return if (SeatedLocationManager.LocationPermissionHelper.hasLocationPermission(activity)) {
//                if (SeatedLocationManager.lastRecentLocation() == null) {
//                    "undefined"
//                } else {
//                    ""
//                }
//            } else {
//                "permission"
//            }
//        }
//        return "unknown"
//    }

    fun returnValidatedUrl(url: String): String {
        try {
            var webPageUrl = Uri.parse(url)
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webPageUrl = Uri.parse("http://$url")
            }
            return webPageUrl.toString()
        } catch (e: Exception) {
//            FirebaseCrashlytics.getInstance()
//                    .recordException(Throwable("Invalid Url"))
        }
        return ""
    }

}