package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.data.RideEntity
import com.example.data.WalletTransactionEntity
import com.example.ui.theme.*
import com.example.ui.viewmodel.ActiveRideState
import com.example.ui.viewmodel.MeshwarViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.File

// Central Nav routes definition
object MeshwarRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val OTP = "otp"
    const val HOME = "home"
    const val HISTORY = "history"
    const val WALLET = "wallet"
    const val PROFILE = "profile"
}

@Composable
fun MeshwarAppNavigation(
    navController: NavHostController,
    viewModel: MeshwarViewModel
) {
    val rideState by viewModel.rideState.collectAsStateWithLifecycle()
    val isDriverMode by viewModel.isDriverMode.collectAsStateWithLifecycle()

    if (isDriverMode) {
        MeshwarDriverHUDScreen(viewModel = viewModel)
    } else {
        when (val state = rideState) {
            is ActiveRideState.Searching -> {
                MeshwarSearchingScreen(viewModel = viewModel)
            }
            is ActiveRideState.DriverOnTheWay -> {
                MeshwarTrackingScreen(state = state, viewModel = viewModel)
            }
            is ActiveRideState.ArrivedForRating -> {
                MeshwarRatingScreen(state = state, viewModel = viewModel)
            }
            ActiveRideState.Idle -> {
                NavHost(
                    navController = navController,
                    startDestination = MeshwarRoutes.SPLASH
                ) {
                    composable(MeshwarRoutes.SPLASH) {
                        MeshwarSplashScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.LOGIN) {
                        MeshwarLoginScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.OTP) {
                        MeshwarOtpScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.HOME) {
                        MeshwarHomeScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.HISTORY) {
                        MeshwarHistoryScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.WALLET) {
                        MeshwarWalletScreen(navController, viewModel)
                    }
                    composable(MeshwarRoutes.PROFILE) {
                        MeshwarProfileScreen(navController, viewModel)
                    }
                }
            }
        }
    }
}

// Global Custom Background Decoration helper
@Composable
fun BrandedBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .drawBehind {
                drawCircle(
                    color = PrimaryContainerColor.copy(alpha = 0.12f),
                    radius = 350.dp.toPx(),
                    center = Offset(-100.dp.toPx(), -100.dp.toPx())
                )
                drawCircle(
                    color = SecondaryContainerColor.copy(alpha = 0.15f),
                    radius = 300.dp.toPx(),
                    center = Offset(size.width + 100.dp.toPx(), size.height * 0.8f)
                )
            }
    ) {
        content()
    }
}

// 1. SPLASH / GET STARTED SCREEN
@Composable
fun MeshwarSplashScreen(navController: NavController, viewModel: MeshwarViewModel) {
    BrandedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Brand
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                // Logo Icon Box
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(SurfaceContainerLowestColor)
                        .border(1.dp, OutlineVariantColor, RoundedCornerShape(24.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsCar,
                        contentDescription = "Meshwar Logo",
                        tint = PrimaryColor,
                        modifier = Modifier.size(64.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "مشوار",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceColor,
                        fontSize = 36.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "طريقك الأسهل والأسرع للتنقل في جميع أنحاء العراق بكل أمان وراحة.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            // Bento Grid of 4 Transport Cards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TransportGridCard(
                        title = "تكسي",
                        desc = "سريع ومريح",
                        icon = Icons.Default.TaxiAlert,
                        modifier = Modifier.weight(1f)
                    )
                    TransportGridCard(
                        title = "كيا حمل",
                        desc = "لنقل البضائع",
                        icon = Icons.Default.LocalShipping,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TransportGridCard(
                        title = "كيا ركاب",
                        desc = "للمجموعات الصغيرة",
                        icon = Icons.Default.AirportShuttle,
                        modifier = Modifier.weight(1f)
                    )
                    TransportGridCard(
                        title = "كوستر",
                        desc = "للرحلات الجماعية",
                        icon = Icons.Default.DirectionsBus,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // CTA Button
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(MeshwarRoutes.LOGIN) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("get_started_button"),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ابدأ الآن",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryContainerColor
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ابدأ",
                            tint = OnPrimaryContainerColor
                        )
                    }
                }

                Text(
                    text = "باستمرارك، أنت توافق على شروط الخدمة و سياسة الخصوصية",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = SecondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TransportGridCard(
    title: String,
    desc: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainerColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PrimaryColor,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = OnSurfaceColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


// 2. LOGIN SCREEN
@Composable
fun MeshwarLoginScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val phoneState by viewModel.enteredPhone.collectAsStateWithLifecycle()
    var isError by remember { mutableStateOf(false) }

    BrandedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Nav
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Back",
                        tint = OnSurfaceColor
                    )
                }
                Text(
                    text = "تسجيل الدخول",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp)) // Spacer
            }

            // Main Card Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "تسجيل الدخول",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurfaceColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                    Text(
                        text = "أدخل رقم هاتفك لنرسل لك رمز التحقق",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Phone Input Form Custom Design
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "رقم الهاتف",
                            style = MaterialTheme.typography.labelLarge,
                            color = OnSurfaceVariantColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            textAlign = TextAlign.Right
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainerLowColor)
                                .border(
                                    width = 1.5.dp,
                                    color = if (isError) ErrorColor else OutlineVariantColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Dial Code LTR
                            Row(
                                modifier = Modifier.padding(end = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Flag,
                                    contentDescription = "Iraq Flag",
                                    tint = SecondaryColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+964",
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceColor,
                                    fontSize = 16.sp
                                )
                            }
                            // Divider
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(OutlineVariantColor)
                                    .padding(vertical = 12.dp)
                            )
                            // Input
                            OutlinedTextField(
                                value = phoneState,
                                onValueChange = {
                                    isError = false
                                    viewModel.updatePhone(it.filter { char -> char.isDigit() })
                                },
                                placeholder = {
                                    Text(
                                        text = "770 000 0000",
                                        color = OutlineColor.copy(alpha = 0.6f)
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedTextColor = OnSurfaceColor,
                                    unfocusedTextColor = OnSurfaceColor
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("phone_input_field"),
                                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Left)
                            )
                        }
                    }

                    Text(
                        text = "بالاستمرار، أنت توافق على شروط الخدمة و سياسة الخصوصية",
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Submit Button
                    Button(
                        onClick = {
                            if (phoneState.length >= 9) {
                                navController.navigate(MeshwarRoutes.OTP)
                            } else {
                                isError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("send_otp_button")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "إرسال رمز التحقق",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryContainerColor
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Next",
                                tint = OnPrimaryContainerColor
                            )
                        }
                    }
                }
            }

            // Divider Alternative option
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(OutlineVariantColor)
                    )
                    Text(
                        text = "أو",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = SecondaryColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(OutlineVariantColor)
                    )
                }

                OutlinedButton(
                    onClick = { /* Simulated Email option */ },
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, OutlineVariantColor),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.Mail, contentDescription = "Email")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "المتابعة عبر البريد الإلكتروني")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "هل تحتاج للمساعدة؟ تواصل مع الدعم",
                    style = MaterialTheme.typography.bodySmall,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { /* Contact Support */ }
                )
            }
        }
    }
}


// 3. OTP SCREEN
@Composable
fun MeshwarOtpScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val phoneState by viewModel.enteredPhone.collectAsStateWithLifecycle()
    var otpCode by remember { mutableStateOf("") }
    var countdownSeconds by remember { mutableStateOf(119) }

    LaunchedEffect(Unit) {
        while (countdownSeconds > 0) {
            delay(1000)
            countdownSeconds--
        }
    }

    BrandedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Back",
                        tint = OnSurfaceColor
                    )
                }
                Text(
                    text = "التحقق من الرقم",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp))
            }

            // Central Ring and details
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f).padding(vertical = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainerColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhonelinkRing,
                        contentDescription = "Phonelink",
                        tint = OnPrimaryContainerColor,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Text(
                    text = "رمز التحقق",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )

                Text(
                    text = "أدخل الرمز المكون من 4 أرقام المرسل إلى الرقم\n+964 $phoneState",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // OTP Custom fields row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    // 4 custom stylized inputs
                    for (i in 0 until 4) {
                        val activeChar = otpCode.getOrNull(i)?.toString() ?: ""
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainerLowestColor)
                                .border(
                                    width = 2.dp,
                                    color = if (otpCode.length == i) PrimaryColor else OutlineVariantColor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    // Simulated keypress popup or focus trigger
                                    if (otpCode.length < 4) {
                                        otpCode += (1..9)
                                            .random()
                                            .toString()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = activeChar,
                                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                                color = OnSurfaceColor
                            )
                        }
                    }
                }

                if (otpCode.isNotEmpty()) {
                    Text(
                        text = "مسح الرمز",
                        style = MaterialTheme.typography.bodySmall.copy(color = ErrorColor),
                        modifier = Modifier.clickable { otpCode = "" }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timer text
                if (countdownSeconds > 0) {
                    val m = countdownSeconds / 60
                    val s = countdownSeconds % 60
                    val formattedTime = String.format("%02d:%02d", m, s)
                    Text(
                        text = "يمكنك إعادة إرسال الرمز خلال $formattedTime دقيقة",
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryColor
                    )
                } else {
                    Text(
                        text = "إعادة إرسال الرمز الآن",
                        style = MaterialTheme.typography.labelLarge,
                        color = PrimaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            countdownSeconds = 119
                            otpCode = ""
                        }
                    )
                }
            }

            // Bottom CTA
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            // Automatically mock completion and navigate to HOME
                            navController.navigate(MeshwarRoutes.HOME) {
                                popUpTo(MeshwarRoutes.SPLASH) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("verify_otp_button")
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "تأكيد الرمز",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnPrimaryContainerColor
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "تأكيد",
                                tint = OnPrimaryContainerColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "لم يصلك الرمز؟ تغيير الرقم",
                        style = MaterialTheme.typography.bodySmall,
                        color = TertiaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
        }
    }
}


// 4. HOME SCREEN (PASSENGER MAP AND VEHICLE OPTIONS)
@Composable
fun MeshwarHomeScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val selectedVehicle by viewModel.selectedVehicle.collectAsStateWithLifecycle()
    val isDriverMode by viewModel.isDriverMode.collectAsStateWithLifecycle()
    val pickupLocation by viewModel.pickupLocation.collectAsStateWithLifecycle()
    val dropoffLocation by viewModel.dropoffLocation.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var showPickupDropdown by remember { mutableStateOf(false) }
    var showDropoffDropdown by remember { mutableStateOf(false) }
    
    val baghdadLocations = listOf(
        "المنصور، تقاطع الرواد",
        "الكرادة، شارع ٦٢",
        "الجادرية، جامعة بغداد",
        "باب الشرقي",
        "مطار بغداد الدولي",
        "الكاظمية"
    )

    Scaffold(
        bottomBar = {
            MeshwarBottomNavBar(
                currentRoute = MeshwarRoutes.HOME,
                navController = navController,
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Real map with a pin to start destination and final destination
            MeshwarRealMapView(
                fromAddress = pickupLocation,
                toAddress = dropoffLocation,
                showRoute = true,
                modifier = Modifier.fillMaxSize()
            )

            // Header Controls Over Map
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Toolbar row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        IconButton(onClick = { viewModel.toggleDriverMode() }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu Switch Mode",
                                tint = OnSurfaceColor
                            )
                        }
                    }

                    // Brand title
                    Text(
                        text = "MESHWAR",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        ),
                        color = PrimaryColor
                    )

                    // Profile Pic Circle
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerColor)
                            .border(2.dp, SurfaceContainerLowestColor, CircleShape)
                            .clickable { navController.navigate(MeshwarRoutes.PROFILE) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = OnPrimaryContainerColor,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Search Panel Card (Interactive)
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Start Location (Pickup) Selector
                        Box {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerLowColor)
                                    .clickable { showPickupDropdown = true }
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF4CAF50))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "من: $pickupLocation",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurfaceColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Pickup",
                                    tint = PrimaryColor
                                )
                            }
                            DropdownMenu(
                                expanded = showPickupDropdown,
                                onDismissRequest = { showPickupDropdown = false },
                                modifier = Modifier.background(SurfaceContainerLowestColor)
                            ) {
                                baghdadLocations.forEach { loc ->
                                    DropdownMenuItem(
                                        text = { Text(loc, color = OnSurfaceColor) },
                                        onClick = {
                                            viewModel.pickupLocation.value = loc
                                            showPickupDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        Divider(color = OutlineVariantColor.copy(alpha = 0.2f))

                        // Final Destination (Dropoff) Selector
                        Box {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerLowColor)
                                    .clickable { showDropoffDropdown = true }
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFEF5350))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "إلى: $dropoffLocation",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurfaceColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Destination",
                                    tint = PrimaryColor
                                )
                            }
                            DropdownMenu(
                                expanded = showDropoffDropdown,
                                onDismissRequest = { showDropoffDropdown = false },
                                modifier = Modifier.background(SurfaceContainerLowestColor)
                            ) {
                                baghdadLocations.forEach { loc ->
                                    DropdownMenuItem(
                                        text = { Text(loc, color = OnSurfaceColor) },
                                        onClick = {
                                            viewModel.dropoffLocation.value = loc
                                            showDropoffDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Floating CTA trigger (Confirm Request Panel overlay at bottom)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(SurfaceContainerLowestColor)
                    .border(
                        BorderStroke(1.dp, OutlineVariantColor.copy(alpha = 0.3f)),
                        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Drag Line handle
                Box(
                    modifier = Modifier
                        .size(48.dp, 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(OutlineVariantColor.copy(alpha = 0.6f))
                        .align(Alignment.CenterHorizontally)
                )

                val distanceKm = calculateEstimatedDistanceKm(pickupLocation, dropoffLocation)
                val distanceStr = String.format("%.1f", distanceKm)

                Text(
                    text = "اختر وسيلة النقل (المسافة: $distanceStr كم)",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    color = OnSurfaceColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Vehicle select horizontal slider
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    val taxiPrice = calculatePriceForVehicle("Taxi", distanceKm)
                    val kiaPrice = calculatePriceForVehicle("Kia", distanceKm)
                    val kiaLoadPrice = calculatePriceForVehicle("Kia Load", distanceKm)
                    val coasterPrice = calculatePriceForVehicle("Coaster", distanceKm)

                    val vehicles = listOf(
                        VehicleSelectionItem("Taxi", "تكسي", taxiPrice, formatIqdPrice(taxiPrice), "3 دقائق", Icons.Default.TaxiAlert),
                        VehicleSelectionItem("Kia", "كيا", kiaPrice, formatIqdPrice(kiaPrice), "7 دقائق", Icons.Default.AirportShuttle),
                        VehicleSelectionItem("Kia Load", "كيا حمل", kiaLoadPrice, formatIqdPrice(kiaLoadPrice), "10 دقائق", Icons.Default.LocalShipping),
                        VehicleSelectionItem("Coaster", "كوستر", coasterPrice, formatIqdPrice(coasterPrice), "5 دقائق", Icons.Default.DirectionsBus)
                    )
                    items(vehicles) { item ->
                        val isSelected = selectedVehicle == item.id
                        Box(
                            modifier = Modifier
                                .width(112.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) PrimaryColor.copy(alpha = 0.08f) else Color.Transparent)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) PrimaryColor else OutlineVariantColor.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { viewModel.selectVehicleType(item.id) }
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) SurfaceContainerLowestColor else SurfaceContainerLowColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = if (isSelected) PrimaryColor else SecondaryColor,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceColor,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = item.priceText,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) PrimaryColor else SecondaryColor,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = item.eta,
                                    color = OnSurfaceVariantColor,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // Payment Method Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLowColor)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                             imageVector = Icons.Default.Payments,
                             contentDescription = "Payment Method",
                             tint = OnSurfaceVariantColor
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "الدفع نقداً",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = OnSurfaceColor
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { /* Toggle change payment */ }
                    ) {
                        Text(
                            text = "تغيير",
                            style = MaterialTheme.typography.labelLarge,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "تغيير",
                            tint = PrimaryColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Final Button
                Button(
                    onClick = {
                        val price = calculatePriceForVehicle(selectedVehicle, distanceKm)
                        viewModel.startTrackingRide(
                            from = pickupLocation,
                            to = dropoffLocation,
                            price = price
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("confirm_ride_button")
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "تأكيد الطلب",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryContainerColor
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Confirm",
                            tint = OnPrimaryContainerColor
                        )
                    }
                }
            }
        }
    }
}

data class VehicleSelectionItem(
    val id: String,
    val title: String,
    val price: Double,
    val priceText: String,
    val eta: String,
    val icon: ImageVector
)

fun getGeoPointForAddress(address: String): GeoPoint {
    return when {
        address.contains("المنصور") || address.contains("Mansour") -> GeoPoint(33.3250, 44.3614)
        address.contains("الكرادة") || address.contains("Karrada") -> GeoPoint(33.3150, 44.4220)
        address.contains("الجادرية") || address.contains("Jadriyah") -> GeoPoint(33.2720, 44.3780)
        address.contains("باب الشرقي") || address.contains("Bab") -> GeoPoint(33.3330, 44.4180)
        address.contains("مطار") || address.contains("Airport") -> GeoPoint(33.2625, 44.2345)
        address.contains("الكاظمية") || address.contains("Kadhimiya") -> GeoPoint(33.3800, 44.3400)
        else -> GeoPoint(33.3250, 44.3614)
    }
}

fun calculateEstimatedDistanceKm(from: String, to: String): Double {
    val p1 = getGeoPointForAddress(from)
    val p2 = getGeoPointForAddress(to)
    val lat1 = p1.latitude
    val lon1 = p1.longitude
    val lat2 = p2.latitude
    val lon2 = p2.longitude
    
    val r = 6371.0 // Earth radius in km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val distance = r * c
    return if (distance.isNaN() || distance < 0.1) 1.5 else distance
}

fun calculatePriceForVehicle(vehicleId: String, distanceKm: Double): Double {
    val baseAndRate = when (vehicleId) {
        "Taxi" -> Pair(3000.0, 450.0)
        "Kia" -> Pair(1500.0, 200.0)
        "Kia Load" -> Pair(5000.0, 750.0)
        "Coaster" -> Pair(1000.0, 100.0)
        else -> Pair(1000.0, 100.0)
    }
    val rawPrice = baseAndRate.first + (baseAndRate.second * distanceKm)
    // Round to nearest 250 IQD
    return (Math.round(rawPrice / 250.0) * 250).toDouble()
}

fun formatIqdPrice(price: Double): String {
    val formatted = String.format("%,.0f", price)
    return "$formatted د.ع"
}

@Composable
fun MeshwarRealMapView(
    fromAddress: String = "المنصور، تقاطع الرواد",
    toAddress: String = "الكرادة، شارع ٦٢",
    showRoute: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    val transition = rememberInfiniteTransition(label = "map_car_anim")
    val animFraction by transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "car_on_map"
    )

    AndroidView(
        factory = { ctx ->
            Configuration.getInstance().userAgentValue = ctx.packageName
            val osmdroidCache = File(ctx.cacheDir, "osmdroid")
            osmdroidCache.mkdirs()
            Configuration.getInstance().osmdroidTileCache = osmdroidCache
            
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                isTilesScaledToDpi = true
                zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
            }
        },
        update = { mapView ->
            mapView.overlays.clear()
            
            val startPoint = getGeoPointForAddress(fromAddress)
            val endPoint = getGeoPointForAddress(toAddress)
            
            val startMarker = Marker(mapView).apply {
                position = startPoint
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                title = "نقطة الانطلاق"
                subDescription = fromAddress
            }
            mapView.overlays.add(startMarker)
            
            if (showRoute) {
                val endMarker = Marker(mapView).apply {
                    position = endPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "وجهة الوصول"
                    subDescription = toAddress
                }
                mapView.overlays.add(endMarker)
                
                val routePolyline = Polyline(mapView).apply {
                    val points = mutableListOf<GeoPoint>()
                    points.add(startPoint)
                    
                    val midLat = (startPoint.latitude + endPoint.latitude) / 2
                    val midLng = (startPoint.longitude + endPoint.longitude) / 2
                    
                    points.add(GeoPoint(midLat + 0.003, midLng - 0.005))
                    points.add(GeoPoint(midLat - 0.001, midLng + 0.003))
                    points.add(endPoint)
                    
                    setPoints(points)
                    color = android.graphics.Color.parseColor("#F5B041")
                    width = 8f
                }
                mapView.overlays.add(routePolyline)
                
                val carMarker = Marker(mapView).apply {
                    val polyPoints = routePolyline.actualPoints
                    if (polyPoints.size >= 3) {
                        val segFraction = animFraction * (polyPoints.size - 1)
                        val index = segFraction.toInt().coerceIn(0, polyPoints.size - 2)
                        val localFraction = segFraction - index
                        
                        val pStart = polyPoints[index]
                        val pEnd = polyPoints[index + 1]
                        
                        val lat = pStart.latitude + (pEnd.latitude - pStart.latitude) * localFraction
                        val lng = pStart.longitude + (pEnd.longitude - pStart.longitude) * localFraction
                        
                        position = GeoPoint(lat, lng)
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        title = "الكابتن في الطريق"
                    }
                }
                mapView.overlays.add(carMarker)
                
                val midLat = (startPoint.latitude + endPoint.latitude) / 2
                val midLng = (startPoint.longitude + endPoint.longitude) / 2
                mapView.controller.setCenter(GeoPoint(midLat, midLng))
                mapView.controller.setZoom(13.0)
            } else {
                mapView.controller.setCenter(startPoint)
                mapView.controller.setZoom(14.5)
            }
            
            mapView.invalidate()
        },
        modifier = modifier
    )
}

// Map visual simulated rendering inside Canvas
@Composable
fun MeshwarSimulatedMapView() {
    val transition = rememberInfiniteTransition(label = "map_anim")
    val carOffsetFraction by transition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "car_movement"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceDimColor)
    ) {
        val width = size.width
        val height = size.height

        // Draw multiple Baghdad styled intersecting roads
        val mainRoadPath = Path().apply {
            moveTo(0f, height * 0.4f)
            quadraticTo(width * 0.4f, height * 0.35f, width * 0.5f, height * 0.5f)
            quadraticTo(width * 0.6f, height * 0.65f, width, height * 0.6f)
        }

        val secondaryRoadPath = Path().apply {
            moveTo(width * 0.3f, 0f)
            quadraticTo(width * 0.4f, height * 0.4f, width * 0.2f, height)
        }

        val karradaBridgePath = Path().apply {
            moveTo(0f, height * 0.75f)
            lineTo(width, height * 0.85f)
        }

        // Draw river Tigris as a beautiful soft blue curve
        val riverPath = Path().apply {
            moveTo(width * 0.1f, 0f)
            quadraticTo(width * 0.45f, height * 0.2f, width * 0.35f, height * 0.5f)
            quadraticTo(width * 0.25f, height * 0.8f, width * 0.7f, height)
        }

        // River rendering
        drawPath(
            path = riverPath,
            color = Color(0xFFA5D6A7).copy(alpha = 0.4f), // Soft green-blue representation
            style = Stroke(width = 80.dp.toPx())
        )

        // Draw grid patterns representing building blocks
        drawRect(
            color = SurfaceBrightColor.copy(alpha = 0.4f),
            topLeft = Offset(width * 0.05f, height * 0.1f),
            size = androidx.compose.ui.geometry.Size(120.dp.toPx(), 80.dp.toPx())
        )
        drawRect(
            color = SurfaceBrightColor.copy(alpha = 0.4f),
            topLeft = Offset(width * 0.6f, height * 0.2f),
            size = androidx.compose.ui.geometry.Size(140.dp.toPx(), 100.dp.toPx())
        )
        drawRect(
            color = SurfaceBrightColor.copy(alpha = 0.4f),
            topLeft = Offset(width * 0.1f, height * 0.6f),
            size = androidx.compose.ui.geometry.Size(100.dp.toPx(), 120.dp.toPx())
        )

        // Road rendering
        drawPath(path = mainRoadPath, color = Color.White, style = Stroke(width = 16.dp.toPx()))
        drawPath(path = mainRoadPath, color = PrimaryContainerColor.copy(alpha = 0.4f), style = Stroke(width = 4.dp.toPx()))

        drawPath(path = secondaryRoadPath, color = Color.White, style = Stroke(width = 12.dp.toPx()))
        drawPath(path = karradaBridgePath, color = Color.White, style = Stroke(width = 12.dp.toPx()))

        // Draw pins/circles matching Baghdad landmarks
        drawCircle(
            color = ErrorColor,
            radius = 12.dp.toPx(),
            center = Offset(width * 0.25f, height * 0.38f) // Al-Mansour
        )
        drawCircle(
            color = Color.White,
            radius = 6.dp.toPx(),
            center = Offset(width * 0.25f, height * 0.38f)
        )

        drawCircle(
            color = TertiaryColor,
            radius = 12.dp.toPx(),
            center = Offset(width * 0.65f, height * 0.58f) // Al-Karrada
        )
        drawCircle(
            color = Color.White,
            radius = 6.dp.toPx(),
            center = Offset(width * 0.65f, height * 0.58f)
        )

        // Draw animated simulated car along the highlighted route
        val carX = width * (0.1f + carOffsetFraction * 0.7f)
        val carY = height * (0.39f + carOffsetFraction * 0.18f)

        drawCircle(
            color = PrimaryColor,
            radius = 10.dp.toPx(),
            center = Offset(carX, carY)
        )
        drawCircle(
            color = PrimaryContainerColor,
            radius = 6.dp.toPx(),
            center = Offset(carX, carY)
        )
    }
}


// 5. SEARCHING FOR DRIVER MODAL SCREEN
@Composable
fun MeshwarSearchingScreen(viewModel: MeshwarViewModel) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_trans")
    val pulseSize by infiniteTransition.animateFloat(
        initialValue = 100.dp.value,
        targetValue = 240.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "searching_pulse"
    )

    BrandedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.size(48.dp))

            // Animated Pulse Radar
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(pulseSize.dp)
                        .clip(CircleShape)
                        .background(PrimaryColor.copy(alpha = 1.0f - (pulseSize / 240f)))
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainerColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsCar,
                        contentDescription = "Searching",
                        tint = OnPrimaryContainerColor,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            // Text Loading Status
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "جاري البحث عن كابتن...",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Text(
                    text = "نبحث عن أقرب سيارة تكسي لتلبية طلبك بسرور",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SecondaryColor,
                    textAlign = TextAlign.Center
                )
            }

            // Cancel Search Action Button
            Button(
                onClick = { viewModel.cancelActiveRide() },
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowestColor),
                border = BorderStroke(1.dp, ErrorColor.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "إلغاء الطلب",
                    style = MaterialTheme.typography.labelLarge.copy(color = ErrorColor, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


// 6. TRACKING DRIVER SCREEN (THE ROAD ACTIVE MAP)
@Composable
fun MeshwarTrackingScreen(
    state: ActiveRideState.DriverOnTheWay,
    viewModel: MeshwarViewModel
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Real map representing route progress
            MeshwarRealMapView(
                fromAddress = state.fromAddress,
                toAddress = state.toAddress,
                showRoute = true,
                modifier = Modifier.fillMaxSize()
            )

            // Header status info overlay
            Card(
                colors = CardDefaults.cardColors(containerColor = PrimaryContainerColor),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(OnPrimaryContainerColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "السائق في طريقه إليك (يصل خلال ٣ دقائق)",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnPrimaryContainerColor,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Right
                    )
                }
            }

            // Bottom card details of driver Ahmed
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .border(
                        BorderStroke(1.dp, OutlineVariantColor.copy(alpha = 0.3f)),
                        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Drag Line
                    Box(
                        modifier = Modifier
                            .size(48.dp, 6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(OutlineVariantColor.copy(alpha = 0.6f))
                            .align(Alignment.CenterHorizontally)
                    )

                    // Driver details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating & Car details
                        Column {
                            Text(
                                text = state.driverName,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = OnSurfaceColor
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsCar,
                                    contentDescription = "Car",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = state.driverCar,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SecondaryColor
                                )
                            }
                        }

                        // Driver plate card right
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowColor),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, OutlineVariantColor)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = state.plateNumber,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp
                                    ),
                                    color = OnSurfaceColor
                                )
                                Text(
                                    text = "رقم اللوحة",
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariantColor
                                )
                            }
                        }
                    }

                    // Fare details / pricing estimator breakdown
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowColor)
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = "Estimated Fare",
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "تكلفة الرحلة المقدرة",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = OnSurfaceColor
                                )
                                val distanceKm = calculateEstimatedDistanceKm(state.fromAddress, state.toAddress)
                                val distanceStr = String.format("%.1f", distanceKm)
                                Text(
                                    text = "المسافة: $distanceStr كم (الدفع نقداً)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceVariantColor
                                )
                            }
                        }
                        Text(
                            text = formatIqdPrice(state.price),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = PrimaryColor
                            )
                        )
                    }

                    // Simulated arrival buttons for mockup interactivity
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { /* simulated call */ },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = OnPrimaryContainerColor)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "اتصال", style = MaterialTheme.typography.labelLarge.copy(color = OnPrimaryContainerColor))
                            }
                        }

                        Button(
                            onClick = {
                                // Simulate trip completion
                                viewModel.completeRideAndGoToRating()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryContainerColor),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Chat, contentDescription = "Chat", tint = OnSecondaryContainerColor)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "دردشة / إنهاء", style = MaterialTheme.typography.labelLarge.copy(color = OnSecondaryContainerColor))
                            }
                        }
                    }

                    // Cancel ride
                    OutlinedButton(
                        onClick = { viewModel.cancelActiveRide() },
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, ErrorColor.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel", tint = ErrorColor)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "إلغاء الرحلة", style = MaterialTheme.typography.labelLarge.copy(color = ErrorColor))
                        }
                    }
                }
            }
        }
    }
}


// 7. FEEDBACK / RATING SCREEN
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MeshwarRatingScreen(
    state: ActiveRideState.ArrivedForRating,
    viewModel: MeshwarViewModel
) {
    var selectedStars by remember { mutableStateOf(0) }
    var notesText by remember { mutableStateOf("") }
    val tags = listOf("سيارة نظيفة", "سياقة آمنة", "مهذب جداً", "طريق مختصر")
    val selectedTags = remember { mutableStateListOf<String>() }

    BrandedBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header top Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.submitRating(5, "") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = OnSurfaceColor)
                }
                Text(
                    text = "تقييم الرحلة",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp))
            }

            // Success arrival banner
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHighestColor),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "لقد وصلت لوجهتك!",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = OnSurfaceVariantColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "رحلة سعيدة مع مشوار",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Stats Bento Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Payments, contentDescription = "Cost", tint = PrimaryColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "التكلفة الإجمالية", color = OnSurfaceVariantColor, fontSize = 12.sp)
                        Text(
                            text = "${String.format("%,.0f", state.price)} د.ع",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnSurfaceColor
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = "Duration", tint = PrimaryColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "وقت الرحلة", color = OnSurfaceVariantColor, fontSize = 12.sp)
                        Text(
                            text = "24 دقيقة",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnSurfaceColor
                        )
                    }
                }
            }

            // Driver feedback card
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Driver Avatar",
                            tint = OnPrimaryContainerColor,
                            modifier = Modifier.size(56.dp)
                        )
                    }

                    Text(
                        text = state.driverName,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurfaceColor
                    )
                    Text(
                        text = "كابتن مشوار • ${state.driverCar}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryColor
                    )

                    // Stars rating selector
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= selectedStars) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = "Star $i",
                                tint = if (i <= selectedStars) PrimaryContainerColor else OutlineVariantColor,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { selectedStars = i }
                            )
                        }
                    }

                    // Optional feedback Textarea
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "أضف ملاحظاتك (اختياري)",
                            style = MaterialTheme.typography.labelLarge,
                            color = OnSurfaceVariantColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            textAlign = TextAlign.Right
                        )
                        OutlinedTextField(
                            value = notesText,
                            onValueChange = { notesText = it },
                            placeholder = { Text(text = "كيف كانت تجربتك مع الكابتن؟") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .testTag("feedback_textarea"),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryColor,
                                unfocusedBorderColor = OutlineVariantColor
                            )
                        )
                    }

                    // Quick Chips Tags selection
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.forEach { tag ->
                            val isSelected = selectedTags.contains(tag)
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSelected) PrimaryContainerColor else SurfaceContainerLowColor)
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) PrimaryColor else OutlineVariantColor,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        if (isSelected) selectedTags.remove(tag) else selectedTags.add(tag)
                                    }
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) OnPrimaryContainerColor else SecondaryColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Rating primary button
                    Button(
                        onClick = { viewModel.submitRating(selectedStars, notesText) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("submit_feedback_button")
                    ) {
                        Text(
                            text = "إرسال التقييم",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnPrimaryColor
                            )
                        )
                    }
                }
            }
        }
    }
}


// 8. RIDE HISTORY / ACTIVITY SCREEN
@Composable
fun MeshwarHistoryScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val ridesList by viewModel.rides.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf("past") } // past, scheduled

    Scaffold(
        bottomBar = {
            MeshwarBottomNavBar(
                currentRoute = MeshwarRoutes.HISTORY,
                navController = navController,
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
                .statusBarsPadding()
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.toggleDriverMode() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
                Text(
                    text = "نشاطاتي",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp))
            }

            // Tab Slider Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowColor)
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (selectedTab == "past") SurfaceContainerLowestColor else Color.Transparent)
                        .clickable { selectedTab = "past" }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "الرحلات السابقة",
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTab == "past") PrimaryColor else SecondaryColor
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (selectedTab == "scheduled") SurfaceContainerLowestColor else Color.Transparent)
                        .clickable { selectedTab = "scheduled" }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "المجدولة",
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTab == "scheduled") PrimaryColor else SecondaryColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTab == "scheduled") {
                // Scheduled empty state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerLowColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "No scheduled rides",
                            tint = SecondaryColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "لا توجد رحلات مجدولة",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurfaceColor
                    )
                    Text(
                        text = "قم بجدولة رحلتك القادمة بكل سهولة عبر واجهة الحجز",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SecondaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            } else {
                // Historical List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(ridesList) { ride ->
                        HistoryRideCard(ride = ride)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryRideCard(ride: RideEntity) {
    val isCancelled = ride.status == "ملغاة"

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row: Vehicle details & State status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainerColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DirectionsCar,
                            contentDescription = ride.type,
                            tint = PrimaryColor
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = ride.type,
                            fontWeight = FontWeight.Bold,
                            color = OnSurfaceColor,
                            fontSize = 15.sp
                        )
                        Text(
                            text = ride.dateText,
                            color = SecondaryColor,
                            fontSize = 12.sp
                        )
                    }
                }

                // Status chip
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (isCancelled) ErrorContainerColor else Color(0xFFE8F5E9)
                        )
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = ride.status,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCancelled) ErrorColor else Color(0xFF2E7D32)
                    )
                }
            }

            // Custom dotted path address flow LTR
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(PrimaryColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = ride.fromAddress,
                        style = MaterialTheme.typography.bodySmall,
                        color = SecondaryColor
                    )
                }

                // Dotted line spacer
                Box(
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .width(2.dp)
                        .height(16.dp)
                        .background(OutlineVariantColor.copy(alpha = 0.5f))
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(ErrorColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = ride.toAddress,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurfaceColor
                    )
                }
            }

            Divider(color = OutlineVariantColor.copy(alpha = 0.3f))

            // Price summary row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "إجمالي التكلفة",
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryColor
                )
                Text(
                    text = ride.priceText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (isCancelled) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    color = if (isCancelled) SecondaryFixedDimColor else PrimaryColor
                )
            }
        }
    }
}


// 9. WALLET / PORTFOLIO SCREEN
@Composable
fun MeshwarWalletScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val profileState by viewModel.userProfile.collectAsStateWithLifecycle()
    val transactionsList by viewModel.transactions.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            MeshwarBottomNavBar(
                currentRoute = MeshwarRoutes.WALLET,
                navController = navController,
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.toggleDriverMode() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
                Text(
                    text = "المحفظة",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp))
            }

            // Wallet card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryContainerColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "الرصيد الحالي",
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnPrimaryContainerColor,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = String.format("%,.0f", profileState?.balance ?: 25450.0),
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 42.sp
                            ),
                            color = OnPrimaryContainerColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "د.ع",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnPrimaryContainerColor
                        )
                    }

                    // Recharge / transfer row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.rechargeWallet(10000.0, "زين كاش")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = OnBackgroundColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("recharge_button")
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Recharge", tint = SurfaceColor)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "شحن رصيد", color = SurfaceColor)
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.rechargeWallet(-5000.0, "تحويل")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceColor.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Send, contentDescription = "Transfer", tint = OnPrimaryContainerColor)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "تحويل", color = OnPrimaryContainerColor)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Preferred Payment Methods Slider
            Text(
                text = "طرق الدفع المفضلة",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = OnSurfaceColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Right
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    PaymentMethodMethodCard(title = "زين كاش", color = Color(0xFFFF0000), icon = Icons.Default.AccountBalanceWallet)
                }
                item {
                    PaymentMethodMethodCard(title = "آسيا بي", color = Color(0xFF00A1E4), icon = Icons.Default.Payments)
                }
                item {
                    PaymentMethodMethodCard(title = "فاست بي", color = PrimaryColor, icon = Icons.Default.QrCode2)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Transactions History Log
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "سجل العمليات",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Text(
                    text = "عرض الكل",
                    style = MaterialTheme.typography.labelLarge,
                    color = PrimaryColor,
                    modifier = Modifier.clickable { /* View All */ }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                transactionsList.forEach { tx ->
                    WalletTransactionCard(tx = tx)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Promo Banner Card
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerColor),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(PrimaryContainerColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalOffer,
                            contentDescription = "Offer",
                            tint = OnPrimaryContainerColor,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "عرض لفترة محدودة!",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryColor,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "اشحن رصيدك بـ 50,000 د.ع أو أكثر واحصل على خصم 10% على رحلاتك القادمة.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariantColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PaymentMethodMethodCard(
    title: String,
    color: Color,
    icon: ImageVector
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, OutlineVariantColor.copy(alpha = 0.3f)),
        modifier = Modifier.width(136.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(28.dp))
            }
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceColor,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun WalletTransactionCard(tx: WalletTransactionEntity) {
    val isExpense = tx.type == "expense"
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isExpense) Icons.Default.DirectionsCar else Icons.Default.AccountBalanceWallet,
                        contentDescription = tx.title,
                        tint = SecondaryColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = tx.title,
                        fontWeight = FontWeight.Bold,
                        color = OnSurfaceColor,
                        fontSize = 14.sp
                    )
                    Text(
                        text = tx.dateText,
                        color = SecondaryColor,
                        fontSize = 11.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isExpense) "-" else "+"} ${String.format("%,.0f", tx.amount)} د.ع",
                    fontWeight = FontWeight.Bold,
                    color = if (isExpense) ErrorColor else TertiaryColor,
                    fontSize = 14.sp
                )
                Text(
                    text = tx.status,
                    color = OnSurfaceVariantColor,
                    fontSize = 11.sp
                )
            }
        }
    }
}


// 10. PROFILE SCREEN
@Composable
fun MeshwarProfileScreen(navController: NavController, viewModel: MeshwarViewModel) {
    val profileState by viewModel.userProfile.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            MeshwarBottomNavBar(
                currentRoute = MeshwarRoutes.PROFILE,
                navController = navController,
                viewModel = viewModel
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.toggleDriverMode() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
                Text(
                    text = "الملف الشخصي",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurfaceColor
                )
                Box(modifier = Modifier.size(48.dp))
            }

            // User Profile Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(PrimaryContainerColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar",
                                tint = OnPrimaryContainerColor,
                                modifier = Modifier.size(56.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(PrimaryColor)
                                .border(2.dp, SurfaceContainerLowestColor, CircleShape)
                                .clickable { /* Edit avatar */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = OnPrimaryColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Text(
                        text = profileState?.name ?: "Ahmed Al-Iraqi",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurfaceColor
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = PrimaryContainerColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", profileState?.rating ?: 4.9),
                            fontWeight = FontWeight.Bold,
                            color = SecondaryColor,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "|", color = OutlineVariantColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = profileState?.level ?: "عضو ذهبي",
                            fontWeight = FontWeight.Bold,
                            color = SecondaryColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile statistics
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = PrimaryColor.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, PrimaryColor.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "الرحلات", color = PrimaryColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${profileState?.completedRidesCount ?: 142}",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryColor
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = SecondaryContainerColor.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, SecondaryContainerColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "عضو منذ", color = SecondaryColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = profileState?.memberSince ?: "2022",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnSurfaceColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Options List Panel
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    ProfileMenuRow(title = "تعديل الملف الشخصي", icon = Icons.Outlined.Person)
                    Divider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    
                    ProfileMenuRow(title = "مظهر التطبيق", icon = Icons.Outlined.Palette)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppThemePreset.values().forEach { preset ->
                            val isSelected = MeshwarThemeState.currentTheme == preset
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) PrimaryColor else SurfaceContainerLowColor)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) PrimaryColor else OutlineVariantColor.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { MeshwarThemeState.currentTheme = preset }
                                    .padding(vertical = 10.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = preset.label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) OnPrimaryColor else OnSurfaceColor
                                )
                            }
                        }
                    }
                    Divider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))

                    ProfileMenuRow(title = "طرق الدفع", icon = Icons.Outlined.Payments)
                    Divider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(title = "العروض والترويج", icon = Icons.Outlined.Sell)
                    Divider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(title = "الدعم الفني", icon = Icons.Outlined.SupportAgent)
                    Divider(color = OutlineVariantColor.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(title = "الإعدادات", icon = Icons.Outlined.Settings)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logout action red button
            Button(
                onClick = { navController.navigate(MeshwarRoutes.SPLASH) },
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceColor),
                border = BorderStroke(1.dp, ErrorColor.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
                    .testTag("logout_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = ErrorColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "تسجيل الخروج", style = MaterialTheme.typography.labelLarge.copy(color = ErrorColor))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileMenuRow(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* simulated option click */ }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerLowColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = SecondaryColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = OnSurfaceColor
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go",
            tint = OutlineVariantColor,
            modifier = Modifier.size(20.dp)
        )
    }
}


// DRIVER HUD SCREEN MODAL (SCREEN 1 / SCREEN 10)
@Composable
fun MeshwarDriverHUDScreen(viewModel: MeshwarViewModel) {
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
    val isRequestVisible by viewModel.isDriverRideRequestVisible.collectAsStateWithLifecycle()
    val timerProgress by viewModel.activeTimerProgress.collectAsStateWithLifecycle()
    val pickupLocation by viewModel.pickupLocation.collectAsStateWithLifecycle()
    val dropoffLocation by viewModel.dropoffLocation.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .background(SurfaceColor)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.toggleDriverMode() }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Switch Back Mode")
                        }
                        Text(
                            text = "MESHWAR DRIVER",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = PrimaryColor
                        )
                    }

                    // Online/Offline Slider Toggles
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isOnline) Color(0xFFE8F5E9) else SurfaceContainerLowColor)
                            .clickable { viewModel.toggleOnlineStatus() }
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (isOnline) Color(0xFF4CAF50) else Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isOnline) "متصل الآن" else "غير متصل",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (isOnline) Color(0xFF2E7D32) else SecondaryColor
                        )
                    }
                }
                Divider(color = OutlineVariantColor.copy(alpha = 0.3f))
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Real map representing driver route
            MeshwarRealMapView(
                fromAddress = pickupLocation,
                toAddress = dropoffLocation,
                showRoute = true,
                modifier = Modifier.fillMaxSize()
            )

            // HUD overlay metrics
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Statistics Grid Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor.copy(alpha = 0.92f)),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "أرباح اليوم", fontSize = 11.sp, color = OnSurfaceVariantColor)
                            Text(
                                text = "٤٥,٠٠٠ د.ع",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryColor
                            )
                        }
                        Box(modifier = Modifier.width(1.dp).height(32.dp).background(OutlineVariantColor))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "الرحلات", fontSize = 11.sp, color = OnSurfaceVariantColor)
                            Text(
                                text = "١٢",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryColor
                            )
                        }
                        Box(modifier = Modifier.width(1.dp).height(32.dp).background(OutlineVariantColor))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "التقييم", fontSize = 11.sp, color = OnSurfaceVariantColor)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "٤.٩",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryColor
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = PrimaryContainerColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                // Popup offer request for driver
                AnimatedVisibility(
                    visible = isRequestVisible && isOnline,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowestColor),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Top progress slider bar represent offer expiry
                            LinearProgressIndicator(
                                progress = { timerProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = PrimaryColor,
                                trackColor = OutlineVariantColor.copy(alpha = 0.3f)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "طلب رحلة جديد",
                                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurfaceColor
                                    )
                                    Text(text = "تبعد عنك ٣ دقائق", color = SecondaryColor, fontSize = 12.sp)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SecondaryContainerColor)
                                        .padding(vertical = 4.dp, horizontal = 12.dp)
                                ) {
                                    Text(
                                        text = "سيدان",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnSecondaryContainerColor
                                    )
                                }
                            }

                            Divider(color = OutlineVariantColor.copy(alpha = 0.3f))

                            // Route path details
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(PrimaryColor)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "المنصور، تقاطع الرواد", fontWeight = FontWeight.Bold, color = OnSurfaceColor, fontSize = 13.sp)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(ErrorColor)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "الكرادة، شارع ٦٢", fontWeight = FontWeight.Bold, color = OnSurfaceColor, fontSize = 13.sp)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "السعر التقريبي", fontSize = 11.sp, color = OnSurfaceVariantColor)
                                    Text(
                                        text = "٨,٥٠٠ د.ع",
                                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                                        color = PrimaryColor
                                    )
                                }
                            }

                            // Actions Ignored vs Accepted
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.dismissDriverRideRequest() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerLowestColor),
                                    border = BorderStroke(1.dp, OutlineColor),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp)
                                ) {
                                    Text(text = "تجاهل", style = MaterialTheme.typography.labelLarge.copy(color = OnSurfaceColor))
                                }

                                Button(
                                    onClick = {
                                        // Accept ride and change driver mode status back
                                        viewModel.toggleDriverMode()
                                        viewModel.startTrackingRide("المنصور، تقاطع الرواد", "الكرادة، شارع ٦٢", 8500.0)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainerColor),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .weight(2f)
                                        .height(52.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Accept", tint = OnPrimaryContainerColor)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "قبول الرحلة",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                color = OnPrimaryContainerColor,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// Bottom Navigation component aligned to M3 guidelines
@Composable
fun MeshwarBottomNavBar(
    currentRoute: String,
    navController: NavController,
    viewModel: MeshwarViewModel
) {
    NavigationBar(
        containerColor = SurfaceContainerLowestColor,
        tonalElevation = 8.dp
    ) {
        // Home Navigation Bar Item
        NavigationBarItem(
            selected = currentRoute == MeshwarRoutes.HOME,
            onClick = {
                if (currentRoute != MeshwarRoutes.HOME) {
                    navController.navigate(MeshwarRoutes.HOME) {
                        popUpTo(MeshwarRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = "الرئيسية"
                )
            },
            label = { Text(text = "الرئيسية", style = MaterialTheme.typography.labelMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainerColor,
                selectedTextColor = PrimaryColor,
                indicatorColor = PrimaryContainerColor,
                unselectedIconColor = SecondaryColor,
                unselectedTextColor = SecondaryColor
            )
        )

        // History Navigation Bar Item
        NavigationBarItem(
            selected = currentRoute == MeshwarRoutes.HISTORY,
            onClick = {
                if (currentRoute != MeshwarRoutes.HISTORY) {
                    navController.navigate(MeshwarRoutes.HISTORY) {
                        popUpTo(MeshwarRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = "نشاطاتي"
                )
            },
            label = { Text(text = "نشاطاتي", style = MaterialTheme.typography.labelMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainerColor,
                selectedTextColor = PrimaryColor,
                indicatorColor = PrimaryContainerColor,
                unselectedIconColor = SecondaryColor,
                unselectedTextColor = SecondaryColor
            )
        )

        // Wallet Navigation Bar Item
        NavigationBarItem(
            selected = currentRoute == MeshwarRoutes.WALLET,
            onClick = {
                if (currentRoute != MeshwarRoutes.WALLET) {
                    navController.navigate(MeshwarRoutes.WALLET) {
                        popUpTo(MeshwarRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = "المحفظة"
                )
            },
            label = { Text(text = "المحفظة", style = MaterialTheme.typography.labelMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainerColor,
                selectedTextColor = PrimaryColor,
                indicatorColor = PrimaryContainerColor,
                unselectedIconColor = SecondaryColor,
                unselectedTextColor = SecondaryColor
            )
        )

        // Profile Navigation Bar Item
        NavigationBarItem(
            selected = currentRoute == MeshwarRoutes.PROFILE,
            onClick = {
                if (currentRoute != MeshwarRoutes.PROFILE) {
                    navController.navigate(MeshwarRoutes.PROFILE) {
                        popUpTo(MeshwarRoutes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "حسابي"
                )
            },
            label = { Text(text = "حسابي", style = MaterialTheme.typography.labelMedium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainerColor,
                selectedTextColor = PrimaryColor,
                indicatorColor = PrimaryContainerColor,
                unselectedIconColor = SecondaryColor,
                unselectedTextColor = SecondaryColor
            )
        )
    }
}
