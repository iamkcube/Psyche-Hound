package com.example.personalityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalityapp.ui.theme.*
import com.example.personalityapp.ui.helpers.PersonalityType
import com.example.personalityapp.ui.helpers.PersonalityTypeList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalityAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .background(Grey)
                            .fillMaxSize()
                    ) {

                        var search by remember {
                            mutableStateOf("")
                        }

                        TitleBar(title = "Personality App")
                        SearchBox(
                            searchText = search,
                            onValueChange = { search = it }
                        )
                        PersonalityTypesComponent(
                            searchText = search
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TitleBar(title: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp
            )
    ) {
        Text(
            text = stringResource(R.string.app_title_bar_text),
            fontFamily = fontBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )
    }
}


@Composable
fun SearchBox(
    searchText: String,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = searchText, onValueChange = onValueChange,
        placeholder = { Text(text = "Search", fontFamily = fontLight) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, start = 15.dp, end = 15.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.LightGray,
            trailingIconColor = Black
        )
    )
}


@Composable
fun PersonalityTypesComponent(
    searchText: String = ""
) {
    val personalityOptions = PersonalityTypeList.filter {
        it.description.lowercase().contains(searchText.lowercase())
    }


    LazyColumn(
        Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(personalityOptions.size) {
            PersonalityCard(
                personalityTypes = personalityOptions[it]
            )
        }
    }
}


@Composable
fun PersonalityCard(personalityTypes: PersonalityType) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxSize(),
        backgroundColor = personalityTypes.backgroundColor
    ) {
        var expanded by remember {
            mutableStateOf(false)
        }



        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .animateContentSize(
                    animationSpec = tween(500)
                )
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    backgroundColor = personalityTypes.timeBackgroundColor
                ) {
                    Text(
                        text = personalityTypes.keyword1,
                        fontFamily = fontMedium,
                        fontSize = 12.sp ,
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Card(
                    backgroundColor = Color.White
                ) {
                    Text(
                        text = personalityTypes.keyword2,
                        fontFamily = fontMedium,
                        fontSize = 12.sp ,
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Card(
                    backgroundColor = personalityTypes.timeBackgroundColor
                ) {
                    Text(
                        text = personalityTypes.keyword1,
                        fontFamily = fontMedium,
                        fontSize = 12.sp ,
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                ExpandItemButton(
                    expanded = expanded,
                    color = personalityTypes.contentColor,
                    onClick = { expanded = !expanded }
                )
            }

            Text(
                text = personalityTypes.title,
                fontFamily = fontBold,
                fontSize = 18.sp,
                color = personalityTypes.contentColor,
                textAlign = TextAlign.Start
            )

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(initialAlpha = 0.1f),
                exit = fadeOut(targetAlpha = 0.3f)
            ) {
                Text(
                    text = personalityTypes.description,
                    fontFamily = fontLight,
                    fontSize = 16.sp,
                    color = personalityTypes.contentColor,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}


@Composable
private fun ExpandItemButton(
    expanded: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = color,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun PersonalityCardPreview() {
    PersonalityTypesComponent()
}
