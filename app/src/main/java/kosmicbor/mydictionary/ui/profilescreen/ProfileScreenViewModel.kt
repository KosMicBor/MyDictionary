package kosmicbor.mydictionary.ui.profilescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileScreenViewModel : ViewModel() {

    companion object {
        const val IMAGE_URL =
            "https://lh3.googleusercontent.com/a-/AOh14GgQ5zitTudMC4DEr9zWE89XivqQG2vfv9FzV8SBmQ=s317-p-rw-no"
    }

    private val _dataYoObserve = MutableLiveData<String>()
    val dataToObserve: LiveData<String> = _dataYoObserve

    fun getImageUrl() = _dataYoObserve.postValue(IMAGE_URL)
}