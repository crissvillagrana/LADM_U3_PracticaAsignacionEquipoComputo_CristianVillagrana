package mx.edu.ladm_u3_practica1_cristianvillagrana.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Asignacion(este: Context) {
    private val este = este
    var idasignacion = ""
    var nomempleado = ""
    var areatrabajo = ""
    var fecha = ""
    var codigofk = ""
    private var err = ""

    fun insertar() : Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{
            val tabla = basedatos.writableDatabase
            var datos = ContentValues()

            datos.put("NOM_EMPLEADO",nomempleado)
            datos.put("AREA_TRABAJO",areatrabajo)
            datos.put("FECHA",fecha)
            datos.put("CODIGOBARRAS",codigofk)

            val respuesta = tabla.insert("ASIGNACION",null,datos)

            if(respuesta == -1L)return false

        }catch(err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedatos.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Asignacion>{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""
        var arreglo = ArrayList<Asignacion>()
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION"
            var cursor = tabla.rawQuery(SQLSELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val asig = Asignacion(este)
                    asig.idasignacion = cursor.getString(0)
                    asig.nomempleado = cursor.getString(1)
                    asig.areatrabajo = cursor.getString(2)
                    asig.fecha = cursor.getString(3)
                    asig.codigofk = cursor.getString(4)
                    arreglo.add(asig)
                }while(cursor.moveToNext())
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return arreglo
    }//mostrarTodos

    fun buscarAsignacion(buscar:String):Asignacion{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err=""
        var asig = Asignacion(este)
        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION WHERE IDASIGNACION=?"

            var cursor = tabla.rawQuery(SQLSELECT,arrayOf(buscar))
            if(cursor.moveToFirst()){
                asig.idasignacion = cursor.getString(0)
                asig.nomempleado = cursor.getString(1)
                asig.areatrabajo = cursor.getString(2)
                asig.fecha = cursor.getString(3)
                asig.codigofk = cursor.getString(4)
            }
        }catch (err:SQLiteException){
            this.err = err.message!!
        }finally {
            basedatos.close()
        }
        return asig
    }//buscarAsig

    fun actualizar():Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""

        try{
            var tabla = basedatos.writableDatabase
            val datosactualizados = ContentValues()
            datosactualizados.put("NOM_EMPLEADO",nomempleado)
            datosactualizados.put("AREA_TRABAJO",areatrabajo)

            val resultado = tabla.update("ASIGNACION",datosactualizados,"IDASIGNACION=?", arrayOf(idasignacion))
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

    fun eliminar():Boolean{
        val basedatos = BaseDatos(este,"ASIGNACIONEQUIPOCOMPUTO",null,1)
        err = ""
        try{
            var tabla = basedatos.writableDatabase
            val respuesta = tabla.delete("ASIGNACION","IDASIGNACION=?", arrayOf(idasignacion))
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