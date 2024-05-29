package com.example.tefinal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tefinal.R;
import com.example.tefinal.fragment.CartFragment;
import com.example.tefinal.fragment.HomeFragment;
import com.example.tefinal.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout rl_Home = findViewById(R.id.RL_home);
        RelativeLayout rl_Cart = findViewById(R.id.RL_Cart);
        RelativeLayout rl_profile = findViewById(R.id.RL_Profile);

        ImageView iv_Home = findViewById(R.id.IV_Home);

        ImageView iv_Profile = findViewById(R.id.IV_Profile);
        ImageView iv_Cart = findViewById(R.id.IV_Cart);

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());

        if (!(fragment instanceof HomeFragment)){
            iv_Home.setImageResource(R.drawable.hometerang);
            iv_Profile.setImageResource(R.drawable.baseline_account_circle_24);

            iv_Cart.setImageResource(R.drawable.baseline_shopping_cart_24);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, homeFragment)
                    .commit();
        }



        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_Profile.setImageResource(R.drawable.profilterang);
                iv_Home.setImageResource(R.drawable.baseline_home_24);
                iv_Cart.setImageResource(R.drawable.baseline_shopping_cart_24);

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, profileFragment)
                        .commit();
            }
        });

        rl_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_Home.setImageResource(R.drawable.hometerang);
                iv_Profile.setImageResource(R.drawable.baseline_account_circle_24);

                iv_Cart.setImageResource(R.drawable.baseline_shopping_cart_24);

                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, homeFragment)
                        .commit();
            }
        });

        rl_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_Profile.setImageResource(R.drawable.baseline_account_circle_24);
                iv_Home.setImageResource(R.drawable.baseline_home_24);
                iv_Cart.setImageResource(R.drawable.cartterang);

                CartFragment cartFragment = new CartFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, cartFragment)
                        .commit();
            }
        });
    }
}
