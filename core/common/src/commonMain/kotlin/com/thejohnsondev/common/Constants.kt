package com.thejohnsondev.common

const val PASS_MIN_SIZE = 8
const val PASSWORD_AGE_THRESHOLD_DAYS = 180

const val DATA_STORE_FILE_NAME = "passguard_settings.preferences_pb"

val String.Companion.empty: String get() = ""

const val TOGGLE_ANIM_DURATION = 200
const val EXPAND_ANIM_DURATION = 500
const val SCROLL_DOWN_DELAY = 400L
const val DEFAULT_ANIM_DURATION = 300

const val DESKTOP_WINDOW_MIN_WIDTH = 500
const val DESKTOP_WINDOW_MIN_HEIGHT = 600
const val DESKTOP_WINDOW_DEFAULT_WIDTH = 1080
const val DESKTOP_WINDOW_DEFAULT_HEIGHT = 720

const val PASSWORD_IDLE_ITEM_HEIGHT = 88
const val PASSWORD_EXPANDED_ITEM_HEIGHT = 184
const val ADDITIONAL_FIELD_HEIGHT = 84

const val VAULT_GENERATION_FAKE_TIME_DURATION = 1000L

const val VAULT_ITEM_CATEGORY_PERSONAL = "item_category_personal"
const val VAULT_ITEM_CATEGORY_WORK = "item_category_work"
const val VAULT_ITEM_CATEGORY_FINANCE = "item_category_finance"
const val VAULT_ITEM_CATEGORY_OTHERS = "item_category_others"

const val VAULT_ITEM_TYPE_PASSWORDS = "item_type_passwords"
const val VAULT_ITEM_TYPE_NOTES = "item_type_notes"
const val VAULT_ITEM_TYPE_BANK_ACCOUNTS = "item_type_bank_accounts"

const val SORT_TIME_NEW = "sort_time_new"
const val SORT_TIME_OLD = "sort_time_old"
const val SORT_TITLE_AZ = "sort_title_az"
const val SORT_TITLE_ZA = "sort_title_za"

const val SORT_SHOW_FAVORITES_AT_TOP = "sort_show_favorites_at_top"

const val ERROR_INVALID_ID_TOKEN = "INVALID_ID_TOKEN"
const val ERROR_CREDENTIAL_TOO_OLD_LOGIN_AGAIN = "CREDENTIAL_TOO_OLD_LOGIN_AGAIN"

const val CONTENT_DESCRIPTION_VERTICAL_SCROLL = "Vertical Scroll"