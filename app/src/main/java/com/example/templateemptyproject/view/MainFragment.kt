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
    private var arrayLotteriesQuerried = arrayOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        activity?.title = "Lottery"
        setHasOptionsMenu(true)
        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fetchTwoLottries -> {
                arrayLotteriesQuerried = arrayOf("6aus49","eurojackpot")
                viewModel.fetchApiAndUpdateData(
                    *arrayLotteriesQuerried // Spread Operator
                )
                true
            }
            R.id.fetchFourLottries -> {
                arrayLotteriesQuerried = arrayOf("6aus49","eurojackpot", "spiel77", "super6")
                viewModel.fetchApiAndUpdateData(
                    *arrayLotteriesQuerried
                )
                true
            }
            R.id.fetchThreeLottries -> {
                arrayLotteriesQuerried = arrayOf("6aus49","eurojackpot", "spiel77")
                viewModel.fetchApiAndUpdateData(
                    *arrayLotteriesQuerried
                )
                true
            }
            R.id.reset -> {
                showLoadingState(false)
                fillLotteries(emptyList())
                showErrorState("")
                showRetry(false)
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnRetryClickListener()

        setupAdapter()
        setupRecyclerView()

        bindViewModelLiveData()
        setItemsDivider()
    }

    private fun bindViewModelLiveData() {
        viewModel.isBackendLoading.observe(
            viewLifecycleOwner,
            { isBackendLoading ->
                showLoadingState(isBackendLoading)
            }
        )
        viewModel.lotteriesList.observe(
            viewLifecycleOwner,
            { arrayLotteries ->
                fillLotteries(arrayLotteries?.toList())
            }
        )
        viewModel.backendErrorMessage.observe(
            viewLifecycleOwner,
            { errorMessage ->
                showErrorState(errorMessage)
            }
        )
    }

    private fun setItemsDivider() {
        val itemDecorator = DividerItemDecoration(viewBinding.root.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(viewBinding.root.context, R.drawable.recycler_separator)!!)
        viewBinding.rvLotteries.addItemDecoration(itemDecorator)
    }

    private fun setOnRetryClickListener(){
        viewBinding.btnRetryLoadingLotteries.setOnClickListener {
            showLoadingState(true)
            viewModel.fetchApiAndUpdateData(
                *arrayLotteriesQuerried
            )
        }
    }

    private fun setupAdapter(){
        lotteriesAdapter = LotteriesAdapter(
            { lotteryName, lotteryPosition -> doOnItemClick(lotteryName, lotteryPosition) }
        )
    }

    private fun setupRecyclerView() {
        with(viewBinding.rvLotteries){
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(false)
            adapter = lotteriesAdapter
        }
    }

    private fun doOnItemClick(lotteryName: String, lotteryPosition: Int){
        val websiteUrl = "https://www.lotto24.de/"
        context?.let { context ->
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Do you want to know more about $lotteryName? \n You are in the Lotetry #${lotteryPosition+1}")
            builder.setMessage("CLICK YES to visit the website -> \n$websiteUrl")

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

    private fun showLoadingState(isBackendLoading: Boolean) {
        lotteriesAdapter.submitList(emptyList())
        viewBinding.pbProgressLoadingLotteries.visibility = if(isBackendLoading) View.VISIBLE else View.INVISIBLE
        viewBinding.tvMessageLoadingLotteries.visibility = if(isBackendLoading) View.VISIBLE else View.INVISIBLE
        showCardView(isBackendLoading)
        viewBinding.btnRetryLoadingLotteries.visibility = if(isBackendLoading) View.INVISIBLE else View.VISIBLE
        viewBinding.tvMessageError.visibility = if(isBackendLoading) View.INVISIBLE else View.VISIBLE
        if(isBackendLoading) viewBinding.tvMessageError.text = ""
    }

    private fun fillLotteries(lotteryData: List<LotteryData>?) {
        showLoadingState(false)
        showErrorState("")
        showRetry(false)
        viewBinding.CardViewLotteries.visibility = View.VISIBLE
        lotteriesAdapter.submitList(lotteryData)
    }

    private fun showErrorState(errorMessage: String) {
        showLoadingState(false)
        lotteriesAdapter.submitList(emptyList())
        showCardView(false)
        viewBinding.tvMessageError.visibility = if (!errorMessage.isEmpty()) View.VISIBLE else View.INVISIBLE
        viewBinding.tvMessageError.text = errorMessage
        showRetry(true)
    }

    private fun showCardView(isCardShown: Boolean){
        viewBinding.CardViewLotteries.visibility = if (isCardShown) View.VISIBLE else View.INVISIBLE
    }

    private fun showRetry(isRetryShown: Boolean){
        viewBinding.btnRetryLoadingLotteries.visibility = if (isRetryShown) View.VISIBLE else View.INVISIBLE
    }
}