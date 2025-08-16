package com.thejohnsondev.localization

import com.thejohnsondev.ui.utils.ResString
import org.jetbrains.compose.resources.StringResource
import vaultmultiplatform.core.ui.generated.resources.language_arabic
import vaultmultiplatform.core.ui.generated.resources.language_bulgarian
import vaultmultiplatform.core.ui.generated.resources.language_chinese
import vaultmultiplatform.core.ui.generated.resources.language_croatian
import vaultmultiplatform.core.ui.generated.resources.language_czech
import vaultmultiplatform.core.ui.generated.resources.language_danish
import vaultmultiplatform.core.ui.generated.resources.language_dutch
import vaultmultiplatform.core.ui.generated.resources.language_english
import vaultmultiplatform.core.ui.generated.resources.language_estonian
import vaultmultiplatform.core.ui.generated.resources.language_finnish
import vaultmultiplatform.core.ui.generated.resources.language_french
import vaultmultiplatform.core.ui.generated.resources.language_german
import vaultmultiplatform.core.ui.generated.resources.language_greek
import vaultmultiplatform.core.ui.generated.resources.language_hungarian
import vaultmultiplatform.core.ui.generated.resources.language_italian
import vaultmultiplatform.core.ui.generated.resources.language_japanese
import vaultmultiplatform.core.ui.generated.resources.language_korean
import vaultmultiplatform.core.ui.generated.resources.language_latvian
import vaultmultiplatform.core.ui.generated.resources.language_lithuanian
import vaultmultiplatform.core.ui.generated.resources.language_norwegian
import vaultmultiplatform.core.ui.generated.resources.language_polish
import vaultmultiplatform.core.ui.generated.resources.language_portuguese
import vaultmultiplatform.core.ui.generated.resources.language_romanian
import vaultmultiplatform.core.ui.generated.resources.language_serbian
import vaultmultiplatform.core.ui.generated.resources.language_slovak
import vaultmultiplatform.core.ui.generated.resources.language_slovenian
import vaultmultiplatform.core.ui.generated.resources.language_spanish
import vaultmultiplatform.core.ui.generated.resources.language_swedish
import vaultmultiplatform.core.ui.generated.resources.language_turkish
import vaultmultiplatform.core.ui.generated.resources.language_ukrainian

enum class Language(val typeNameStringResource: StringResource, val iso2Code: String) {
    ENGLISH(typeNameStringResource = ResString.language_english, iso2Code = "en"),
    SPANISH(typeNameStringResource = ResString.language_spanish, iso2Code = "es"),
    UKRAINIAN(typeNameStringResource = ResString.language_ukrainian, iso2Code = "uk");
    // TODO uncomment and add translations before release
//    GERMAN(typeNameStringResource = ResString.language_german, iso2Code = "de"),
//    FRENCH(typeNameStringResource =ResString.language_french, iso2Code = "fr"),
//    ITALIAN(typeNameStringResource =ResString.language_italian, iso2Code = "it"),
//    POLISH(typeNameStringResource =ResString.language_polish, iso2Code = "pl"),
//    PORTUGUESE(typeNameStringResource =ResString.language_portuguese, iso2Code = "pt"),
//    CHINESE(typeNameStringResource = ResString.language_chinese, iso2Code = "zh"),
//    JAPANESE(typeNameStringResource = ResString.language_japanese, iso2Code = "ja"),
//    KOREAN(typeNameStringResource = ResString.language_korean, iso2Code = "ko"),
//    TURKISH(typeNameStringResource = ResString.language_turkish, iso2Code = "tr"),
//    DUTCH(typeNameStringResource = ResString.language_dutch, iso2Code = "nl"),
//    SWEDISH(typeNameStringResource = ResString.language_swedish, iso2Code = "sv"),
//    DANISH(typeNameStringResource = ResString.language_danish, iso2Code = "da"),
//    NORWEGIAN(typeNameStringResource = ResString.language_norwegian, iso2Code = "no"),
//    FINNISH(typeNameStringResource = ResString.language_finnish, iso2Code = "fi"),
//    GREEK(typeNameStringResource = ResString.language_greek, iso2Code = "el"),
//    CZECH(typeNameStringResource = ResString.language_czech, iso2Code = "cs"),
//    HUNGARIAN(typeNameStringResource = ResString.language_hungarian, iso2Code = "hu"),
//    ROMANIAN(typeNameStringResource = ResString.language_romanian, iso2Code = "ro"),
//    BULGARIAN(typeNameStringResource = ResString.language_bulgarian, iso2Code = "bg"),
//    SLOVAK(typeNameStringResource = ResString.language_slovak, iso2Code = "sk"),
//    SLOVENIAN(typeNameStringResource = ResString.language_slovenian, iso2Code = "sl"),
//    CROATIAN(typeNameStringResource = ResString.language_croatian, iso2Code = "hr"),
//    SERBIAN(typeNameStringResource = ResString.language_serbian, iso2Code = "sr"),
//    LATVIAN(typeNameStringResource = ResString.language_latvian, iso2Code = "lv"),
//    LITHUANIAN(typeNameStringResource = ResString.language_lithuanian, iso2Code = "lt"),
//    ESTONIAN(typeNameStringResource = ResString.language_estonian, iso2Code = "et");

    companion object {
        val default = ENGLISH
        fun fromTypeName(typeName: String?): Language {
            return entries.firstOrNull { it.name.equals(typeName, ignoreCase = true) } ?: default
        }

        fun fromIso2Code(iso2Code: String?): Language {
            return entries.firstOrNull { it.iso2Code.equals(iso2Code, ignoreCase = true) } ?: default
        }
    }
}