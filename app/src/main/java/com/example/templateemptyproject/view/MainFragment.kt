package com.example.templateemptyproject.view

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.templateemptyproject.LotteriesAdapter
import com.example.templateemptyproject.R
import com.example.templateemptyproject.databinding.FragmentMainBinding
import com.example.templateemptyproject.datamodel.LotteryData
import com.example.templateemptyproject.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment: Fragment() {

    lateinit var viewBinding: FragmentMainBinding
    val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var lotteriesAdapter: LotteriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        setHasOptionsMenu(true)
        setOnRetryClickListener()
        setupAdapter()
        setupRecyclerView()
        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fetchData -> {
                showLoadingState(true)
//                viewModel.fetchApiAndUpdateData(
//                    "6aus49",
//                    "eurojackpot"
//                )
                viewModel.fetchApiAndUpdateData(
                    "6aus49",
                    "eurojackpot",
                    "spiel77",
                    "super6"
                )
                true
            }
            R.id.reset -> {
                showLoadingState(false)
                fillLotteries(emptyList())
                showErrorInGettingData("")
                showRetry(false)
                true
            }
            else -> false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isBackendLoading.observe(
            viewLifecycleOwner,
            { isBackendLoading ->
                showLoadingState(isBackendLoading)
            }
        )

        viewModel.listOfLotteries.observe(
            viewLifecycleOwner,
            { arrayLotteries ->
                fillLotteries(arrayLotteries?.toList())
            }
        )

        viewModel.backendErrorMessage.observe(
            viewLifecycleOwner,
            { errorMessage ->
                showErrorInGettingData(errorMessage)
            }
        )

        viewModel.isRetryShown.observe(
            viewLifecycleOwner,
            { isRetryShown ->
                showRetry(isRetryShown)
            }
        )

        setItemsDivider()
    }

    fun doOnClickOnLottery(lotteryName: String, lotteryPosition: Int){
        val websiteUrl = "https://www.lotto24.de/"
        context?.let { context ->
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Do you want to know more about $lotteryName? \n You are in the Lotetry #${lotteryPosition+1}")
            builder.setMessage("Visit the website: \n$websiteUrl")

            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                startActivity(browserIntent)
                Toast.makeText(context, "ENJOY the $lotteryName", Toast.LENGTH_LONG).show()
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(context, "Next time you can explore: $lotteryName", Toast.LENGTH_LONG).show()
            })

            builder.setNeutralButton("Maybe", DialogInterface.OnClickListener { dialogInterface, i ->
            })

            builder.show()
        }
    }

    private fun setOnRetryClickListener(){
        viewBinding.btnRetryLoadingLotteries.setOnClickListener {
            showLoadingState(true)
            viewModel.fetchApiAndUpdateData(
                "6aus49",
                "eurojackpot"
            )
        }
    }

    private fun setupAdapter(){
        lotteriesAdapter = LotteriesAdapter(
            { lotteryName, lotteryPosition -> doOnClickOnLottery(lotteryName, lotteryPosition) }
        )
    }

    private fun setupRecyclerView() {
        with(viewBinding.rvLotteries){
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(false)
            adapter = lotteriesAdapter
        }
    }

    private fun setItemsDivider() {
        val itemDecorator = DividerItemDecoration(viewBinding.root.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(viewBinding.root.context, R.drawable.recycler_separator)!!)
        viewBinding.rvLotteries.addItemDecoration(itemDecorator)
    }

    private fun showLoadingState(isBackendLoading: Boolean) {
        viewBinding.CardViewLotteries.visibility = if(isBackendLoading) View.VISIBLE else View.INVISIBLE
        viewBinding.pbProgressLoadingLotteries.visibility = if(isBackendLoading) View.VISIBLE else View.INVISIBLE
        viewBinding.tvMessageLoadingLotteries.visibility = if(isBackendLoading) View.VISIBLE else View.INVISIBLE
        viewBinding.btnRetryLoadingLotteries.visibility = if(isBackendLoading) View.INVISIBLE else View.VISIBLE
        viewBinding.tvMessageError.visibility = if(isBackendLoading) View.INVISIBLE else View.VISIBLE
        lotteriesAdapter.submitList(emptyList())
    }

    private fun fillLotteries(lotteryData: List<LotteryData>?) {
        viewBinding.CardViewLotteries.visibility = if (!lotteryData.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        viewBinding.tvMessageLoadingLotteries.visibility = if (!lotteryData.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        viewBinding.btnRetryLoadingLotteries.visibility = if (!lotteryData.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        viewBinding.tvMessageError.visibility = if (!lotteryData.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        lotteriesAdapter.submitList(lotteryData)
    }

    private fun showErrorInGettingData(errorMessage: String) {
        viewBinding.CardViewLotteries.visibility = if (!errorMessage.isEmpty()) View.VISIBLE else View.INVISIBLE
        lotteriesAdapter.submitList(emptyList())
        viewBinding.tvMessageError.visibility = if (!errorMessage.isEmpty()) View.VISIBLE else View.INVISIBLE
        viewBinding.tvMessageError.text = errorMessage
    }

    private fun showRetry(isRetryShown: Boolean){
        viewBinding.tvMessageLoadingLotteries.visibility = if (isRetryShown) View.VISIBLE else View.INVISIBLE
        viewBinding.btnRetryLoadingLotteries.visibility = if (isRetryShown) View.VISIBLE else View.INVISIBLE
    }
}