package mx.edu.ittepic.ladm_u4_t2_trujilloorozco

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.telecom.Call
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val siLecturaLlamadas =18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_CALL_LOG),siLecturaLlamadas)
        }

        button.setOnClickListener {
            obtenerDatosLLamadas()
        }
    }

    private fun obtenerDatosLLamadas() {
        var resultado =""
        var cursorllamadas = contentResolver.query(
                Uri.parse("content://call_log/calls"),
                null,null,null,null
        )
        if(cursorllamadas!!.moveToFirst()){
            do {
                var nombre = cursorllamadas.getString(cursorllamadas.getColumnIndex(CallLog.Calls.CACHED_NAME))
                var numero = cursorllamadas.getString(cursorllamadas.getColumnIndex(CallLog.Calls.NUMBER))
                var tipo = cursorllamadas.getString(cursorllamadas.getColumnIndex(CallLog.Calls.TYPE))
                var fecha = cursorllamadas.getString(cursorllamadas.getColumnIndex(CallLog.Calls.DATE))
                var duracion = cursorllamadas.getString(cursorllamadas.getColumnIndex(CallLog.Calls.DURATION))

                resultado += "NOMBRE: "+nombre+"\nNUMERO: "+numero + "\nTIPO: "+tipo+"\nFECHA :\n"+fecha+"\nDURACION :"+duracion+"Seg"+"\n-----------\n"
            }while (cursorllamadas.moveToNext())
        }
        textView.setText(resultado)
    }










    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==siLecturaLlamadas){
            setTitle("PERMISO OTORGADO")
        }

    }


}
