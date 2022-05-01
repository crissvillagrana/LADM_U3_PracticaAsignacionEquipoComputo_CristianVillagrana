package mx.edu.ladm_u3_practica1_cristianvillagrana

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.ActivityMain5Binding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Asignacion

class MainActivity5 : AppCompatActivity() {
    lateinit var binding : ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id = ""
        binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)


        id = this.intent.extras!!.getString("idasignacion")!!
        val a = Asignacion(this).buscarAsignacion(id)

        binding.txtempleado.setText(a.nomempleado)
        binding.txtarea.setText(a.areatrabajo)

        binding.btnback.setOnClickListener { finish() }

        binding.btnguardar.setOnClickListener {
            var asig = Asignacion(this)
            asig.idasignacion = id
            asig.nomempleado = binding.txtempleado.text.toString()
            asig.areatrabajo = binding.txtarea.text.toString()

            val respuesta = asig.actualizar()
            if(respuesta){
                Toast.makeText(this,"Datos actualizados", Toast.LENGTH_LONG).show()
                limpiar()
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage("No se pudo actualizar").show()
            }
        }



    }//onCreate

    fun limpiar(){
        binding.txtempleado.setText("")
        binding.txtarea.setText("")
    }
}