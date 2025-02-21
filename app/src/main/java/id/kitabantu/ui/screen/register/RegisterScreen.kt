package id.kitabantu.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.kitabantu.R
import id.kitabantu.ui.AuthenticationViewModel
import id.kitabantu.ui.pattern.Pattern
import id.kitabantu.ui.theme.GreyDark
import id.kitabantu.ui.theme.Mobilekbichallenge2025Theme

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateToLogin: ()-> Unit,
    viewModel: AuthenticationViewModel = hiltViewModel()
){
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordIsError = rememberSaveable { mutableStateOf(true) }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val emailIsError = rememberSaveable { mutableStateOf(true) }
    val verifyPassword = rememberSaveable { mutableStateOf("") }
    val verifyPasswordIsError = rememberSaveable { mutableStateOf(true) }
    val verifyPasswordVisible = rememberSaveable { mutableStateOf(false) }
    val loginDialog = remember { mutableStateOf(false) }

    val onRegisterClick = {
        loginDialog.value = true
        viewModel.createUserEmailAndPassword(email.value, verifyPassword.value)
        navigateToLogin()
    }

    RegisterContent(
        email = email,
        password = password,
        passwordIsError = passwordIsError,
        passwordVisible = passwordVisible,
        emailIsError = emailIsError,
        onRegisterClick = onRegisterClick,
        onRegisteredClick = navigateToLogin,
        verifyPassword = verifyPassword,
        verifyPasswordIsError = verifyPasswordIsError,
        verifyPasswordVisible = verifyPasswordVisible
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    password : MutableState<String>,
    verifyPassword : MutableState<String>,
    emailIsError: MutableState<Boolean>,
    passwordIsError: MutableState<Boolean>,
    verifyPasswordIsError: MutableState<Boolean>,
    passwordVisible: MutableState<Boolean>,
    verifyPasswordVisible: MutableState<Boolean>,
    onRegisterClick: () -> Unit,
    onRegisteredClick: () -> Unit
){
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.register_page),
            contentDescription = "Login",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = modifier.height(20.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.email)) },
            value = email.value,
            colors = outlinedTextFieldColors(),
            onValueChange = {
                email.value = it
                emailIsError.value = Pattern.emailPattern(it)},
            isError = !emailIsError.value,
            supportingText = {
                if (!emailIsError.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.email_error_massage),
                        color = MaterialTheme.colorScheme.error

                    )
                }
            },
            maxLines = 1,
            modifier = modifier
                .width(377.dp)
        )

        Spacer(modifier = modifier.height(4.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.password)) },
            value = password.value,
            colors = outlinedTextFieldColors(),
            visualTransformation =
            if (passwordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { password.value = it
                passwordIsError.value = Pattern.passwordPattern(it)},
            isError = !passwordIsError.value,
            supportingText = {
                if (!emailIsError.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.password_error_message),
                        color = MaterialTheme.colorScheme.error

                    )
                }
            },
            maxLines = 1,
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = modifier
                .width(377.dp)
        )

        Spacer(modifier = modifier.height(4.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.verify_password)) },
            value = verifyPassword.value,
            colors = outlinedTextFieldColors(),
            visualTransformation =
            if (verifyPasswordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { verifyPassword.value = it
                verifyPasswordIsError.value = Pattern.verifyPasswordPattern(password.value, it)},
            isError = !verifyPasswordIsError.value,
            supportingText = {
                if (!emailIsError.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.password_error_message),
                        color = MaterialTheme.colorScheme.error

                    )
                }
            },
            maxLines = 1,
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = modifier
                .width(377.dp)
        )

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default
            ),
            modifier = modifier
                .align(Alignment.End)
                .padding(top = 8.dp, end = 24.dp)
        )
        Spacer(modifier = modifier.height(20.dp))
        Box(modifier = modifier.padding(20.dp)) {
            Button(
                onClick = { onRegisterClick() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = GreyDark,
                    disabledContentColor = Color.White
                ),
                enabled = emailIsError.value && email.value.isNotEmpty()
                        && passwordIsError.value && password.value.isNotEmpty()
                        && verifyPasswordIsError.value && verifyPassword.value.isNotEmpty(),
                modifier = modifier
                    .height(50.dp)
                    .width(377.dp)
            ) {
                Text(text = stringResource(R.string.register))
            }
        }

        Spacer(modifier = modifier.height(20.dp))
        Box (contentAlignment = Alignment.BottomCenter){
            Row(
                modifier
                    .padding(8.dp)
                    .clickable { onRegisteredClick() }
            ) {
                Text(
                    text = stringResource(R.string.registered_message),
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = stringResource(R.string.login),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Mobilekbichallenge2025Theme {
        RegisterScreen(
            navigateToLogin = {}
        )
    }
}
