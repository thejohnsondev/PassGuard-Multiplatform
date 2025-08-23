import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    @State private var iosPlatformDependency: IOSPlatformDependency = IOSPlatformDependency()
    
    func makeUIViewController(context: Context) -> UIViewController {
        let mainComposeVC = MainViewControllerKt.MainViewController(
            platformDependency: iosPlatformDependency
        )
        
        iosPlatformDependency.setPresentingViewController(viewController: mainComposeVC)
        return mainComposeVC
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        iosPlatformDependency.setPresentingViewController(viewController: uiViewController)
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .background(Color.black)
            .ignoresSafeArea(edges: .all)
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



