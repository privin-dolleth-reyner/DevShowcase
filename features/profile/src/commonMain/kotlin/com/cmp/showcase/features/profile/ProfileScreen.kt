import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmp.showcase.data.profile.Profile
import com.cmp.showcase.features.profile.ProfileState
import com.cmp.showcase.features.profile.ProfileViewmodel
import com.cmp.showcase.features.profile.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewmodel: ProfileViewmodel = koinViewModel(),
    onBackClick: () -> Unit,
    onUrlClick: (String) -> Unit,
    onEmailClick: (String) -> Unit
) {
    val state by viewmodel.state.collectAsState()

    val scrollState = rememberScrollState()
    val isNameVisible = remember { mutableStateOf(false) }

    Surface {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Row(modifier = Modifier.height(80.dp).padding(top = 16.dp)) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                if (isNameVisible.value.not()) {
                    AnimatedVisibility(
                        visible = true,
                        modifier = Modifier.align(Alignment.CenterVertically).weight(1f)
                    ) {
                        Text(
                            text = state.profile?.getName() ?: "",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
            Divider(Modifier.height(1.dp))
            when (val uiState = state.uiState) {
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            uiState.msg,
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.error
                        )
                    }
                }

                UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                UiState.Success -> Profile(
                    state = state,
                    scrollState = scrollState,
                    isNameVisible = isNameVisible,
                    onUrlClick = onUrlClick,
                    onEmailClick = onEmailClick
                )
            }
        }
    }
}

@Composable
fun Profile(
    state: ProfileState,
    scrollState: ScrollState,
    isNameVisible: MutableState<Boolean>,
    onUrlClick: (String) -> Unit,
    onEmailClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        state.profile?.let {
            ProfileHeader(it, isNameVisible, onEmailClick, onUrlClick)
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Summary")
        Text(
            text = state.profile?.about ?: "",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Skills")
        SkillsGrid(state.profile?.skills ?: emptyList())


        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Project")
        state.projects.forEach { project ->
            ProjectItem(
                title = project.title,
                description = project.description,
                link = project.link,
                onClick = {
                    onUrlClick(project.link)
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        SectionTitle(title = "Experience")
        state.experiences.forEach { experience ->
            ExperienceItem(
                company = experience.company,
                position = experience.designation,
                duration = experience.duration,
                description = experience.description
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Education")
        state.educations.forEach { education ->
            EducationItem(
                school = education.institution,
                degree = education.degree,
                duration = education.duration,
                location = education.location
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Languages")
        state.languages.forEach { language ->
            LanguageItem(
                name = language.name
            )
        }

        Spacer(modifier = Modifier.height(200.dp))

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileHeader(
    profile: Profile,
    isNameVisible: MutableState<Boolean>,
    onEmailClick: (String) -> Unit,
    onUrlClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = profile.getName(),
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.boundsInWindow()
                isNameVisible.value = position.top != 0f && position.bottom != 0f
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = profile.designation,
            style = MaterialTheme.typography.h6,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            ContactInfo(icon = Icons.Default.Place, text = profile.location)
            Spacer(modifier = Modifier.width(16.dp))
            ContactInfo(icon = Icons.Default.Email, text = profile.emailAddress, onClick = {
                onEmailClick(profile.emailAddress)
            })
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onUrlClick(profile.linkedInUrl)
            }){
                Text("LinkedIn", style = MaterialTheme.typography.button)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                onUrlClick(profile.githubUrl)
            }){
                Text("Github", style = MaterialTheme.typography.button)
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun ExperienceItem(
    company: String,
    position: String,
    duration: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = position,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            if (company.isNotEmpty()) {
                Text(text = company, color = Color.Gray)
            }
            Text(text = duration, style =  MaterialTheme.typography.subtitle2)
            Text(
                text = description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun EducationItem(
    school: String,
    degree: String,
    duration: String,
    location: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = school, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
            Text(text = degree, style = MaterialTheme.typography.subtitle2)
            Text(text = location, style = MaterialTheme.typography.subtitle2)
            Text(text = duration, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
private fun LanguageItem(
    name: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProjectItem(
    title: String,
    description: String,
    link: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
            Text(text = description, style = MaterialTheme.typography.body1, modifier = Modifier.padding(top = 4.dp))
            Text(text = AnnotatedString(link), style = MaterialTheme.typography.caption, color = MaterialTheme.colors.secondary, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
private fun SkillsGrid(skills: List<String> = emptyList()) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        skills.forEach { skill ->
            Chip(onClick = {}) {
                Text(
                    text = skill,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun ContactInfo(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.subtitle1)
    }
}