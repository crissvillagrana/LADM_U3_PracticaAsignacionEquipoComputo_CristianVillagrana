package mx.edu.ladm_u3_practica1_cristianvillagrana.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.core.content.contentValuesOf

class Inventario(este: Context) {
    private val este = este
    var codigobarras = ""
    var tipoequipo = ""
    var caracteristicas = ""
    var fechacompra = ""
    var empty = true
    private var err = ""

    fun insertar() : Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""
        try{
            val tabla = basedatos.writableDatabase
            var datos = ContentValues()

            datos.put("CODIGOBARRAS",codigobarras)
            datos.put("TIPOEQUIPO",tipoequipo)
            datos.put("CARACTERISTICAS",caracteristicas)
            datos.put("FECHACOMPRA",fechacompra)

            val respuesta = tabla.insert("INVENTARIO",null,datos)
            if(respuesta == -1L){
                return false
            }

        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }

    fun mostrarInventario(): ArrayList<Inventario>{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""
        var arreglo = ArrayList<Inventario>()
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO"

            var cursor = tabla.rawQuery(SQLSELECT,null)
            //if(cursor.isNull(0))empty = true
            if(cursor.moveToFirst()){
                do{
                    val inve = Inventario(este)
                    inve.codigobarras = cursor.getString(0)
                    inve.tipoequipo = cursor.getString(1)
                    inve.caracteristicas = cursor.getString(2)
                    inve.fechacompra = cursor.getString(3)
                    arreglo.add(inve)
                }while (cursor.moveToNext())
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }//mostrarInventario

    fun buscarProducto(buscar:String):Inventario{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""
        var prod = Inventario(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO WHERE CODIGOBARRAS=?"

            var cursor = tabla.rawQuery(SQLSELECT,arrayOf(buscar))
            if(cursor.moveToFirst()){
                prod.codigobarras = cursor.getString(0)
                prod.tipoequipo = cursor.getString(1)
                prod.caracteristicas = cursor.getString(2)
                prod.fechacompra = cursor.getString(3)
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return prod
    }

    fun actualizar():Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{
            var tabla = basedatos.writableDatabase
            val datosActualizados = ContentValues()
            datosActualizados.put("TIPOEQUIPO",tipoequipo)
            datosActualizados.put("CARACTERISTICAS",caracteristicas)
            datosActualizados.put("FECHACOMPRA",fechacompra)

            val resultado = tabla.update("INVENTARIO",datosActualizados,"CODIGOBARRAS=?", arrayOf(codigobarras))
            if(resultado == 0){
                return false
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return true
    }

    fun eliminarx(deletecode:String): Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{
            var tabla = basedatos.writableDatabase
            val respuesta = tabla.delete("INVENTARIO","CODIGOBARRAS=?", arrayOf(deletecode))
            if(respuesta==0)return false
        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }//eliminar

    fun eliminar():Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{
            var tabla = basedatos.writableDatabase
            val respuesta = tabla.delete("INVENTARIO","CODIGOBARRAS=?", arrayOf(codigobarras))
            if(respuesta==0)return false
        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }
}//class

/*
val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{

        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
 */