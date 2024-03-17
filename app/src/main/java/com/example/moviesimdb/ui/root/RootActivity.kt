package com.example.moviesimdb.ui.root

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.moviesimdb.R
import com.example.moviesimdb.databinding.ActivityRootBinding
import com.example.moviesimdb.ui.movies.MoviesFragment

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        binding = ActivityRootBinding.inflate(layoutInflater)
        binding.root

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.rootFragmentContainerView, MoviesFragment())
            }
        }
    }
}