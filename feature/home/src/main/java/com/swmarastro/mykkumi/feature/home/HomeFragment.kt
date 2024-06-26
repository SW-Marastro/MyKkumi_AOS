package com.swmarastro.mykkumi.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.swmarastro.mykkumi.common_ui.base.BaseFragment
import com.swmarastro.mykkumi.domain.entity.HomeBannerListVO
import com.swmarastro.mykkumi.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var bannerAdapter: HomeBannerViewPagerAdapter
    private lateinit var timer: Timer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        lifecycleScope.launchWhenCreated {
            initView()
        }
        super.onViewCreated(view, savedInstanceState)

        // 배너 테스트용
        /*val bannerList: HomeBannerListVO = HomeBannerListVO(
            mutableListOf(
                HomeBannerVO(1, "https://avatars.githubusercontent.com/u/76805879?v=4"),
                HomeBannerVO(2, "https://avatars.githubusercontent.com/u/168630394?s=200&v=4")
            )
        )
        initBannerViewPager(bannerList)*/

        startAutoScroll()
    }

    override suspend fun initView() {
        bind {
            vm = viewModel
        }
        setHomeBanner() // 배너
    }

    private fun initBannerViewPager(banners: HomeBannerListVO) {
        bannerAdapter = HomeBannerViewPagerAdapter(mutableListOf())
        binding.viewpagerBanner.adapter = bannerAdapter
        binding.viewpagerBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.lifecycleOwner = this
        lifecycleScope.launchWhenStarted {
            viewModel.bannerImageUiState.collect { bitmaps ->
                binding.viewpagerBanner.adapter?.let {
                    if (it is HomeBannerViewPagerAdapter) {
                        it.setImages(bitmaps)
                    }
                }
            }
        }

        viewModel.loadImages(banners.banners)
    }

    suspend fun setHomeBanner() {
        viewModel.setHomeBanner()
        viewModel.homeBannerUiState.collect { response ->
            initBannerViewPager(response)
        }
    }

    private fun startAutoScroll() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                binding.viewpagerBanner.post {
                    binding.viewpagerBanner.currentItem = (binding.viewpagerBanner.currentItem + 1) % bannerAdapter.itemCount
                }
            }
        }, 3000, 3000) // Delay 3 seconds, repeat every 3 seconds
    }

    override fun onDestroyView() {
        _binding = null
        timer.cancel()
        super.onDestroyView()
    }
}