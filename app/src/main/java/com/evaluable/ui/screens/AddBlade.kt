package com.evaluable.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluable.R
import com.evaluable.ui.TopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddBlade(navController: NavController) {
    Scaffold(topBar = {
        TopBar(
            navController = navController,
            pageName = stringResource(id = R.string.main_menu_opt1)
        )
    }) {

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val usersCollectionName = stringResource(id = R.string.collection_users)
        val bladesCollectionName = stringResource(id = R.string.collection_blades)

        // STATES
        var name by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }
        var isResponse by rememberSaveable { mutableStateOf(false) }
        var responseMessage by rememberSaveable { mutableStateOf("") }
        var isLoading by rememberSaveable { mutableStateOf(false) }

        val successMessage = stringResource(id = R.string.add_blade_success_res)
        val errorMessage = stringResource(id = R.string.add_blade_error_res)
        val focusManager = LocalFocusManager.current
        val items =
            listOf("Luz", "Oscuridad", "Fuego", "Agua", "Electricidad", "Tierra", "Viento", "Hielo")


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = stringResource(id = R.string.add_blade_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(20.dp))
            OutlinedTextField(value = description,
                onValueChange = { description = it },
                label = { Text(text = stringResource(id = R.string.add_blade_description)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))


            var expanded by remember { mutableStateOf(false) }
            val items = listOf(
                "Luz",
                "Oscuridad",
                "Fuego",
                "Agua",
                "Electricidad",
                "Tierra",
                "Viento",
                "Hielo"
            )
            var selectedIndex by remember { mutableStateOf(0) }
            Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth() ,colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
                Text(text = "Cambiar elemento", textAlign = TextAlign.Justify)
            }
            Text(text = items[selectedIndex])
            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colors.primary
                    )
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        Text(text = s)
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))

            val data = hashMapOf(
                "name" to name.toString(),
                "description" to description.toString(),
                "element" to items[selectedIndex].toString()
            )

            val user = auth.currentUser?.email

            val userData = hashMapOf(
                "email" to user
            )

            Button(onClick = {
                isLoading = true
                if (user != null) {

                    db.collection(usersCollectionName)
                        .document(user)
                        .set(userData, SetOptions.merge())
                        .addOnSuccessListener {
                            db.collection(usersCollectionName)
                                .document(user)
                                .collection(bladesCollectionName)
                                .document(name.toString().lowercase())
                                .set(data, SetOptions.merge())
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
                        }
                        .addOnFailureListener {
                            responseMessage = errorMessage
                        }

                }
            }) {
                Text(text = stringResource(id = R.string.add_blade_submit))
            }

            if (isResponse) {
                PopUpResponse(response = responseMessage) {
                    isResponse = false
                    name = ""
                    description = ""
                    focusManager.clearFocus()
                }
            }

            if (isLoading) {
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
        text = {
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
