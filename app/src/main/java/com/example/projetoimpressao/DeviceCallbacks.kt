package com.example.projetoimpressao

interface DeviceCallbacks {
    fun onConnected()
    fun onFailure()
    fun onDisconnected()
}