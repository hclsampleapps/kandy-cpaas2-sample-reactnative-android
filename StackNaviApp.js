import { createStackNavigator, createAppContainer } from "react-navigation";
import App from './App';
import Home from './src/screens/Home';
import SMS from './src/screens/Sms';
import Chat from './src/screens/Chat';
import AddressBook from './src/screens/AddressBookListing';
import AddContact from './src/screens/AddContact';
import Presence from './src/screens/Presence';

const MainNavigator = createStackNavigator({
    Login: { screen: App },
    Home: { screen: Home },
    Sms: { screen: SMS },
    Chat: { screen: Chat },
    AddressBook: { screen: AddressBook },
    AddContact: { screen: AddContact },
    Presence: { screen: Presence },
});

const StackNaviApp = createAppContainer(MainNavigator);

export default StackNaviApp;