package com.example.shoppinglistapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListAppTheme {
                ShoppingListApp()
            }
        }
    }
}

class ShoppingListItem(
    val name: String,
    val quantity: String,
    val isCompleted: MutableState<Boolean> = mutableStateOf(false)
)
@Composable
fun ShoppingListApp(){
    //variable for each task
    var shoppingItem by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    //create list and couple it with string and boolean , true = completed. false = not completed
    val itemListing = remember { mutableStateListOf<ShoppingListItem>() }



    Column (
        // Aligns the contents
        modifier = Modifier.padding(
            top = 80.dp,
            start = 120.dp,
            end = 100.dp
        )
    ) {
        Text(
            text = "Shopping List App",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Column (
        // Aligns the contents
        modifier = Modifier.padding(
            top = 180.dp,
            start = 100.dp,
            end = 100.dp
        )
    ) {

            TextField(
                value = shoppingItem,
                onValueChange = {shoppingItem = it},

                label = {Text("Enter your item")},
                textStyle = TextStyle(
                    fontSize = 12.sp
                )

            )

            TextField(
                value = itemQuantity,
                onValueChange = {itemQuantity = it},

                label = {Text("Enter the quantity")},
                textStyle = TextStyle(
                    fontSize = 12.sp
                )

            )




        Spacer(modifier = Modifier.height(20.dp))

        //if text field is not empty, add it to the list (task Listing)
        Button(
            onClick = {
                if(shoppingItem.isNotEmpty() && itemQuantity.isNotEmpty()){
                    itemListing.add(ShoppingListItem(shoppingItem, itemQuantity))
                    //clear the Text on textfield for next task
                    shoppingItem = ""
                    itemQuantity = ""
                }
            },
            // Align the buttons
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3943cb),
                contentColor = Color.White
            )

        ) {
            Text("Add Item")
        }

        Button(
            onClick = {
                itemListing.removeAll { it.isCompleted.value }
            },
            // Align the buttons
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFb92428),
                contentColor = Color.White
            )

        ){Text("Clear Shopping List")}

        Spacer(modifier = Modifier.height(20.dp))

        ShoppingList(itemListing = itemListing)
    }
}

@Composable
fun ShoppingList(itemListing: List<ShoppingListItem>) {
    LazyColumn {
        items(itemListing) { task ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                //if check box is checked, update the boolean so system will know that specific task is completed.
                Checkbox(
                    checked = task.isCompleted.value,
                    onCheckedChange = { isChecked ->
                        task.isCompleted.value = isChecked
                    }
                )


                Text(
                    text = "${task.name} Quantity: ${task.quantity}",
                    // Strikes through text when the box is checked
                    // and makes the text red for visibility
                    style = if (task.isCompleted.value) {
                        TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = Color(0xFFb92428)
                        )

                    } else {
                        TextStyle.Default
                    },
                    // Improve readability and align the text with the checkbox
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}