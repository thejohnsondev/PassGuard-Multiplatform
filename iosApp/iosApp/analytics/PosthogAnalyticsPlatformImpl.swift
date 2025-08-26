import Foundation
import ComposeApp
import PostHog


class PosthogAnalyticsPlatformImpl: AnalyticsPlatform {
   
    func doInitPlatform(config: AnalyticsConfig) {
        let platformConfig: PosthogAnalyticsConfig = config as! PosthogAnalyticsConfig
        let config = PostHogConfig(apiKey: platformConfig.apiKey, host: platformConfig.host)
        PostHogSDK.shared.setup(config)
    }
    
    func trackEventPlatform(name: String, props: [String: Any], installId: String?) {
        PostHogSDK.shared.capture(name, properties: props)
    }
    
    func logCrashPlatform(t: KotlinThrowable) {
        
    }
    
}
