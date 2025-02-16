import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.helper_for_conf.repository.ChapterRepository
import com.example.helper_for_conf.viewmodels.ChapterViewModel

class ChapterViewModelFactory(
    private val repository: ChapterRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChapterViewModel::class.java)) {
            return ChapterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}