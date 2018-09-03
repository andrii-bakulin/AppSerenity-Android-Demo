package com.appserenity.android.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appserenity.AppSerenity;
import com.appserenity.ads.banner.AdBanner;
import com.appserenity.ads.interstitial.AdInterstitial;
import com.appserenity.ads.rwvideo.AdRewardedVideo;
import com.appserenity.ads.rwvideo.AdRewardedVideoListener;
import com.appserenity.enums.AdNetwork;
import com.appserenity.enums.RewardedVideoFinishState;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------------------------------------------------------------------------------------
        // AppSerenity : initialization
        // This code maybe moved to Application.onCreate()

        String AppSerenityAppId  = "10001";
        String AppSerenitySecret = "12345678901234567890123456789012";

        AppSerenity.setDebugLog(true);
        AppSerenity.setAppIdAndSecret( this, AppSerenityAppId, AppSerenitySecret, AppSerenity.Orientation.PORTRAIT );

        // optional
        AppSerenity.setUserCustomId("UserCustomIdHere");

        //------------------------------------------------------------------------------------------

        AppSerenity.onCreate(this);

        this.initButtonEvents();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AppSerenity.onResume(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        AppSerenity.onPause(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        AppSerenity.onDestroy(this);
    }

    //----------------------------------------------------------------------------------------------

    private void initButtonEvents()
    {
        findViewById(R.id.button_bnr_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { bannerStartRequests(); }
        });

        findViewById(R.id.button_bnr_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { bannerStopRequests(); }
        });

        findViewById(R.id.button_bnr_height).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { bannerDetectBannerHeight(); }
        });

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        findViewById(R.id.button_int_ready).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { interstitialIsReady(); }
        });

        findViewById(R.id.button_int_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { interstitialShow(); }
        });

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        findViewById(R.id.button_rwv_ready).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { rewardedVideoIsReady(); }
        });

        findViewById(R.id.button_rwv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { rewardedVideoShow(); }
        });

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        findViewById(R.id.button_iap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { userDidInAppPurchase(); }
        });
    }

    //----------------------------------------------------------------------------------------------

    public void bannerStartRequests()
    {
        AdBanner.startRequestAndShowBanner();
    }

    public void bannerStopRequests()
    {
        AdBanner.stopRequestsAndHideBanner();
    }

    public void bannerDetectBannerHeight()
    {
        int bannerHeightPx = AdBanner.getBannerHeightPx(AdBanner.detectBestBannerSize());
        this.showMessage( "Banner Height=" + String.valueOf(bannerHeightPx) + "px" );
    }

    //----------------------------------------------------------------------------------------------

    public void interstitialIsReady()
    {
        boolean isReady = AdInterstitial.isReady();
        this.showMessage( isReady ? "Interstitial is ready" : "Interstitial is not ready" );
    }

    public void interstitialShow()
    {
        boolean isShowed = AdInterstitial.showAd();
        this.showMessage( isShowed ? "Interstitial was showed" : "Interstitial was not showed" );
    }

    //----------------------------------------------------------------------------------------------

    public void rewardedVideoIsReady()
    {
        boolean isReady = AdRewardedVideo.isReady();
        this.showMessage( isReady ? "RwVideo is ready" : "RwVideo is not ready" );
    }

    public void rewardedVideoShow()
    {
        boolean isShowed = AdRewardedVideo.playVideo(new AdRewardedVideoListener() {
            @Override
            public void onRewardedVideoAdClosed(AdNetwork adNetwork, RewardedVideoFinishState finishState, int reward, String currency) {
                // values 'reward' & 'currency' may return be ad-network, BUT MAY BE NOT!!! :)

                if( finishState == RewardedVideoFinishState.COMPLETED )
                    showMessage("RwVideo SUCCESS completed! Do User Reward!");
                else
                    showMessage("RwVideo complete was ERROR. No rewards!");
            }
        });

        if( !isShowed )
            this.showMessage("RwVideo not showed... do FAILED-logic here, because callback will not be executed!");
    }

    //----------------------------------------------------------------------------------------------

    public void userDidInAppPurchase()
    {
        // optional
        AppSerenity.fixInAppPurchase( "com.iap.id", 3.99f, "USD");
    }

    //----------------------------------------------------------------------------------------------

    public void showMessage( final String message )
    {
        Toast.makeText( getApplicationContext(),message, Toast.LENGTH_LONG ).show();
    }
}
