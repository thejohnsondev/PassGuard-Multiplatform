package com.thejohnsondev.domain

import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.parseTime
import com.thejohnsondev.domain.model.VersionInfo

class GetVersionInfoUseCase {
    operator fun invoke(): VersionInfo {
        val lastAppUpdateTime = BuildKonfigProvider.getLastAppUpdate().parseTime()
        val lastAppUpdateHash = BuildKonfigProvider.getLastCommitHash()
        return VersionInfo(
            versionName = BuildKonfigProvider.getAppVersionName(),
            lastUpdateTime = lastAppUpdateTime,
            lastUpdateHash = lastAppUpdateHash
        )
    }
}