package com.example.projetoimpressao

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager

class USB(private val usbManager: UsbManager, private val context: Context) {
    fun dispositivos(): Map<String, UsbDevice> {
        return usbManager.deviceList.toMap()
    }
}