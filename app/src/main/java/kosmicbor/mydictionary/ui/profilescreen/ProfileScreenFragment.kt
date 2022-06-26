package kosmicbor.mydictionary.ui.profilescreen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment(R.layout.fragment_profile_screen) {

    private val viewModel: ProfileScreenViewModel by viewModels()
    private val binding: FragmentProfileScreenBinding by viewBinding(FragmentProfileScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModel()
        viewModel.getImageUrl()
    }

    private fun subscribeToViewModel() {
        viewModel.dataToObserve.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .circleCrop()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.profileImageView.setImageResource(R.drawable.ic_baseline_person)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //NOTHING TO DO
                        return false
                    }

                })
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(binding.profileImageView)
        }
    }
}