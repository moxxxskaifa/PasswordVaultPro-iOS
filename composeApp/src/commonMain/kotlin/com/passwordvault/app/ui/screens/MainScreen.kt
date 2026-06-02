package com.passwordvault.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passwordvault.app.platform.HapticFeedback
import com.passwordvault.app.platform.copyToClipboard
import kotlin.random.Random

data class SavedPassword(val label: String, val password: String)

@Composable
fun MainScreen() {
    var saved by remember { mutableStateOf(listOf<SavedPassword>()) }
    var label by remember { mutableStateOf("") }
    var length by remember { mutableFloatStateOf(16f) }
    var useSymbols by remember { mutableStateOf(true) }
    var currentPwd by remember { mutableStateOf("") }
    val haptic = remember { HapticFeedback() }
    val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" + if (useSymbols) "!@#$%^&*()_+-=[]{}|;':",./<>?" else ""

    fun generate(): String = (1..length.toInt()).map { chars[Random.nextInt(chars.length)] }.joinToString("")

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Password Vault", fontWeight = FontWeight.Bold) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Card(shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Generate Password", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(16.dp))
                    Text("{length.toInt()} characters", fontSize = 14.sp)
                    Slider(value = length, onValueChange = { length = it }, valueRange = 6f..32f)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Include Symbols", fontSize = 14.sp); Spacer(Modifier.weight(1f))
                        Switch(checked = useSymbols, onCheckedChange = { useSymbols = it })
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { currentPwd = generate(); haptic.light() }, modifier = Modifier.fillMaxWidth(),
                           shape = RoundedCornerShape(12.dp)) { Text("Generate") }
                }
            }
            if (currentPwd.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(currentPwd, fontSize = 16.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        IconButton(onClick = { copyToClipboard(currentPwd); haptic.light() }) { Icon(Icons.Default.ContentCopy, "Copy") }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("Saved", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            if (saved.isEmpty()) {
                Text("No saved passwords", color = MaterialTheme.colorScheme.onMaterialTheme.colorScheme.surfaceVariant, fontSize = 14.sp)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(saved) { item ->
                        Card(shape = RoundedCornerShape(12.dp)) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(item.label, fontWeight = FontWeight.Medium)
                                    Text(item.password, fontSize = 12.sp, color = MaterialTheme.colorScheme.onMaterialTheme.colorScheme.surfaceVariant)
                                }
                                IconButton(onClick = { copyToClipboard(item.password); haptic.light() }) { Icon(Icons.Default.ContentCopy, "Copy") }
                            }
                        }
                    }
                }
            }
        }
    }
}
