package mx.edu.ladm_u3_practica1_cristianvillagrana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.ActivityMain2Binding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Inventario
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        binding.calendarfecha.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            binding.calendarfecha.date = calendar.timeInMillis
            val dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT)
            binding.txtfecha.setText(dateFormatter.format(calendar.time))
        }

        binding.btnagregar.setOnClickListener {
            var prod = Inventario(this)
            prod.codigobarras = binding.barcode.text.toString()
            prod.tipoequipo = binding.tipoequipo.text.toString()
            prod.caracteristicas = binding.specs.text.toString()
            if(binding.txtfecha.text.toString().equals("FECHA DE INGRESO")){
                val sdf = SimpleDateFormat("dd-M-yyyy")
                val fechasistema = sdf.format(Date())
                prod.fechacompra = fechasistema
            }else{
                prod.fechacompra = binding.txtfecha.text.toString()
            }


            val resultado = prod.insertar()
            if(resultado){
                Toast.makeText(this,"SE INSERTO CON EXITO", Toast.LENGTH_LONG).show()
                borrar()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }//btnAgregar

        binding.btnvolver.setOnClickListener {finish()}
    }//onCreate

    fun borrar(){
        binding.barcode.setText("")
        binding.tipoequipo.setText("")
        binding.specs.setText("")
    }
}