package com.example.templateemptyproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.templateemptyproject.databinding.ItemlistLotteryBinding
import com.example.templateemptyproject.datamodel.LotteryData
import com.example.templateemptyproject.utils.DAY_FORMAT
import com.example.templateemptyproject.utils.SHORT_DATE_FORMAT
import com.example.templateemptyproject.utils.getDayFromStringLongDate
import com.example.templateemptyproject.utils.getShortDateFormatFromStringLongDate

class LotteriesAdapter(
    val actionItemClicked: (String, Int) -> Unit
): ListAdapter<LotteryData, LotteriesAdapter.LotteriesViewHolder>(lotteryItemCallback) {

    inner class LotteriesViewHolder(val viewBinding: ItemlistLotteryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: LotteryData) {
            handleLotteryName(viewBinding, data)
            handleNumberOfWinners(viewBinding, data)
            handleTotalStake(viewBinding, data)
            handleLastDrawDate(viewBinding, data)
            handleBannerNextDrawDate(viewBinding, data)
            handleNextDrawDate(viewBinding, data)
            handleNextDrawJackpotOne(viewBinding, data)
            handleNextDrawJackpotTwo(viewBinding, data)
            handleNextDrawDateTextColor(viewBinding, data)
            handleNextDrawDateBackgroundDrawer(viewBinding, data)
            handleBannerBackgroundColor(viewBinding, data)
            handleBannerIcon(viewBinding, data)
            handleBannerNextDrawTextColor(viewBinding, data)
            handleMainPointsVisibility(viewBinding, data)
            handleAdditionalPointsVisibility(viewBinding, data)
            handleMainPointsValues(viewBinding, data)
            handleAdditionalPointsValues(viewBinding, data)

        }

        private fun handleLotteryName(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            viewBinding.tvNameLottery.text = if (data.name == "eurojackpot") "Eurojackpot" else "LOTTO ${data.name.orEmpty()}"
        }

        private fun handleNumberOfWinners(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            var winners = 0
            data.lastDraw?.winners?.run {
                winners =
                    this.WC_1
                        .plus(this.WC_2)
                        .plus(this.WC_3)
                        .plus(this.WC_4)
                        .plus(this.WC_5)
                        .plus(this.WC_6)
                        .plus(this.WC_7)
                        .plus(this.WC_8)
                        .plus(this.WC_9)
                        .plus(this.WC_10)
                        .plus(this.WC_11)
                        .plus(this.WC_12)
            }
            viewBinding.tvNumberOfWinner.text = winners.toString()
        }

        private fun handleTotalStake(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            viewBinding.tvTotalStakeLastDraw.text = "Total stake: ${data.lastDraw?.totalStake.orEmpty()}€"
        }

        private fun handleLastDrawDate(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.lastDraw?.drawDateUtc?.run {
                val dateShortFormat = getShortDateFormatFromStringLongDate(
                    SHORT_DATE_FORMAT,
                    this
                )
                val dayShortFormat: String = getDayFromStringLongDate(this)
                viewBinding.tvDateResultsLastDraw.text = "$dayShortFormat., $dateShortFormat"
            }
        }

        private fun handleBannerNextDrawDate(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.nextDraw?.drawDateUtc?.run {
                val dayOfTheWeek = getShortDateFormatFromStringLongDate(
                    DAY_FORMAT,
                    this
                )
                val dayShortFormat: String = getDayFromStringLongDate(this)
                viewBinding.tvBannerDateNextDraw.text = "$dayOfTheWeek\n$dayShortFormat"
            }
        }

        private fun handleNextDrawDate(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.nextDraw?.drawDateUtc?.run {
                val dateShortFormat = getShortDateFormatFromStringLongDate(
                    SHORT_DATE_FORMAT,
                    this
                )
                val dayShortFormat: String = getDayFromStringLongDate(this)
                viewBinding.tvDateNextDraw.text = "$dayShortFormat., $dateShortFormat"
            }
        }

        private fun handleNextDrawJackpotOne(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.nextDraw?.jackpot?.allJackpots?.run {
                viewBinding.tvJackpotOne.visibility = if (!jackpotOne.isNullOrEmpty()) View.VISIBLE else View.GONE
                viewBinding.tvJackpotOne.text = "#1 Jackpot : ${jackpotOne.orEmpty()}€"
            }
        }

        private fun handleNextDrawJackpotTwo(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.nextDraw?.jackpot?.allJackpots?.run {
                viewBinding.tvJackpotTwo.visibility = if (!jackpotTwo.isNullOrEmpty()) View.VISIBLE else View.GONE
                viewBinding.tvJackpotTwo.text = "#2 Jackpot : ${jackpotTwo.orEmpty()}€"
            }
        }

        private fun handleNextDrawDateTextColor(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            when(data.name){
                "6aus49" -> viewBinding.tvDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.red_next_draw))
                "eurojackpot" -> viewBinding.tvDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.yellow_next_draw))
                else -> viewBinding.tvDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.white))
            }
        }

        private fun handleNextDrawDateBackgroundDrawer(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            when(data.name){
                "6aus49" -> viewBinding.tvDateNextDraw?.setBackgroundResource(R.drawable.date_background_lotto24)
                "eurojackpot" -> viewBinding.tvDateNextDraw?.setBackgroundResource(R.drawable.date_background_eurojackpot)
                else -> viewBinding.tvDateNextDraw?.setBackgroundResource(R.color.blue)
            }
        }

        private fun handleBannerBackgroundColor(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            when(data.name){
                "6aus49" -> viewBinding.bannerLottery?.setBackgroundResource(R.color.yellow_next_draw)
                "eurojackpot" -> viewBinding.bannerLottery?.setBackgroundResource(R.color.eurojackpot_background)
                else -> viewBinding.bannerLottery?.setBackgroundResource(R.color.blue)
            }
        }

        private fun handleBannerIcon(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            when(data.name){
                "6aus49" -> viewBinding.ivLogoLottery?.setImageResource(R.drawable.logo_lotto24)
                "eurojackpot" -> viewBinding.ivLogoLottery?.setImageResource(R.drawable.logo_eurojackpot)
                else -> viewBinding.ivLogoLottery?.setImageResource(R.drawable.ic_launcher_background)
            }
        }

        private fun handleBannerNextDrawTextColor(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            when(data.name){
                "6aus49" -> viewBinding.tvBannerDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.red_next_draw))
                "eurojackpot" -> viewBinding.tvBannerDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.yellow_next_draw))
                else -> viewBinding.tvBannerDateNextDraw?.setTextColor(ContextCompat.getColor(viewBinding.root.context, R.color.white))
            }
        }

        private fun handleMainPointsVisibility(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            if (data.lastDraw?.drawResult?.mainNumbers?.size == 5
                || data.lastDraw?.drawResult?.wholeNumber?.length == 5){
                viewBinding.tvNumberSixLight.visibility = View.GONE
                viewBinding.tvNumberSevenLight.visibility = View.GONE
            }
            if (data.lastDraw?.drawResult?.mainNumbers?.size == 6
                || data.lastDraw?.drawResult?.wholeNumber?.length == 6){
                viewBinding.tvNumberSevenLight.visibility = View.GONE
            }
        }

        private fun handleAdditionalPointsVisibility(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            if (data.lastDraw?.drawResult?.twoAdditionalEuroJackpotNumers == null){
                if (data.lastDraw?.drawResult?.oneAdditionalLotto6aus49Number == null){
                    viewBinding.containerPoints.setPadding(0, 0, 20, 0)
                    viewBinding.containerAdditionalPointsLotto24.visibility = View.GONE
                } else viewBinding.tvNumberTwoDark.visibility = View.GONE
            }
            if (data.lastDraw?.drawResult?.oneAdditionalLotto6aus49Number == null){
                if (data.lastDraw?.drawResult?.twoAdditionalEuroJackpotNumers == null){
                    viewBinding.containerPoints.setPadding(0, 0, 20, 0)
                    viewBinding.containerAdditionalPointsLotto24.visibility = View.GONE
                }
            }
        }

        private fun handleMainPointsValues(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.lastDraw?.drawResult?.mainNumbers?.run {
                viewBinding.tvNumberOneLight.text = this.get(0).toString()
                viewBinding.tvNumberTwoLight.text = this.get(1).toString()
                viewBinding.tvNumberThreeLight.text = this.get(2).toString()
                viewBinding.tvNumberFourLight.text = this.get(3).toString()
                viewBinding.tvNumberFiveLight.text = this.get(4).toString()
                if(this.size == 6){
                    viewBinding.tvNumberSixLight.text = this.get(5).toString()
                }
            }
            data.lastDraw?.drawResult?.wholeNumber?.run {
                viewBinding.tvNumberOneLight.text = this.get(0).toString()
                viewBinding.tvNumberTwoLight.text = this.get(1).toString()
                viewBinding.tvNumberThreeLight.text = this.get(2).toString()
                viewBinding.tvNumberFourLight.text = this.get(3).toString()
                viewBinding.tvNumberFiveLight.text = this.get(4).toString()
                viewBinding.tvNumberSixLight.text = this.get(5).toString()
                if (this.length == 7){
                    viewBinding.tvNumberSevenLight.text = this.get(6).toString()
                }
            }
        }

        private fun handleAdditionalPointsValues(viewBinding: ItemlistLotteryBinding, data: LotteryData) {
            data.lastDraw?.drawResult?.twoAdditionalEuroJackpotNumers?.run {
                viewBinding.tvNumberOneDark.text = this.get(0).toString()
                viewBinding.tvNumberTwoDark.text = this.get(1).toString()
            }
            data.lastDraw?.drawResult?.oneAdditionalLotto6aus49Number?.run {
                viewBinding.tvNumberOneDark.text = this.toString()
            }
        }

        fun setOnItemClicked(data: LotteryData, dataPosition: Int) {
            viewBinding.root.setOnClickListener {
                actionItemClicked(data.name.orEmpty(), dataPosition)
            }
        }

    }

    companion object {
        val lotteryItemCallback = object : DiffUtil.ItemCallback<LotteryData>() {

            override fun areItemsTheSame(
                oldItem: LotteryData,
                newItem: LotteryData
            ): Boolean {
                return newItem.hashCode() == oldItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: LotteryData,
                newItem: LotteryData
            ): Boolean {
                return newItem.equals(oldItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LotteriesViewHolder {
        return LotteriesViewHolder(
            // Important to keep the "parent, false" to make the Recycler expand
            ItemlistLotteryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LotteriesViewHolder, position: Int) {
        val data = this.getItem(position)
        holder.bind(data)
        holder.setOnItemClicked(data, position)
    }


}