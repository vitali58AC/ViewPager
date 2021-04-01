package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentViewPagerBinding

class ViewPagerFragment() : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.articleTitle.setText(requireArguments().getInt(KEY_TITLE))
        binding.fragmentText.setText(requireArguments().getInt(KEY_TEXT))
        binding.fragmentImage.setImageResource(requireArguments().getInt(KEY_IMAGE))

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_TEXT = "text"
        private const val KEY_IMAGE = "image"
        fun newInstance(
            @StringRes title: Int,
            @StringRes text: Int,
            @DrawableRes image: Int
        ): ViewPagerFragment {
            return ViewPagerFragment().withArguments {
                putInt(KEY_TITLE, title)
                putInt(KEY_TEXT, text)
                putInt(KEY_IMAGE, image)
            }
        }
    }
}