package com.android.yambasama.ui.views.shimmer

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun AnnouncementShimmer() {

    //These colors will be used on the brush. The lightest color should be in the middle

    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition() // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
    ShimmerGridItemAnnouncement(brush = brush)
}

@ExperimentalMaterial3Api
@Composable
fun ShimmerGridItemAnnouncement(brush: Brush) {

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 10.dp), verticalAlignment = Alignment.Top
    ) {
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 10.dp), verticalAlignment = Alignment.Top
    ) {
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}