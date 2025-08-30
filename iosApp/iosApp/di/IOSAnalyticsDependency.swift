import Foundation
import ComposeApp

class IOSAnalyticsDependency: NSObject, AnalyticsDependency {
    
    func getAnalyticsPlatform() -> AnalyticsPlatform {
        return PosthogAnalyticsPlatformImpl()
    }
    
}
