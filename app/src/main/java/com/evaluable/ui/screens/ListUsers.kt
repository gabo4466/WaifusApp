package com.evaluable.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluable.R
import com.evaluable.ui.TopBar
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListUsers(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val usersCollectionName = stringResource(id = R.string.collection_users)
    val error = stringResource(id = R.string.error_generic)
    var message by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var users = rememberSaveable { mutableListOf<String>() }


    db.collection(usersCollectionName)
        .get()
        .addOnSuccessListener {
            users.clear()
            for (user in it) {
                users.add(user.id)
            }
        }
        .addOnFailureListener {
            message = error
        }
        .addOnCompleteListener {
            isLoading = false
        }

    Scaffold(topBar = {
        TopBar(
            navController = navController,
            pageName = stringResource(id = R.string.main_menu_opt2)
        )
    }) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(text = message, color = MaterialTheme.colors.primary)

            if (isLoading) {
                Spacer(modifier = Modifier.size(30.dp))
                CircularProgressIndicator()
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
            ) {
                for (email in users) {
                    UserItem(email = email)
                }

            }
        }


    }
}

@Composable
fun UserItem(email: String) {

    Spacer(modifier = Modifier.size(30.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = email, textAlign = TextAlign.Left)
        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(130.dp)) {
            Text(text = stringResource(id = R.string.list_users_action))
        }
    }

    Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp)


}