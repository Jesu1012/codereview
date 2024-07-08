package com.xyz.codereview.Vista.Scene1.Extend

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xyz.codereview.R


@Composable
fun BlockHome(
) {

    Row(
        Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(350.dp),
            color = Color(0xFF8D6531),
            shape = RoundedCornerShape(25.dp) // Mitad de la altura para redondear completamente las esquinas
        ) {
            Image(
                painter = painterResource(id = R.drawable.cofe), // Ajusta el recurso de la imagen según tu archivo drawable
                contentDescription = "Example Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(340.dp),
            color = Color.White,
            shape = RoundedCornerShape(25.dp) // Mitad de la altura para redondear completamente las esquinas
        ) {
            Image(
                painter = painterResource(id = R.drawable.pc), // Ajusta el recurso de la imagen según tu archivo drawable
                contentDescription = "Example Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(340.dp),
            color = Color.Yellow,
            shape = RoundedCornerShape(25.dp) // Mitad de la altura para redondear completamente las esquinas
        ) {
            Image(
                painter = painterResource(id = R.drawable.publicidad3), // Ajusta el recurso de la imagen según tu archivo drawable
                contentDescription = "Example Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }



}





@Preview
@Composable
fun HomeScreenPreview() {

    BlockHome()

}
