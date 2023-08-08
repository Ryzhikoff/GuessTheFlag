package evgeniy.ryzhikov.guesstheflag.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityRatingBinding
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.RatingField
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatHeader
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatisticAdapter
import evgeniy.ryzhikov.guesstheflag.utils.CenterSmoothScroller
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController


class RatingActivity : AppCompatActivity() {
    lateinit var binding: ActivityRatingBinding
    private lateinit var recyclerView: RecyclerView
    private var adapter = StatisticAdapter()
    private val viewModel = App.instance.ratingViewModel
    private var playerPosition = 0
    private val media = MediaPlayerController.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRV()
        lifecycle.addObserver(viewModel)
        viewModel.playerPositionInRatingLiveData.observe(this) { position ->
            playerPosition = position
        }
        viewModel.ratingListLiveData.observe(this) { ratingList ->
            displayRating(ratingList)
            stopLoadingAnimation()
        }
        getRating()
        addButtonsListeners()
        HideNavigationBars.hide(window, binding.root)
    }

    private fun initRV() {
        recyclerView = binding.ratingRecycler
        recyclerView.adapter = adapter
    }

    private fun getRating() {
        viewModel.getRating()
    }

    private fun displayRating(ratingList: ArrayList<StatisticData>) {

        if (playerPosition == -1) {
            setPlayerRating(ratingList.size, StatisticData(name = FirebaseUserUid.getName()))
        } else {
            setPlayerRating(playerPosition + 1, ratingList[playerPosition] )
        }

        adapter.addItem(StatHeader(getString(R.string.statistic_rating_tittle)))

        ratingList.forEachIndexed() { i: Int, statisticData: StatisticData ->

            adapter.addItem(
                RatingField(
                    position = "#${i+1}",
                    name = statisticData.name,
                    points = statisticData.totalPoints,
                    games = statisticData.totalGame,
                    percentWin = statisticData.totalPercentCorrect
                )
            )
        }
        adapter.update()
    }


    private fun setPlayerRating(position: Int, statisticData: StatisticData) {
        binding.ratingItem.setData(
            position = position,
            name = statisticData.name,
            points = statisticData.totalPoints,
            games = statisticData.totalGame,
            percent = statisticData.totalPercentCorrect

        )
        binding.ratingItem.visibility = View.VISIBLE
    }

    private fun stopLoadingAnimation() {
        binding.loadingAnimation.cancelAnimation()
        binding.loadingAnimation.visibility = View.GONE
    }

    private fun addButtonsListeners() {
        binding.btnBack.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            startMainMenu()
        }
        binding.ratingItem.setOnClickListener {
            media.playSound(MediaPlayerController.SoundEvent.CLICK_BUTTON)
            smoothScrollToPosition()
        }
    }
    private fun startMainMenu() {
        media.stopMusic = false
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun smoothScrollToPosition() {
        if (binding.ratingItem.visibility != View.INVISIBLE && playerPosition != -1) {
            //recyclerView.smoothScrollToPosition(playerPosition)
            val lm = recyclerView.layoutManager
            val smoothScroller: SmoothScroller = CenterSmoothScroller(recyclerView.context)

            smoothScroller.targetPosition = playerPosition

            lm?.startSmoothScroll(smoothScroller)
        }
    }

    override fun onResume() {
        super.onResume()
        if (media.stopMusic) {
            media.resumeMusic()
        }
        media.stopMusic = true
    }

    override fun onPause() {
        super.onPause()
        if (media.stopMusic) {
            media.pauseMusic()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.clear()
    }
}