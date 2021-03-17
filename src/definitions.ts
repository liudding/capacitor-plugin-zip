declare module '@capacitor/core' {
  interface PluginRegistry {
    Zip: ZipPlugin;
  }
}

export interface ZipPlugin {
  zip(options: { src: string, dest: string }): Promise<any>;
  unzip(options: { src: string, dest: string }): Promise<any>;
}
