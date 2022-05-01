package mx.edu.ladm_u3_practica1_cristianvillagrana.ui.slideshow

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity3
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity4
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity5
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.FragmentSlideshowBinding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Asignacion
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Inventario

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    var listaIDs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Aquí lo chido--------------------------------------------------------------------------------
        Toast.makeText(requireContext(),"Para asignar un equipo, vaya al inventario y seleccione el equipo. Después seleccione la opción Asignar",
        Toast.LENGTH_LONG)
        val spinner: Spinner = binding.spinnerA
        ArrayAdapter.createFromResource(
            requireContext(),
            mx.edu.ladm_u3_practica1_cristianvillagrana.R.array.asignacionspinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        binding.btnbuscar.setOnClickListener {
            mostrar()
        }

        binding.listaAsignacion.setOnItemClickListener { adapterView, view, index, l ->
            val idAsig = listaIDs[index]
            val asig = Asignacion(requireContext()).buscarAsignacion(idAsig)

            AlertDialog.Builder(requireContext())
                .setTitle("Opciones")
                .setMessage("Ha seleccionado la asignación de: " +
                        "\n ${asig.nomempleado} ${asig.areatrabajo} " +
                        "\n Id de Asignación: ${asig.idasignacion}" +
                        "\n Equipo: ${asig.codigofk}" +
                        "\n Asignado el: ${asig.fecha}")
                .setNegativeButton("Eliminar"){d,i->
                    asig.eliminar()
                    mostrar()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var ventana = Intent(requireContext(),MainActivity5::class.java)
                    ventana.putExtra("idasignacion",idAsig)
                    startActivity(ventana)
                }
                .setNeutralButton("Cerrar"){d,i->
                }
                .show()
        }
        //End------------------------------------------------------------------------------------------
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }

    fun cargarDatos(){
        var listaAsig = Asignacion(this.requireContext()).mostrarTodos()
        var ventas = ArrayList<String>()
        listaIDs.clear()
        (0..listaAsig.size-1).forEach {
            val al = listaAsig.get(it)
            ventas.add(al.nomempleado + " " + al.areatrabajo + " " + al.fecha + " " + al.codigofk)
            listaIDs.add(al.idasignacion)
        }

        binding.listaAsignacion.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,ventas)

    }//cargarDatos

    fun mostrarporNombre(){
        var listaAsig = Asignacion(this.requireContext()).mostrarTodos()
        var ventas = ArrayList<String>()
        listaIDs.clear()
        (0..listaAsig.size-1).forEach {
            val al = listaAsig.get(it)
            ventas.add(al.nomempleado)
            listaIDs.add(al.idasignacion)
        }

        binding.listaAsignacion.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,ventas)

    }//cargarDatos

    fun mostrarporfecha(){
        var listaAsig = Asignacion(this.requireContext()).mostrarTodos()
        var ventas = ArrayList<String>()
        listaIDs.clear()
        (0..listaAsig.size-1).forEach {
            val al = listaAsig.get(it)
            ventas.add(al.fecha)
            listaIDs.add(al.idasignacion)
        }

        binding.listaAsignacion.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,ventas)

    }//cargarDatos

    fun mostrarporArea(){
        var listaAsig = Asignacion(this.requireContext()).mostrarTodos()
        var ventas = ArrayList<String>()
        listaIDs.clear()
        (0..listaAsig.size-1).forEach {
            val al = listaAsig.get(it)
            ventas.add(al.areatrabajo)
            listaIDs.add(al.idasignacion)
        }

        binding.listaAsignacion.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,ventas)

    }//cargarDatos

    fun mostrar(){
        when(binding.spinnerA.selectedItemId.toInt()) {
            0 -> mostrarporNombre()
            1 -> mostrarporfecha()
            2 -> mostrarporArea()
            3 -> cargarDatos()
            else -> cargarDatos()
        }
    }
}