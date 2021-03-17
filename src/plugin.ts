import { ZipPlugin } from './definitions'
import { Plugins } from '@capacitor/core';


export class Zip implements ZipPlugin {

    zip(options: { src: string, dest: string }): Promise<any> {
        return Plugins.Zip.zip(options);
    }

    unzip(options: { src: string, dest: string }): Promise<any> {
        return Plugins.Zip.unzip(options);
    }

}