package mx.edu.ladm_u3_practica1_cristianvillagrana

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.ActivityMain3Binding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Inventario
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity3 : AppCompatActivity() {
    lateinit var binding : ActivityMain3Binding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var barcode = ""
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        barcode = this.intent.extras!!.getString("codigobarras")!!
        val prod = Inventario(this).buscarProducto(barcode)

        binding.tipoequipo.setText(prod.tipoequipo)
        binding.specs.setText(prod.caracteristicas)

        binding.btnactualizar.setOnClickListener {
            var inve = Inventario(this)
            inve.codigobarras = barcode
            inve.tipoequipo = binding.tipoequipo.text.toString()
            inve.caracteristicas = binding.specs.text.toString()

            val respuesta = inve.actualizar()
            if(respuesta){
                Toast.makeText(this,"Datos actualizados",Toast.LENGTH_LONG).show()
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage("No se pudo actualizar").show()
            }
        }

        binding.btnvolver.setOnClickListener { finish() }

    }
}