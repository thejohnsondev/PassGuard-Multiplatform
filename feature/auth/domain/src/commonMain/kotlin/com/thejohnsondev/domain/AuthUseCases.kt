package com.thejohnsondev.domain

data class AuthUseCases(
    val signUp: SignUpUseCase,
    val signIn: SignInUseCase,
    val validateEmail: EmailValidateUseCase,
    val validatePassword: PasswordValidationUseCase,
    val generateUserKey: GenerateUserKeyUseCase,
    val saveUserKey: SaveUserKeyUseCase,
    val saveUserToken: SaveUserTokenUseCase,
    val saveUserEmail: SaveUserEmailUseCase,
)