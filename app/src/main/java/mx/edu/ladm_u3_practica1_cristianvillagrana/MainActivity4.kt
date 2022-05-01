package mx.edu.ladm_u3_practica1_cristianvillagrana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.ActivityMain4Binding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Asignacion
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Inventario
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.slideshow.SlideshowFragment
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.slideshow.SlideshowViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity4 : AppCompatActivity() {
    lateinit var binding : ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var fecha = ""
        var barcode = ""
        val calendar = Calendar.getInstance()
        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            binding.calendar.date = calendar.timeInMillis
            val dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT)
            fecha = dateFormatter.format(calendar.time)
        }

        barcode = this.intent.extras!!.getString("codigobarrasa")!!

        binding.btnasignar.setOnClickListener {
            var venta = Asignacion(this)
            venta.nomempleado = binding.txtempleado.text.toString()
            venta.areatrabajo = binding.txtarea.text.toString()
            if(fecha.equals("")){
                val sdf = SimpleDateFormat("dd-M-yyyy")
                val fechasistema = sdf.format(Date())
                venta.fecha  = fechasistema
            }else{
                venta.fecha = fecha
            }
            venta.codigofk = barcode

            val resultado = venta.insertar()
            if(resultado){
                Toast.makeText(this,"SE INSERTO CON EXITO", Toast.LENGTH_LONG).show()
                limpiar()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }//btnasignar

        binding.btnvolver.setOnClickListener { finish() }



    }//onCreate

    fun limpiar(){
        binding.txtempleado.setText("")
        binding.txtarea.setText("")
    }
}