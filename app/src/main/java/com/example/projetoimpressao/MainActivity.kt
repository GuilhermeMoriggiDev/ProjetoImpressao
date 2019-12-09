package com.example.projetoimpressao

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.github.anastaciocintra.escpos.EscPos


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bluetooth = getBondedDevices()
        for (device: BluetoothDevice in bluetooth!!.lista) {
            if (device.name == "TM-Enterplug") {
                val bluetoothDevice = BluetoothDeviceSocketConnexion(device)
                bluetoothDevice.connect()
                val printerOutputStream = bluetoothDevice.bluetoothSocket!!.outputStream
                val escpos = EscPos(printerOutputStream)
                escpos.writeLF(
                    "aaa!"
                )
                escpos.feed(8)
                escpos.cut(EscPos.CutMode.FULL)
            }

        }
    }

    fun getBondedDevices(): Bluetooth? {
        val bluetooth = Bluetooth()
        // Pega o dispositivo
        bluetooth.dispositivo = BluetoothAdapter.getDefaultAdapter()
        if (!bluetooth.dispositivo!!.isEnabled) {
            bluetooth.dispositivo!!.enable()
        }
        // Pega a lista de dispositivos pareados
        val pairedDevices = bluetooth.dispositivo!!.getBondedDevices()
        // Adiciono na lista e depois retorno a mesma.
        if (pairedDevices.size > 0) {
            for (device: BluetoothDevice in pairedDevices) {
                bluetooth.lista.add(device)
            }
        }
        return bluetooth
    }

}


