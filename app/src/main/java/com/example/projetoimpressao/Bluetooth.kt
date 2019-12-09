package com.example.projetoimpressao

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class Bluetooth : BroadcastReceiver() {

    var lista = ArrayList<BluetoothDevice>()
    var dispositivo: BluetoothAdapter? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action!!.compareTo(BluetoothDevice.ACTION_FOUND) == 0) {
            val device =
                intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if (lista.contains(device)) {
                return
            }
            lista.add(device)
        } else if (action.compareTo(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) == 0) {
            context.unregisterReceiver(this)
        }
    }
}