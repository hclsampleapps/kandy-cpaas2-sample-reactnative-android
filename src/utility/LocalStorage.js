import AsyncStorage from '@react-native-community/async-storage';

 class LocalStorage {


    setTokenData = async (value) => {
        try {
            console.log("value",value);
            await AsyncStorage.setItem('tokenData', value);
        } catch (error) {
            // Error saving data
        }
    };

    getTokenData = async () => {
        try {
            const value = await AsyncStorage.getItem('tokenData').then();
            return value;
        } catch (error) {
            // Error retrieving data
            return null;
        }
    };

    setBaseUrl = async (value) => {
        try {
            console.log("value",value);
            await AsyncStorage.setItem('baseurl', value);
        } catch (error) {
            // Error saving data
        }
    };

    getBaseUrl = async () => {
        try {
            const value = await AsyncStorage.getItem('baseurl').then();
            return value;
        } catch (error) {
            // Error retrieving data
            return null;
        }
    };
}

const localStorage = new LocalStorage();
export default localStorage;
//module.exports = LocalStorage;