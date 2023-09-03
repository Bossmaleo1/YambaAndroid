package com.android.yambasama.ui.views.shimmer

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
fun AnnouncementShimmer(iterator: Int) {
    for(i in 1..iterator) {
        ShimmerGridItemAnnouncement(brush = getBrush())
    }
}

@ExperimentalMaterial3Api
@Composable
fun getBrush(): Brush {
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
    return linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier =
                        Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
        ) {
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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


@ExperimentalMaterial3Api
@Composable
fun ShimmerGridItemAnnouncementDetails(brush: Brush) {
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier =
            Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
        ) {
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
            .background(MaterialTheme.colorScheme.background)
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
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
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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


    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = 10.dp), verticalAlignment = Alignment.Top
    ) {

        Spacer(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center) {
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