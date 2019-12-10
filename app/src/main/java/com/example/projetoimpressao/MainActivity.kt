package com.example.projetoimpressao

import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.felhr.usbserial.UsbSerialDevice
import com.felhr.usbserial.UsbSerialInterface
import com.github.anastaciocintra.escpos.EscPos
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val bluetooth = getBondedDevices()
//        for (device: BluetoothDevice in bluetooth!!.lista) {
//            if (device.name == "TM-Enterplug") {
//                val bluetoothDevice = BluetoothDeviceSocketConnexion(device)
//                bluetoothDevice.connect()
//                val printerOutputStream = bluetoothDevice.bluetoothSocket!!.outputStream
//                val escpos = EscPos(printerOutputStream)
//                escpos.writeLF(
//                    "aaa!"
//                )
//                escpos.feed(8)
//                escpos.cut(EscPos.CutMode.FULL)
//            }
//
//        }
        usb.setOnClickListener {
            val manager = this.getSystemService(Context.USB_SERVICE) as UsbManager
            var connection: UsbDeviceConnection? = null
            Toast.makeText(this@MainActivity, "SEU PAI TEM GONORREIA", Toast.LENGTH_SHORT).show()
            manager.deviceList.forEach {
                val usbPermissionName = "com.android.example.USB_PERMISSION";
                val permissionPendingIntent = PendingIntent.getBroadcast(this, 0, Intent(usbPermissionName), 0)
                manager.requestPermission(it.value, permissionPendingIntent);
                registerReceiver(object: BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        if (usbPermissionName.equals(intent!!.action)) {
                            val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                            if(intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                                synchronized (this) {
                                    connection = manager.openDevice(device)
                                    if(connection != null) {
                                        val usbSerialDevice =
                                            UsbSerialDevice.createUsbSerialDevice(
                                                device,
                                                connection
                                            )
                                        usbSerialDevice.syncOpen()
                                        usbSerialDevice.setBaudRate(9600)
                                        usbSerialDevice.setDataBits(UsbSerialInterface.DATA_BITS_8)
                                        usbSerialDevice.setParity(UsbSerialInterface.PARITY_ODD)
                                        usbSerialDevice.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
                                        val escpos = EscPos(usbSerialDevice.outputStream)
                                        Toast.makeText(this@MainActivity, "SEU PAI TEM GONORREIA", Toast.LENGTH_SHORT).show()
                                        escpos.writeLF(
                                            "Sds dela mano"
                                        )
                                        escpos.feed(1)
                                            escpos.cut(EscPos.CutMode.PART)
                                    }else{
                                        Toast.makeText(this@MainActivity, "Não foi possível conectar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }, IntentFilter(usbPermissionName))
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


