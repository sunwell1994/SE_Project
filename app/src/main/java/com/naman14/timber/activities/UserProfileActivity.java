package com.naman14.timber.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.naman14.timber.R;
import com.naman14.timber.adapters.UserProfileAdapter;
import com.naman14.timber.utils.CircleTransformation;
import com.naman14.timber.utils.Constants;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.widgets.RevealBackgroundView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindString;


/**
 * Created by Miroslaw Stanek on 14.01.15.
 */
public class UserProfileActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @Bind(R.id.rvUserProfile)
    RecyclerView rvUserProfile;
//    @Bind(R.id.drawer_layout)
//    DrawerLayout drawerLayout;

//    @Bind(R.id.tlUserProfileTabs)
//    TabLayout tlUserProfileTabs;

    @Bind(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;
    @Bind(R.id.vUserDetails)
    View vUserDetails;
    //@Bind(R.id.btnFollow)
    //Button btnFollow;
    @Bind(R.id.vUserStats)
    View vUserStats;
    @Bind(R.id.vUserProfileRoot)
    View vUserProfileRoot;

    @BindString(R.string.user_profile_photo)
    String profilePhoto;
    private int avatarSize;
   // private String profilePhoto;
    private UserProfileAdapter userPhotosAdapter;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.setAction(Constants.NAVIGATE_USERPROFILE);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        this.profilePhoto = getString(R.string.user_profile_photo);


        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfilePhoto);

//        setupTabs();
//        setupUserProfileGrid();
//        setupRevealBackground(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        NavigationUtils.navigateToMainActivityWithFragment(UserProfileActivity.this, Constants.NAVIGATE_QUEUE);
    }
//    private void setupTabs() {
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
//        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
//    }



    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rvUserProfile.setVisibility(View.VISIBLE);
//            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
            userPhotosAdapter = new UserProfileAdapter(this);
            rvUserProfile.setAdapter(userPhotosAdapter);
//            animateUserProfileOptions();
            animateUserProfileHeader();
        } else {
//            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            rvUserProfile.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }

//    private void animateUserProfileOptions() {
//        tlUserProfileTabs.setTranslationY(-tlUserProfileTabs.getHeight());
//        tlUserProfileTabs.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
//    }

    private void animateUserProfileHeader() {
           vUserProfileRoot.setTranslationY(-vUserProfileRoot.getHeight());
           ivUserProfilePhoto.setTranslationY(-ivUserProfilePhoto.getHeight());
           vUserDetails.setTranslationY(-vUserDetails.getHeight());
           vUserStats.setAlpha(0);

           vUserProfileRoot.animate().translationY(0).setDuration(300).setInterpolator(INTERPOLATOR);
           ivUserProfilePhoto.animate().translationY(0).setDuration(300).setStartDelay(100).setInterpolator(INTERPOLATOR);
           vUserDetails.animate().translationY(0).setDuration(300).setStartDelay(200).setInterpolator(INTERPOLATOR);
           vUserStats.animate().alpha(1).setDuration(200).setStartDelay(400).setInterpolator(INTERPOLATOR).start();
    }
}
