package com.android.yambasama.ui.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.yambasama.BuildConfig
import com.android.yambasama.R
import com.android.yambasama.data.model.dataLocal.UserRoom
import com.android.yambasama.ui.views.model.Route


@Composable
@ExperimentalMaterial3Api
fun HomeUserImage(
    navController: NavHostController,
    userRoom: UserRoom
) {

    if (userRoom!!.imageUrl?.length == 0) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_colorier),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithCache {
                    val path = Path()
                    path.addOval(
                        Rect(
                            topLeft = Offset.Zero,
                            bottomRight = Offset(size.width,size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path = path) {
                            // this draws the actual image - if you don't call drawContent, it wont
                            // render anything
                            this@onDrawWithContent.drawContent()
                        }
                        val dotSize = size.width / 8f
                        // Clip a white border for the content
                        drawCircle(
                            Color.Black,
                            radius = dotSize,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            ),
                            blendMode = BlendMode.Clear
                        )
                        //draw the red circle indication
                        drawCircle(
                            Color(0xFF4CAF50),
                            radius = dotSize * 0.8f,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            )
                        )
                    }
                }
                .clickable {
                    navController.navigate(Route.accountView)
                },
            contentDescription = "Profile picture description"
        )
    } else {

        Image(
            painter = rememberAsyncImagePainter(
                model = "${BuildConfig.BASE_URL_DEV}/images/${userRoom!!.imageUrl}",
                placeholder = painterResource(id = R.drawable.ic_profile_colorier),
                error = painterResource(id = R.drawable.ic_profile_colorier),
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .clickable {
                    navController.navigate(Route.accountView)
                }
                //.clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .drawWithCache {
                    val path = Path()
                    path.addOval(
                        Rect(
                            topLeft = Offset.Zero,
                            bottomRight = Offset(size.width,size.height)
                        )
                    )
                    onDrawWithContent {
                        clipPath(path = path) {
                            // this draws the actual image - if you don't call drawContent, it wont
                            // render anything
                            this@onDrawWithContent.drawContent()
                        }
                        val dotSize = size.width / 8f
                        // Clip a white border for the content
                        drawCircle(
                            Color.Black,
                            radius = dotSize,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            ),
                            blendMode = BlendMode.Clear
                        )
                        //draw the red circle indication
                        drawCircle(
                            Color(0xFF4CAF50),
                            radius = dotSize * 0.8f,
                            center = Offset(
                                x = size.width - dotSize,
                                y = size.height - dotSize
                            )
                        )
                    }
                }
            ,
            contentDescription = "Profile picture description",
        )
    }
}