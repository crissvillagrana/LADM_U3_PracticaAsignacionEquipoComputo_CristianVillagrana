package mx.edu.ladm_u3_practica1_cristianvillagrana.ui.gallery

import android.R
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity2
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity3
import mx.edu.ladm_u3_practica1_cristianvillagrana.MainActivity4
import mx.edu.ladm_u3_practica1_cristianvillagrana.databinding.FragmentGalleryBinding
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.BaseDatos
import mx.edu.ladm_u3_practica1_cristianvillagrana.ui.Inventario
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    var listaBarcodes = ArrayList<String>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Aquí lo chido ------------------------------------------------------------------------------------------------------------------------
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            mx.edu.ladm_u3_practica1_cristianvillagrana.R.array.inventariospinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.btnbuscar.setOnClickListener {
            mostrar()
        }
        binding.btnagregar.setOnClickListener {
            var otraVentana = Intent(requireContext(), MainActivity2::class.java)
            startActivity(otraVentana)
        }

        binding.listaInventario.setOnItemClickListener { adapterView, view, index, l ->
            val codigoBarras = listaBarcodes[index]
            val inve = Inventario(requireContext()).buscarProducto(codigoBarras)

            AlertDialog.Builder(requireContext())
                .setTitle("Opciones")
                .setMessage("Ha seleccionado el producto: " +
                        "\n ${inve.tipoequipo} ${inve.caracteristicas} " +
                        "Código de barras: \n${inve.codigobarras}")
                .setNegativeButton("Eliminar"){d,i->
                    inve.eliminar()
                    mostrar()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentanaupdate = Intent(requireContext(),MainActivity3::class.java)
                    otraVentanaupdate.putExtra("codigobarras",inve.codigobarras)
                    startActivity(otraVentanaupdate)
                }
                .setNeutralButton("Asignar"){d,i->
                    var otraVentanaAsignar = Intent(requireContext(),MainActivity4::class.java)
                    otraVentanaAsignar.putExtra("codigobarrasa",inve.codigobarras)
                    startActivity(otraVentanaAsignar)
                }
                .show()
        }
        //End--------------------------------------------------------------------------------------------------------------------------------------------
        return root
    }//onCreateView

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        cargarDatos()
    }


    fun cargarDatos(){
        var listaInventario = Inventario(this.requireContext()).mostrarInventario()
        var equipos = ArrayList<String>()
        listaBarcodes.clear()
        (0..listaInventario.size-1).forEach {
            val al = listaInventario.get(it)
            equipos.add(al.tipoequipo + " " + al.caracteristicas + " " + al.fechacompra)
            listaBarcodes.add(al.codigobarras)
        }

        binding.listaInventario.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,equipos)

    }//cargarDatos

    fun mostrarportipoequipo(){
        var listaInventario = Inventario(this.requireContext()).mostrarInventario()
        var equipos = ArrayList<String>()
        listaBarcodes.clear()
        (0..listaInventario.size-1).forEach {
            val al = listaInventario.get(it)
            equipos.add(al.tipoequipo)
            listaBarcodes.add(al.codigobarras)
        }

        binding.listaInventario.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,equipos)

    }//cargarDatos

    fun mostrarporspecs(){
        var listaInventario = Inventario(this.requireContext()).mostrarInventario()
        var equipos = ArrayList<String>()
        listaBarcodes.clear()
        (0..listaInventario.size-1).forEach {
            val al = listaInventario.get(it)
            equipos.add(al.caracteristicas)
            listaBarcodes.add(al.codigobarras)
        }

        binding.listaInventario.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,equipos)

    }//cargarDatos

    fun mostrarporfecha(){
        var listaInventario = Inventario(this.requireContext()).mostrarInventario()
        var equipos = ArrayList<String>()
        listaBarcodes.clear()
        (0..listaInventario.size-1).forEach {
            val al = listaInventario.get(it)
            equipos.add(al.fechacompra)
            listaBarcodes.add(al.codigobarras)
        }

        binding.listaInventario.adapter = ArrayAdapter<String>(this.requireContext(),
            R.layout.simple_list_item_1,equipos)

    }//cargarDatos

    fun mostrar(){
        when(binding.spinner.selectedItemId.toInt()) {
            0 -> mostrarportipoequipo()
            1 -> mostrarporspecs()
            2 -> mostrarporfecha()
            3 -> cargarDatos()
            else -> cargarDatos()
        }
        }


}//class


