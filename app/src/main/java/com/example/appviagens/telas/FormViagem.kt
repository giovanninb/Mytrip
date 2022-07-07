package com.example.appviagens.telas

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.example.appviagens.ScreenManager
import com.example.appviagens.component.CustomTopAppBar
import com.example.appviagens.component.DatePickerDemo
import com.example.appviagens.model.Pessoa
import com.example.appviagens.repository.PessoaRepository
import com.example.appviagens.ui.theme.Gainsoro
import com.example.appviagens.viewModel.PessoaViewModel
import com.example.appviagens.viewModel.ViagemViewModel
import com.example.appviagens.viewModel.ViagemViewModelFactory

var nmidx = 0;
@Composable
fun FormViagemCompose(navController: NavHostController, id: Int?, idUser: Int) {

    val ctx = LocalContext.current
    val app = ctx.applicationContext as Application
    var model:
            ViagemViewModel = viewModel(
        factory = ViagemViewModelFactory(app)
    )
    if (id != null && id > 0) {
        model.id = id
        model.findById(id)
    }
    Scaffold(
        topBar = {
            if (id != null && id > 0) {
                CustomTopAppBar(navController, "Editar viagem", true)
            } else {
                CustomTopAppBar(navController, "Nova viagem", true)
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(6.dp))
            if (id != null && id > 0) {
                Text(
                    text = "Editar Viagem",
                    style = TextStyle(fontSize = 40.sp)
                )
            } else {
                Text(
                    text = "Nova Viagem",
                    style = TextStyle(fontSize = 40.sp)
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            TextField(
                label = { Text(text = "Destino") },
                singleLine = true,
                value = model.destino,
                onValueChange = { model.destino = it },
                leadingIcon = {
                    Icon(
                        tint = Gainsoro,
                        imageVector = Icons.Rounded.Flight,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                var selectedOption by remember {
                    mutableStateOf(0)
                }
                var cor1 by remember {
                    mutableStateOf(Color.Unspecified)
                }
                var cor2 by remember {
                    mutableStateOf(Color.Unspecified)
                }
                if (selectedOption == 1) {
                    cor1 = Color.DarkGray
                    cor2 = Color.Unspecified
                } else if (selectedOption == 2) {
                    cor2 = Color.DarkGray
                    cor1 = Color.Unspecified
                }
                if (id != null && id > 0 && selectedOption == 0) {
                    selectedOption = model.tipoID
                }
                model.tipoID = selectedOption
                Button(
                    onClick = { selectedOption = 1 },
                    colors = ButtonDefaults.buttonColors(backgroundColor = cor1),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    )
                ) {
                    Icon(
                        Icons.Rounded.Surfing,
                        tint = Gainsoro,
                        contentDescription = "Lazer",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Lazer", color = Color.DarkGray)
                }
                Button(
                    onClick = { selectedOption = 2 },
                    colors = ButtonDefaults.buttonColors(backgroundColor = cor2),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    )
                ) {
                    Icon(
                        Icons.Rounded.Work,
                        tint = Gainsoro,
                        contentDescription = "Negócios",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Negócios", color = Color.DarkGray)
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            model.dataPartida = DatePickerDemo("Data de partida", model.dataPartida)
            Spacer(modifier = Modifier.padding(15.dp))
            model.dataChegada = DatePickerDemo("Data de chegada", model.dataChegada)
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                label = { Text(text = "Orçamento") },
                singleLine = true,
                value = model.orcamento?.toString(),
                onValueChange = {
                    try {
                        model.orcamento = it.toDouble()
                    } catch (e: Exception) {
                        Log.e("app", "Erro conversão de valor")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        tint = Gainsoro,
                        imageVector = Icons.Rounded.AttachMoney,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            )
            //// para testes: ////
            model.usuarioID = idUser
            Spacer(modifier = Modifier.padding(20.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    if (id != null && id > 0) {
                        Toast
                            .makeText(
                                context,
                                "Viagem editada com sucesso!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Viagem cadastrada com sucesso!",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    model.salvar()
                    navController.navigate(ScreenManager.Viagens.route)
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                if (id != null && id > 0) {
                    Text(text = "Editar viagem")
                } else {
                    Text(text = "Adicionar")
                }
            }
        }
    }

}


