package com.nothasset.myapplication

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nothasset.myapplication.ui.theme.Jetpackcomposeassignment1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpackcomposeassignment1Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Courses(courses = coursesList)
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Compose Academy")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Go To Courses!")
        }
    }
}

@Composable
private fun Courses(
    modifier: Modifier = Modifier,
    courses: List<coursesData>,
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        item { Text (
            text = "Course List",
            modifier = Modifier.padding(24.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
            )
        ) }
        items(items = courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
private fun CourseCard(course : coursesData, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Course(course = course)
    }
}

@Composable
fun Course(course: coursesData) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Code: ${course.code}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${course.creditHours} Credit Hours",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${course.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Prerequisites: ${course.prerequisites}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Show less" else "Show more"
            )
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    Jetpackcomposeassignment1Theme {
        Courses(courses = coursesList)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    Jetpackcomposeassignment1Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    Jetpackcomposeassignment1Theme {
        MyApp(Modifier.fillMaxSize())
    }
}

data class coursesData (
    val title: String,
    val description: String,
    val prerequisites: String,
    val code: String,
    val creditHours: Int,
)

val coursesList = listOf(
    coursesData(
        title = "Intro to Computer Science",
        description = "Basics of computing and programming.",
        prerequisites = "None",
        code = "CS101",
        creditHours = 3
    ),
    coursesData(
        title = "Data Structures",
        description = "Study of common data structures.",
        prerequisites = "CS101",
        code = "CS102",
        creditHours = 3
    ),
    coursesData(
        title = "Algorithms",
        description = "Core algorithm design and analysis.",
        prerequisites = "CS102",
        code = "CS201",
        creditHours = 3
    ),
    coursesData(
        title = "Object-Oriented Programming",
        description = "OOP concepts using Kotlin.",
        prerequisites = "CS101",
        code = "CS202",
        creditHours = 3
    ),
    coursesData(
        title = "Databases",
        description = "Intro to SQL and relational models.",
        prerequisites = "CS102",
        code = "CS210",
        creditHours = 3
    ),
    coursesData(
        title = "Web Development",
        description = "Front-end and back-end web basics.",
        prerequisites = "CS202",
        code = "CS220",
        creditHours = 3
    ),
    coursesData(
        title = "Operating Systems",
        description = "Processes, memory, and file systems.",
        prerequisites = "CS201",
        code = "CS301",
        creditHours = 4
    ),
    coursesData(
        title = "Computer Networks",
        description = "Networking concepts and protocols.",
        prerequisites = "CS201",
        code = "CS302",
        creditHours = 3
    ),
    coursesData(
        title = "Software Engineering",
        description = "Software design and teamwork.",
        prerequisites = "CS202",
        code = "CS310",
        creditHours = 3
    ),
    coursesData(
        title = "Mobile Development",
        description = "Building Android apps with Kotlin.",
        prerequisites = "CS202",
        code = "CS320",
        creditHours = 3
    ),
    coursesData(
        title = "Cybersecurity",
        description = "Basics of computer security.",
        prerequisites = "CS302",
        code = "CS330",
        creditHours = 3
    ),
    coursesData(
        title = "Machine Learning",
        description = "Intro to ML and model training.",
        prerequisites = "CS201",
        code = "CS340",
        creditHours = 4
    ),
    coursesData(
        title = "Artificial Intelligence",
        description = "Foundations of AI techniques.",
        prerequisites = "CS340",
        code = "CS350",
        creditHours = 3
    ),
    coursesData(
        title = "Cloud Computing",
        description = "Intro to cloud services and deployment.",
        prerequisites = "CS210",
        code = "CS360",
        creditHours = 3
    ),
    coursesData(
        title = "Capstone Project",
        description = "Final project demonstrating skills.",
        prerequisites = "Senior standing",
        code = "CS499",
        creditHours = 5
    )
)
