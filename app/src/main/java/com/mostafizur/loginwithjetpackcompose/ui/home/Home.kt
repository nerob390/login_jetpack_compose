package com.mostafizur.loginwithjetpackcompose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.mostafizur.loginwithjetpackcompose.R
import com.mostafizur.loginwithjetpackcompose.utils.AuthUser

@Composable
fun Home () {
    val user = AuthUser.getUser()
    if (user!=null){
        Column (modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(30.dp)) {
                    Image(
                        painter = rememberImagePainter(user.image), // Your image resource
                        contentDescription = "Splash Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),  // Use alpha value from Animatable
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.weight(1f)) {

                    Column {
                        Text(text = "Joined",color = colorResource(id = R.color.shadow))
                        Row {
                            Text(text = "1", color = colorResource(id = R.color.black), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "Years ago", color = colorResource(id = R.color.black), fontSize = 15.sp, modifier = Modifier.padding(top = 6.dp))
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = user.firstName, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Black,
                modifier = Modifier.padding(start = 30.dp))
            Text(text = user.lastName, fontSize = 30.sp, color = colorResource(id = R.color.shadow),
                modifier = Modifier.padding(start = 30.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "E-Mail", modifier = Modifier.padding(start = 20.dp), color = colorResource(id = R.color.shadow))
            Spacer(modifier = Modifier.height(5.dp))
            Divider( thickness = 2.dp, color = colorResource(id = R.color.shadow), modifier = Modifier.padding(start = 20.dp, end = 300.dp))
            Text(text = user.email, modifier = Modifier.padding(start = 20.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Gender", modifier = Modifier.padding(start = 20.dp), color = colorResource(id = R.color.shadow))
            Spacer(modifier = Modifier.height(5.dp))
            Divider( thickness = 2.dp, color = colorResource(id = R.color.shadow), modifier = Modifier.padding(start = 20.dp, end = 260.dp))
            Text(text = user.gender, modifier = Modifier.padding(start = 20.dp))
        }
    }
}