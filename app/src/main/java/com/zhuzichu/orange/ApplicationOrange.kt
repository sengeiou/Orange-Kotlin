package com.zhuzichu.orange

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import androidx.appcompat.app.AppCompatDelegate
import com.zhuzichu.base.common.preference.UserPreference

import com.zhuzichu.base.global.AppGlobal
import com.zhuzichu.orange.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

import io.reactivex.plugins.RxJavaPlugins.setErrorHandler
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * @author Administrator
 */
class ApplicationOrange : DaggerApplication() {

    private val userPreference: UserPreference by lazy { UserPreference() }


    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = true)
            enableStrictMode()
        }
        super.onCreate()
        AppGlobal.init(this)
        initAutoSize()
        setErrorHandler {

        }
        AppCompatDelegate.setDefaultNightMode(userPreference.uiMode)
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .setCustomFragment(false)
            .setLog(false)
            .setUseDeviceSize(false).isBaseOnWidth = true
        AutoSize.initCompatMultiProcess(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }


    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }
}