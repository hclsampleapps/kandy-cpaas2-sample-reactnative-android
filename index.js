/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import StackNaviApp from './StackNaviApp';

console.reportErrorsAsExceptions = false;

AppRegistry.registerComponent(appName, () => StackNaviApp);
