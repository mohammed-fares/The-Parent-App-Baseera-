package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun WelcomeScreen(
  onNavigateLogin: () -> Unit,
  onNavigateSignUp: () -> Unit,
  onSocialLogin: (String) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(BaseeraNavyDark, BaseeraNavySurface, Color(0xFF070E1E))
        )
      )
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      // Top Brand Header
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
      ) {
        // App Icon / Logo Shield
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
              Brush.linearGradient(
                colors = listOf(BaseeraEmeraldDark, BaseeraNavyCard)
              )
            )
            .border(2.dp, BaseeraEmeraldLight, RoundedCornerShape(24.dp))
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "شعار بصيرة",
            tint = BaseeraEmeraldLight,
            modifier = Modifier.size(46.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "بصيرة",
          style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = BaseeraEmeraldLight,
            letterSpacing = 1.sp
          )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "حماية ذكية لأطفالك في عالم رقمي آمن",
          style = MaterialTheme.typography.titleMedium.copy(
            color = BaseeraTextPrimary,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "الرصد اللحظي • التحليل السلوكي التربوي • التدخل الفوري",
          style = MaterialTheme.typography.bodySmall.copy(
            color = BaseeraCyanLight,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
          )
        )
      }

      // Hero Illustration Card
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = BaseeraNavyCard.copy(alpha = 0.7f),
        border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraNavyBorder),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 20.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.padding(16.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.baseera_family_hero_1787522031871),
            contentDescription = "عائلة آمنة رقمياً مع بصيرة",
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .fillMaxWidth()
              .height(150.dp)
              .clip(RoundedCornerShape(16.dp))
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(Icons.Default.Home, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "تطبيق الوالد المعتمد للأسرة العربية",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = BaseeraTextPrimary
              )
            )
          }
        }
      }

      // Actions Section
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Login Button (Navy / Dark Blue)
        Button(
          onClick = onNavigateLogin,
          colors = ButtonDefaults.buttonColors(
            containerColor = BaseeraNavyCard,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.5.dp, BaseeraCyan, RoundedCornerShape(14.dp))
            .testTag("welcome_login_button")
        ) {
          Icon(Icons.Default.VpnKey, contentDescription = null, tint = BaseeraCyanLight)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "تسجيل الدخول",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sign Up Button (Emerald Green)
        Button(
          onClick = onNavigateSignUp,
          colors = ButtonDefaults.buttonColors(
            containerColor = BaseeraEmerald,
            contentColor = Color.White
          ),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("welcome_signup_button")
        ) {
          Icon(Icons.Default.PersonAdd, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "إنشاء حساب جديد",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "أو التسجيل السريع عبر:",
          style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextMuted)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Social Logins
        Row(
          horizontalArrangement = Arrangement.spacedBy(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          SocialLoginButton(iconLabel = "Google", onClick = { onSocialLogin("Google") })
          SocialLoginButton(iconLabel = "Apple", onClick = { onSocialLogin("Apple") })
          SocialLoginButton(iconLabel = "Facebook", onClick = { onSocialLogin("Facebook") })
        }
      }
    }
  }
}

@Composable
fun SocialLoginButton(iconLabel: String, onClick: () -> Unit) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = BaseeraNavyCard,
    border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraNavyBorder),
    modifier = Modifier
      .clickable { onClick() }
      .testTag("social_login_$iconLabel")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
      Text(
        text = iconLabel,
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = BaseeraTextPrimary
        )
      )
    }
  }
}

@Composable
fun SignUpScreen(
  onSignUpSubmitted: (String, String, String, String) -> Unit,
  onNavigateBack: () -> Unit,
  onNavigateLogin: () -> Unit
) {
  var fullName by remember { mutableStateOf("د. محمد فاروق") }
  var email by remember { mutableStateOf("mf.z.hussein.pal@gmail.com") }
  var phone by remember { mutableStateOf("+20 109 876 5432") }
  var password by remember { mutableStateOf("BaseeraSecure2026") }
  var confirmPassword by remember { mutableStateOf("BaseeraSecure2026") }
  var agreeToTerms by remember { mutableStateOf(true) }
  var passwordVisible by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  Surface(
    color = BaseeraNavyDark,
    modifier = Modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      // Back Button & Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onNavigateBack,
          modifier = Modifier.testTag("signup_back_button")
        ) {
          Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = BaseeraTextPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "إنشاء حساب والد جديد",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = BaseeraTextPrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "أنشئ حسابك لبدء حماية أطفالك وتفعيل الرصد الفوري الذكي.",
        style = MaterialTheme.typography.bodyMedium.copy(color = BaseeraTextSecondary)
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Input Fields
      OutlinedTextField(
        value = fullName,
        onValueChange = { fullName = it },
        label = { Text("الاسم الكامل") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BaseeraEmeraldLight) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("signup_fullname_input")
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("البريد الإلكتروني") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BaseeraEmeraldLight) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("signup_email_input")
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("رقم الهاتف المحمول") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BaseeraEmeraldLight) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("signup_phone_input")
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("كلمة المرور") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BaseeraEmeraldLight) },
        trailingIcon = {
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
              contentDescription = null,
              tint = BaseeraTextSecondary
            )
          }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("signup_password_input")
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        label = { Text("تأكيد كلمة المرور") },
        leadingIcon = { Icon(Icons.Default.LockReset, contentDescription = null, tint = BaseeraEmeraldLight) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("signup_confirm_password_input")
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Terms Checkbox
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .clickable { agreeToTerms = !agreeToTerms }
      ) {
        Checkbox(
          checked = agreeToTerms,
          onCheckedChange = { agreeToTerms = it },
          colors = CheckboxDefaults.colors(checkedColor = BaseeraEmerald)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "أوافق على شروط الاستخدام وسياسة الخصوصية الأسرية",
          style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary)
        )
      }

      errorMessage?.let { msg ->
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = msg, color = BaseeraRed, style = MaterialTheme.typography.bodySmall)
      }

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = {
          if (!agreeToTerms) {
            errorMessage = "يرجى الموافقة على شروط الاستخدام للمتابعة."
          } else if (password != confirmPassword) {
            errorMessage = "كلمتا المرور غير متطابقتين."
          } else if (fullName.isBlank() || email.isBlank()) {
            errorMessage = "يرجى تعبئة كافة الحقول المطلوبة."
          } else {
            errorMessage = null
            onSignUpSubmitted(fullName, email, phone, password)
          }
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = BaseeraEmerald,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("signup_submit_button")
      ) {
        Text("إنشاء الحساب والمتابعة للتحقق (OTP)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("لديك حساب بالفعل؟", color = BaseeraTextSecondary, style = MaterialTheme.typography.bodyMedium)
        TextButton(onClick = onNavigateLogin) {
          Text("تسجيل الدخول", color = BaseeraEmeraldLight, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun LoginScreen(
  onLoginSubmitted: (String, String) -> Unit,
  onNavigateBack: () -> Unit,
  onNavigateSignUp: () -> Unit,
  onNavigateForgotPassword: () -> Unit,
  onSocialLogin: (String) -> Unit
) {
  var emailOrPhone by remember { mutableStateOf("mf.z.hussein.pal@gmail.com") }
  var password by remember { mutableStateOf("BaseeraSecure2026") }
  var passwordVisible by remember { mutableStateOf(false) }
  var rememberMe by remember { mutableStateOf(true) }
  var errorText by remember { mutableStateOf<String?>(null) }

  Surface(
    color = BaseeraNavyDark,
    modifier = Modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      IconButton(onClick = onNavigateBack) {
        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = BaseeraTextPrimary)
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Lock, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "تسجيل دخول الوالد",
          style = MaterialTheme.typography.displaySmall.copy(
            fontWeight = FontWeight.Bold,
            color = BaseeraTextPrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "أهلاً بك مجدداً. أدخل بياناتك للاتصال الفوري بمنظومة حماية أطفالك.",
        style = MaterialTheme.typography.bodyMedium.copy(color = BaseeraTextSecondary)
      )

      Spacer(modifier = Modifier.height(28.dp))

      OutlinedTextField(
        value = emailOrPhone,
        onValueChange = { emailOrPhone = it },
        label = { Text("البريد الإلكتروني أو رقم الهاتف") },
        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = BaseeraEmeraldLight) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("login_identity_input")
      )

      Spacer(modifier = Modifier.height(16.dp))

      OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("كلمة المرور") },
        leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = null, tint = BaseeraEmeraldLight) },
        trailingIcon = {
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
              contentDescription = null,
              tint = BaseeraTextSecondary
            )
          }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          focusedLabelColor = BaseeraEmeraldLight,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("login_password_input")
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(
            checked = rememberMe,
            onCheckedChange = { rememberMe = it },
            colors = CheckboxDefaults.colors(checkedColor = BaseeraEmerald)
          )
          Text("تذكرني على هذا الجهاز", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary))
        }

        TextButton(onClick = onNavigateForgotPassword) {
          Text("نسيت كلمة المرور؟", color = BaseeraCyanLight, style = MaterialTheme.typography.bodySmall)
        }
      }

      errorText?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = it, color = BaseeraRed, style = MaterialTheme.typography.bodySmall)
      }

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = {
          if (emailOrPhone.isBlank() || password.isBlank()) {
            errorText = "يرجى إدخال البريد الإلكتروني وكلمة المرور."
          } else {
            errorText = null
            onLoginSubmitted(emailOrPhone, password)
          }
        },
        colors = ButtonDefaults.buttonColors(
          containerColor = BaseeraNavyCard,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .border(1.5.dp, BaseeraEmerald, RoundedCornerShape(14.dp))
          .testTag("login_submit_button")
      ) {
        Icon(Icons.Default.Login, contentDescription = null, tint = BaseeraEmeraldLight)
        Spacer(modifier = Modifier.width(8.dp))
        Text("تسجيل الدخول", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }

      Spacer(modifier = Modifier.height(24.dp))

      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("ليس لديك حساب؟", color = BaseeraTextSecondary, style = MaterialTheme.typography.bodyMedium)
        TextButton(onClick = onNavigateSignUp) {
          Text("إنشاء حساب جديد", color = BaseeraEmeraldLight, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun OtpVerificationScreen(
  onOtpVerified: (String) -> Unit,
  onNavigateBack: () -> Unit
) {
  var otpCode by remember { mutableStateOf("9421") }

  Surface(
    color = BaseeraNavyDark,
    modifier = Modifier.fillMaxSize()
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(24.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(onClick = onNavigateBack) {
          Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = BaseeraTextPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text("رمز التحقق OTP", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
      }

      Spacer(modifier = Modifier.height(30.dp))

      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(80.dp)
          .clip(CircleShape)
          .background(BaseeraEmeraldDark.copy(alpha = 0.2f))
          .border(1.dp, BaseeraEmerald, CircleShape)
      ) {
        Icon(Icons.Default.MarkEmailRead, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(40.dp))
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "تم إرسال رمز التحقق",
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = BaseeraTextPrimary)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "أدخل رمز OTP المكون من 4 أو 6 أرقام المرسل إلى بريدك أو هاتفك المسجل لتأكيد صحة البيانات.",
        style = MaterialTheme.typography.bodyMedium.copy(color = BaseeraTextSecondary, textAlign = TextAlign.Center)
      )

      Spacer(modifier = Modifier.height(30.dp))

      OutlinedTextField(
        value = otpCode,
        onValueChange = { if (it.length <= 6) otpCode = it },
        label = { Text("رمز التحقق (OTP)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        textStyle = MaterialTheme.typography.titleLarge.copy(
          textAlign = TextAlign.Center,
          letterSpacing = 8.sp,
          fontWeight = FontWeight.Bold,
          color = BaseeraEmeraldLight
        ),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("otp_input_field")
      )

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = { onOtpVerified(otpCode) },
        colors = ButtonDefaults.buttonColors(
          containerColor = BaseeraEmerald,
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("otp_submit_button")
      ) {
        Text("تأكيد الرمز والدخول للمنظومة", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }

      Spacer(modifier = Modifier.height(16.dp))

      TextButton(onClick = { /* Simulated resend */ }) {
        Text("إعادة إرسال الرمز (بعد 45 ثانية)", color = BaseeraCyanLight)
      }
    }
  }
}

@Composable
fun ForgotPasswordScreen(
  onResetRequested: () -> Unit,
  onNavigateBack: () -> Unit
) {
  var emailInput by remember { mutableStateOf("mf.z.hussein.pal@gmail.com") }

  Surface(
    color = BaseeraNavyDark,
    modifier = Modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(24.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onNavigateBack) {
          Icon(Icons.Default.ArrowBack, contentDescription = "رجوع", tint = BaseeraTextPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text("استعادة كلمة المرور", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "أدخل بريدك الإلكتروني المسجل وسنرسل لك رمز OTP لإعادة تعيين كلمة مرور جديدة لحسابك.",
        style = MaterialTheme.typography.bodyMedium.copy(color = BaseeraTextSecondary)
      )

      Spacer(modifier = Modifier.height(24.dp))

      OutlinedTextField(
        value = emailInput,
        onValueChange = { emailInput = it },
        label = { Text("البريد الإلكتروني") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BaseeraEmeraldLight) },
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = BaseeraEmerald,
          unfocusedBorderColor = BaseeraNavyBorder,
          unfocusedContainerColor = BaseeraNavyCard,
          focusedContainerColor = BaseeraNavyCard
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
      )

      Spacer(modifier = Modifier.height(24.dp))

      Button(
        onClick = onResetRequested,
        colors = ButtonDefaults.buttonColors(containerColor = BaseeraEmerald),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
      ) {
        Text("إرسال رمز إعادة التعيين", fontWeight = FontWeight.Bold)
      }
    }
  }
}
