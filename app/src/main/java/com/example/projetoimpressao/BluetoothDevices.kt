package com.example.projetoimpressao

import android.bluetooth.BluetoothAdapter

class BluetoothDevices {
    protected var bluetoothAdapter: BluetoothAdapter?
    /**
     * Get a list of bluetooth devices available.
     * @return Return an array of BluetoothDeviceSocketConnexion instance
     */
    val list: Array<BluetoothDeviceSocketConnexion?>?
        get() {
            if (bluetoothAdapter == null) {
                return null
            }
            if (!bluetoothAdapter!!.isEnabled) {
                return null
            }
            val bluetoothDevicesList =
                bluetoothAdapter!!.bondedDevices
            val bluetoothDevices =
                arrayOfNulls<BluetoothDeviceSocketConnexion>(bluetoothDevicesList.size)
            if (bluetoothDevicesList.size > 0) {
                var i = 0
                for (device in bluetoothDevicesList) {
                    bluetoothDevices[i++] = BluetoothDeviceSocketConnexion(device)
                }
            }
            return bluetoothDevices
        }

    /**
     * Create a new instance of BluetoothDevices
     */
    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }
}