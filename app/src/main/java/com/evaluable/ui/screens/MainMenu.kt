package com.evaluable.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.evaluable.R
import com.evaluable.ui.TopBar

@Composable
fun MainMenu(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController = navController, backbutton = false, pageName = stringResource(
            id = R.string.main_menu_home
        )) }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth,painter = painterResource(id = R.drawable.xenoblade_banner_large), contentDescription = "Banner")
            Column (
                modifier = Modifier.padding(20.dp)
                    ) {
                Button(onClick = { navController.navigate("add_blade") }, modifier = Modifier.width(250.dp)) {
                    Text(text = stringResource(id = R.string.main_menu_opt1))
                }
                Spacer(modifier = Modifier.size(20.dp))
                Button(onClick = { navController.navigate("add_blade") }, modifier = Modifier.width(250.dp)) {
                    Text(text = stringResource(id = R.string.main_menu_opt2))
                }
            }
        }

    }
}


@Preview
@Composable
fun Testing() {
    var navController = rememberNavController()
    MainMenu(navController = navController)


}



