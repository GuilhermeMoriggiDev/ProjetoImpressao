package com.example.projetoimpressao

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.hardware.usb.UsbDevice
import java.io.IOException

class BluetoothDeviceSocketConnexion
/**
 * Create un instance of BluetoothDeviceSocketConnexion.
 *
 * @param device an instance of android.bluetooth.BluetoothDevice
 */(
    /**
     * Get the instance android.bluetooth.BluetoothDevice connected.
     *
     * @return an instance of android.bluetooth.BluetoothDevice
     */
    var device: BluetoothDevice
) {
    var bluetoothSocket: BluetoothSocket? = null

    /**
     * Check if the bluetooth device is connected by socket.
     *
     * @return true if is connected
     */
    val isConnected: Boolean
        get() = bluetoothSocket != null

    /**
     * Start socket connexion with the bluetooth device.
     *
     * @return return true if success
     */
    fun connect(): Boolean {
        try {
            bluetoothSocket =
                device.createRfcommSocketToServiceRecord(device.uuids[0].uuid)
            bluetoothSocket!!.connect()
            return true
        } catch (ex: IOException) {
            try {
                bluetoothSocket!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            bluetoothSocket = null
        }
        return false
    }

    /**
     * Close the socket connexion with the bluetooth device.
     *
     * @return return true if success
     */
    fun disconnect(): Boolean {
        if (!isConnected) {
            return true
        }
        try {
            bluetoothSocket!!.close()
            bluetoothSocket = null
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

}