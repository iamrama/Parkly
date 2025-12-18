package com.rk.parkly.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkUtil {
    companion object {
        fun internetConnection(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // This is the fully corrected logic that avoids the unreliable 'activeNetwork' property directly.
            val networks = connectivityManager.allNetworks
            for (network in networks) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    // Check for specific transport types if needed, but having internet is key.
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true // Found a network with internet access
                    }
                }
            }
            return false // No network with internet access found
        }
    }
}