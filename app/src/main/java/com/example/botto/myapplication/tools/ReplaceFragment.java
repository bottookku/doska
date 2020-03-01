package com.example.botto.myapplication.tools;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.botto.myapplication.R;



public class ReplaceFragment {

     public ReplaceFragment(Fragment removeFragment, Fragment addFragment, Bundle bundle){
        FragmentManager fragmentManager = removeFragment.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(removeFragment);
        addFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.frag_root, addFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
