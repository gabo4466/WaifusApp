package com.evaluable.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.evaluable.R
import com.evaluable.ui.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListBlades(navController: NavController, email: String?) {

    var user by rememberSaveable { mutableStateOf("") }

    email?.let {
        user = it
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController = navController, pageName = stringResource(id = R.string.blade_list)
        ) }
    ) {}


}