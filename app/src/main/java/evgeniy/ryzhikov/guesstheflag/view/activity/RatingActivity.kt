package evgeniy.ryzhikov.guesstheflag.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import evgeniy.ryzhikov.guesstheflag.App
import evgeniy.ryzhikov.guesstheflag.R
import evgeniy.ryzhikov.guesstheflag.data.FirebaseUserUid
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityRatingBinding
import evgeniy.ryzhikov.guesstheflag.domain.PlayerEnvironment
import evgeniy.ryzhikov.guesstheflag.domain.statistic.StatisticData
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.RatingField
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatHeader
import evgeniy.ryzhikov.guesstheflag.domain.statistic.rv.StatisticAdapter
import evgeniy.ryzhikov.guesstheflag.settings.TAG
import evgeniy.ryzhikov.guesstheflag.utils.HideNavigationBars
import evgeniy.ryzhikov.guesstheflag.utils.MediaPlayerController
import evgeniy.ryzhikov.guesstheflag.viewmodel.RatingViewModel
import javax.inject.Inject


class RatingActivity : AppCompatActivity() {
    lateinit var binding: ActivityRatingBinding
    private lateinit var recyclerView: RecyclerView
    private var adapter = StatisticAdapter()
    private val viewModel: RatingViewModel by viewModels()

    @Inject
    lateinit var media: MediaPlayerController
    @Inject
    lateinit var firebaseUserUid: FirebaseUserUid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App.instance.dagger.inject(this)

        initRV()

        viewModel.ratingListLiveData.observe(this) { ratingList ->
            displayRating(ratingList)
            stopLoadingAnimation()
        }

        viewModel.playerPositionLiveData.observe(this) { playerPosition ->
            Log.d(TAG, "player position: $playerPosition")
            addPlayerEnvironment(PlayerEnvironment(playerPosition, viewModel.playerList[0],viewModel.playerList[1], viewModel.playerList[2]))
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

        adapter.addItem(StatHeader(getString(R.string.statistic_rating_top10)))

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
        Log.d(TAG, "displayRating is finish")
        adapter.update()
    }

    private  fun addPlayerEnvironment(playerEnvironment: PlayerEnvironment) {
        adapter.addItem(StatHeader(getString(R.string.statistic_rating_player_tittle)))

        if (playerEnvironment.onTopPlayerStatisticData.totalPoints != 0) {
            adapter.addItem(
                RatingField(
                    position = "#${playerEnvironment.playerPosition - 1}",
                    name = playerEnvironment.onTopPlayerStatisticData.name,
                    points = playerEnvironment.onTopPlayerStatisticData.totalPoints,
                    games = playerEnvironment.onTopPlayerStatisticData.totalGame,
                    percentWin = playerEnvironment.onTopPlayerStatisticData.totalPercentCorrect
                )
            )
        }

        adapter.addItem(RatingField(
            position = "#${playerEnvironment.playerPosition}",
            name = firebaseUserUid.getName(),
            points = playerEnvironment.playerStatisticData.totalPoints,
            games = playerEnvironment.playerStatisticData.totalGame,
            percentWin = playerEnvironment.playerStatisticData.totalPercentCorrect
        ))

        if (playerEnvironment.onBellowPlayerStatisticData.totalPoints != 0) {
            adapter.addItem(
                RatingField(
                    position = "#${playerEnvironment.playerPosition + 1}",
                    name = playerEnvironment.onBellowPlayerStatisticData.name,
                    points = playerEnvironment.onBellowPlayerStatisticData.totalPoints,
                    games = playerEnvironment.onBellowPlayerStatisticData.totalGame,
                    percentWin = playerEnvironment.onBellowPlayerStatisticData.totalPercentCorrect
                )
            )
        }
        adapter.update()
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
    }
    private fun startMainMenu() {
        media.stopMusic = false
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
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