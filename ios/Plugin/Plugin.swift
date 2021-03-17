import Foundation
import Capacitor
import Zip

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ZipPlugin)
public class ZipPlugin: CAPPlugin {

    @objc override public func load() {
    // Called when the plugin is first constructed in the bridge
    }

    @objc func zip(_ call: CAPPluginCall) {
        let src = call.getArray("src", String.self) ?? []
        let dest = call.getString("dest") ?? ""
        
        var paths = [URL]()
        
        for path in src {
            guard let url = URL.init(string: path) else {
                continue
            }
            
            paths.append(url)
        }
        
        guard let destUrl = URL.init(string: dest) else {
            return
        }

        do {
            try Zip.zipFiles(paths: paths, zipFilePath: destUrl, password: nil, progress: nil)
        }
        catch {
            print("Something went wrong")
        }


        call.resolve([
            "success": true,
            "path":dest
        ])
    }

    @objc func unzip(_ call: CAPPluginCall) {
        let src = call.getString("src") ?? ""
        let dest = call.getString("dest") ?? ""
        
        
        guard let srcUrl = URL.init(string: src) else {
            return
        }
        
        guard let destUrl = URL.init(string: dest) else {
            return
        }


        do {
            try Zip.unzipFile(srcUrl, destination: destUrl, overwrite: true, password: nil, progress: nil)
        }
        catch {
            print("Something went wrong")
        }


        call.resolve([
            "success": true
        ])
    }
}
