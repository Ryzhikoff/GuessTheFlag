package evgeniy.ryzhikov.guesstheflag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import evgeniy.ryzhikov.guesstheflag.databinding.ActivityStatisticBinding
import evgeniy.ryzhikov.guesstheflag.fragments.RoundStatisticFragment
import evgeniy.ryzhikov.guesstheflag.fragments.TotalStatisticFragment

class StatisticActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundleRoundStat = intent.extras?.getBundle("stat")
        if (bundleRoundStat != null) {
            displayRoundStatisticFragment(bundleRoundStat)
            displayButtonPlayAgain()
        }

        displayTotalStatisticFragment()
        setButtonOnClickListener()
    }

    private fun displayRoundStatisticFragment(bundleRoundStat: Bundle) {

        val roundStatisticFragment = RoundStatisticFragment()
        roundStatisticFragment.arguments = bundleRoundStat

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentRoundStatistic, roundStatisticFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun displayButtonPlayAgain() {
        binding.btnAgain.visibility = View.VISIBLE
    }

    private fun displayTotalStatisticFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentTotalStatistic, TotalStatisticFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setButtonOnClickListener() {
        binding.btnBack.setOnClickListener {
            startMainMenu()
        }

        binding.btnAgain.setOnClickListener {

        }
    }

    private fun startMainMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}