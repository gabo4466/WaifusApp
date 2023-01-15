package com.evaluable.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluable.R
import com.evaluable.ui.TopBar
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddBlade(navController: NavController) {
    Scaffold(topBar = { TopBar(navController = navController, pageName = stringResource(id = R.string.main_menu_opt1)) }) {

        val db =  FirebaseFirestore.getInstance()
        val collectionName = stringResource(id = R.string.collection_blades)

        // STATES
        var name by rememberSaveable { mutableStateOf("") }
        var isResponse by rememberSaveable { mutableStateOf(false) }
        var responseMessage by rememberSaveable { mutableStateOf("") }
        var isLoading by rememberSaveable { mutableStateOf(false) }

        val successMessage = stringResource(id = R.string.add_blade_success_res)
        val errorMessage = stringResource(id = R.string.add_blade_error_res)


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = name, onValueChange = {name = it}, label = { Text(text = stringResource(id = R.string.add_blade_name)) }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(20.dp))

            val data = hashMapOf(
                "name" to name.toString()
            )

            Button(onClick = {
                isLoading = true
                db.collection(collectionName)
                    .document()
                    .set(data)
                    .addOnSuccessListener {
                        responseMessage = successMessage
                    }
                    .addOnFailureListener {
                        responseMessage = errorMessage
                    }
                    .addOnCompleteListener {
                        isLoading = false
                        isResponse = true
                    }
            }) {
                Text(text = stringResource(id = R.string.add_blade_submit))
            }

            if (isResponse) {
                PopUpResponse(response = responseMessage) {
                    isResponse = false
                    name = ""
                }
            }

            if (isLoading){
                Spacer(modifier = Modifier.size(50.dp))
                CircularProgressIndicator()
            }


        }
    }
}

@Composable
fun PopUpResponse(response: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text= {
            Text(
                text = response,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.confirmation_button))
            }
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}
