package com.example.templateemptyproject.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.templateemptyproject.R
import com.example.templateemptyproject.viewmodel.MainFragmentViewModel
import com.example.templateemptyproject.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    lateinit var viewBinding: FragmentMainBinding

    val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fetchData -> {
                viewModel.fetchApiAndUpdateData()
                true
            }
            R.id.reset -> {
                // empty livedata
                true
            }
            else -> false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lotteryMutableLiveData.observe(
            viewLifecycleOwner,
            { lotto24EurojackpotDrawData ->
                // fill some ui
            }
        )

    }
}