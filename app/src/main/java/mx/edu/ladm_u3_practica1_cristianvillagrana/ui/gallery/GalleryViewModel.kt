package mx.edu.ladm_u3_practica1_cristianvillagrana.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Inventario"
    }
    val text: LiveData<String> = _text
}